package com.galanto.GalantoHealth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.R;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

public class CustomAdaptar extends RecyclerView.Adapter<CustomAdaptar.ViewHolder> {
    FileDataBase fileDataBase;
    Context context;
    int p_id=0;
    ArrayList<Patients> arrayList;
    int selected_position=0;
    int age=0;
    String handImp,gender;

    public CustomAdaptar(Context context, ArrayList<Patients> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }




    @Override
    public CustomAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.user_profile_cardview,parent,false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String usrNameText=arrayList.get(position).getName();
        String arr[]=usrNameText.split(" ",2);
        String firstName=arr[0];


        holder.usrName.setText(firstName.substring(0,1).toUpperCase()+firstName.substring(1));
        handImp=arrayList.get(position).getHandImp();
        gender=arrayList.get(position).getGender();
        age=arrayList.get(position).getAge();



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView usrImg,selectIcon;
        TextView usrName;
        CardView cv;
        ConstraintLayout cardLayout;

        public ViewHolder(View itemView){
            super(itemView);
            usrImg=itemView.findViewById(R.id.usrIcomImg);
            usrName=itemView.findViewById(R.id.usrFirstName);
            cv=itemView.findViewById(R.id.usrCard);

            cardLayout=itemView.findViewById(R.id.cardLayout);



            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p_id=arrayList.get(getAdapterPosition()).getP_id();
                    deleteOrCurrPatient(p_id,1);
                    createOrDeletePatientFolder(p_id,1);
                    goToDashboard(Dashboard.class);

                }
            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //cv.setBackground(null);
                    cardLayout.setBackgroundResource(R.color.orange);
                    //cv.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.gradient_green,null));
                    //cv.setCardBackgroundColor(R.drawable.gradient_blue);
                    p_id=arrayList.get(getAdapterPosition()).getP_id();

                    //Toast.makeText(cv.getContext(), String.valueOf(p_id),Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(context)
                            .setTitle("Delete patient: "+usrName.getText().toString())
                            .setMessage("Do you really want to delete this patient?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int position=getAdapterPosition();
                                    deleteOrCurrPatient(p_id,0);
                                    arrayList.remove(position);
                                    notifyItemRemoved(position);


                                    //cv.setCardBackgroundColor(null);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    cardLayout.setBackgroundResource(R.drawable.gradient_blue);
                                }
                            })
                            .setIcon(android.R.drawable.ic_menu_delete)
                            .setCancelable(false)
                            .show();

                    return true;
                }
            });

        }
        //flag 1: create
        //flag 0: delete
        public void createOrDeletePatientFolder(int p_id,int flag){

            try{
                String folderName="patient_"+String.valueOf(p_id);
                File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File dir=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/",folderName);
                File file2=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/"+folderName+"/","all_sessions.json");
                File file3=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/"+folderName+"/","all_rom.json");
                if(flag==1) {
                    if (!dir.exists()) {
                        dir.mkdir();
                        if(!file2.exists()){
                            fileDataBase.createFile("Galanto/RehabRelive/"+folderName,"all_sessions.json","");
                        }
                        if(!file3.exists()){
                            fileDataBase.createFile("Galanto/RehabRelive/"+folderName,"all_rom.json","");
                        }

                    }
                }else if(flag==0){
                    if (dir.exists()) {
                        //dir.delete();
                        deleteRecursive(dir);
                        //fileDataBase.deleteFolder("Galanto/RehabRelive/patient_"+String.valueOf(p_id));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // Method used to delete patient or create current patient file on the basis of flag
        // flag=0 then delete flag=1 the create curr patient
        public void deleteOrCurrPatient(int p_id,int flag){

            // create new current patient file
            File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File dir=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/","current_patient.json");
            fileDataBase=new FileDataBase(context);

            //read the existing all patients file
            String response = fileDataBase.readFile("Galanto/RehabRelive", "patients.json");

            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    if (jsonArray.getJSONObject(i).getInt("p_id")==p_id) {

                        // For delete case
                        if(flag==0) {
                            createOrDeletePatientFolder(p_id,0);
                            jsonArray.remove(i);
                            Toast.makeText(cv.getContext(),"Patient deleted",Toast.LENGTH_SHORT).show();
                            break;

                        }else {
                            // For creating current patent

                            //if current patient file exist then update else create new
                            if (dir.exists()){
                                fileDataBase.updateFile("Galanto/RehabRelive","current_patient.json",jsonArray.getJSONObject(i).toString());
                            }else {
                                fileDataBase.createFile("Galanto/RehabRelive","current_patient.json",jsonArray.getJSONObject(i).toString());
                            }
                            break;
                        }

                    }
                }

                fileDataBase.updateFile("Galanto/RehabRelive","patients.json",jsonArray.toString());

            }catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(cv.getContext(),"Deletion Error:"+ex.toString(),Toast.LENGTH_SHORT).show();

            }
        }

         private void   deleteRecursive(File fileOrDirectory) {
            if (fileOrDirectory.isDirectory())
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);

            fileOrDirectory.delete();
        }


        public void goToDashboard(Class className){
            p_id=arrayList.get(this.getAdapterPosition()).getP_id();
            Intent intent=new Intent(context, className);
            intent.putExtra("user_name",usrName.getText().toString());
            intent.putExtra("p_id",String.valueOf( p_id));
            intent.putExtra("hand_imp",handImp);
            intent.putExtra("gender",gender);
            intent.putExtra("age",String.valueOf(age));


            context.startActivity(intent);


        }

    }
}
