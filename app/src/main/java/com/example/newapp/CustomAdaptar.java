package com.example.newapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

public class CustomAdaptar extends RecyclerView.Adapter<CustomAdaptar.ViewHolder> {
    FileDataBase fileDataBase;
    Context context;
    int p_id=0;
    ArrayList<Patients> arrayList;
    int selected_position=0;

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

        holder.usrName.setText(arrayList.get(position).getName());


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
                    goToDashboard();
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

        // Method used to delete patient or create current patient file on the basis of flag
        // flag=0 then delete flag=1 the create curr patient
        public void deleteOrCurrPatient(int p_id,int flag){
            File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File dir=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/","curr_patient.json");
            fileDataBase=new FileDataBase(context);
            String response = fileDataBase.readFile("Galanto/RehabRelive", "patients.json");
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    if (jsonArray.getJSONObject(i).getInt("p_id")==p_id) {
                        if(flag==0) {
                            jsonArray.remove(i);
                            Toast.makeText(cv.getContext(),"Patient deleted",Toast.LENGTH_SHORT).show();
                            break;

                        }else {
                            if (dir.exists()){
                                fileDataBase.updateFile("Galanto/RehabRelive","curr_patient.json",jsonArray.getJSONObject(i).toString());
                            }else {
                                fileDataBase.createFile("Galanto/RehabRelive","curr_patient.json",jsonArray.getJSONObject(i).toString());
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


        public void goToDashboard(){
            p_id=arrayList.get(this.getAdapterPosition()).getP_id();
            Intent intent=new Intent(context, Dashboard.class);
            intent.putExtra("user_name",usrName.getText().toString());
            intent.putExtra("p_id",String.valueOf( p_id));

            context.startActivity(intent);

        }

    }
}
