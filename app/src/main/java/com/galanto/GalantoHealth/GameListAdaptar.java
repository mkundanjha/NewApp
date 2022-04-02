package com.galanto.GalantoHealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.R;

import java.util.ArrayList;

public class GameListAdaptar extends RecyclerView.Adapter<GameListAdaptar.ViewHolder> {
    ArrayList<GameClass> gameClassArrayList;
    Context context;
    //private AdapterView.OnItemClickListener clickListener;
    private GameOnClick clickListener;


    public GameListAdaptar(Context context,ArrayList<GameClass> gameClassArrayList) {
        this.context=context;
        this.gameClassArrayList=gameClassArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.game_icon_cardview,parent,false);
        return new ViewHolder(view);
    }

    public void setWhenClickListener(GameOnClick listner){
        this.clickListener=listner;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.gameTitle.setText(gameClassArrayList.get(position).getGameTitle());
            holder.gameImage.setBackgroundResource(gameClassArrayList.get(position).getGameImage());
            holder.gameCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(gameClassArrayList.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return gameClassArrayList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameTitle;
        CardView gameCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.usrIcomImg);
            gameTitle = itemView.findViewById(R.id.usrFirstName);
            gameCard = itemView.findViewById(R.id.usrCard);

        }


    }
}
