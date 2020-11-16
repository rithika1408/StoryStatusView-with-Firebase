package com.example.statusstory;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RecyclerviewAdapter  extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>{

    int []arr;
    String []name;
    Context context;


    public RecyclerviewAdapter(int[] arr, String []name, Context context){
        this.arr=arr;
        this.name=name;
        this.context=context;
    }
    @NonNull
    @Override
    public  MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {

        if(position==0){
            holder.circleImageView2.setVisibility(View.VISIBLE);
            holder.circleImageView1.setVisibility(View.GONE);
            holder.circularStatuesView.setVisibility(View.GONE);

        }
        else {
            holder.circleImageView2.setVisibility(View.GONE);
            holder.circleImageView1.setVisibility(View.VISIBLE);
            holder.circularStatuesView.setVisibility(View.VISIBLE);
        }


        holder.imageView.setImageResource(arr[position]);
        holder.textView.setText(name[position]);

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        CircularStatuesView circularStatuesView;
        CircleImageView circleImageView1,circleImageView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
            circleImageView1=itemView.findViewById(R.id.statusImage1);
            circleImageView2=itemView.findViewById(R.id.statusImage2);
            circularStatuesView=itemView.findViewById(R.id.statusCircular1);
            textView=itemView.findViewById(R.id.textview);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition()==0){
                circleImageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MyStatusView.class);
                        context.startActivity(intent);
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,MyStatus.class);
                        context.startActivity(intent);
                    }
                });
            }
            else{
                Intent intent=new Intent(context,StatusView.class);
                context.startActivity(intent);
            }
        }

    }

}
