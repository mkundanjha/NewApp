package com.example.newapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdaptar extends RecyclerView.Adapter<CustomAdaptar.ViewHolder> {

    Context context;

    public CustomAdaptar(Context context, ArrayList<Patients> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Patients> arrayList;



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
        ImageView usrImg;
        TextView usrName;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);
            usrImg=itemView.findViewById(R.id.usrIcomImg);
            usrName=itemView.findViewById(R.id.usrFirstName);
            cv=itemView.findViewById(R.id.usrCard);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToDashboard();
                }
            });
        }
        public void goToDashboard(){
            Intent intent=new Intent(context, Dashboard.class);
            intent.putExtra("user_name",usrName.getText().toString());
            context.startActivity(intent);

        }

    }
}
