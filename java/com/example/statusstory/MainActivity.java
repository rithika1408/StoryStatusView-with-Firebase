package com.example.statusstory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerviewAdapter recyclerviewAdapter;

    int arr[]={R.drawable.o1,R.drawable.o2,R.drawable.o3,R.drawable.o4,R.drawable.o5,R.drawable.o6,R.drawable.o1,R.drawable.o2,R.drawable.o3};
    String name[]={"Add to story","Aravind","Jacklin Suresh","Shankar","Ramesh","Geetha","Gayathri","Aravind","Jacklin Suresh"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerviewAdapter=new RecyclerviewAdapter(arr,name,this);


        recyclerView.setAdapter(recyclerviewAdapter);

        recyclerView.setHasFixedSize(true);
    }

}