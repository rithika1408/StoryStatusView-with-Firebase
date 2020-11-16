package com.example.statusstory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

import static java.lang.System.load;

public class MyStatusView extends AppCompatActivity implements Firebase {

    private StoriesProgressView storiesProgressView;
    private ImageView image;
    TextView textView;

    int counter=0;

    DatabaseReference dbref;
    Firebase firebase;

    long pressTime = 0L;
    long limit = 500L;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status_view);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        image = (ImageView) findViewById(R.id.imagey);
        textView=findViewById(R.id.tect);

        dbref= FirebaseDatabase.getInstance().getReference("status");
        firebase=this;

        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Upload> uploads=new ArrayList<>();
                        for (DataSnapshot itemsnapshot:dataSnapshot.getChildren()){
                            Upload upload=itemsnapshot.getValue(Upload.class);
                            uploads.add(upload);
                        }

                        firebase.onFirebaseLoadsuccess(uploads);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        firebase.onFirebaseLoadFailed(databaseError.getMessage());

                    }
                });
            }
        });
        skip.setOnTouchListener(onTouchListener);
    }



    @Override
    public void onFirebaseLoadsuccess(final List<Upload> uploadList) {
        storiesProgressView.setStoriesCount(uploadList.size());
        storiesProgressView.setStoryDuration(6000L);
        Picasso.get().load(uploadList.get(0).getUrl()).into(image, new Callback() {
            @Override
            public void onSuccess() {
                storiesProgressView.startStories();
            }

            @Override
            public void onError(Exception e) {

            }
        });
        storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
            @Override
            public void onNext() {
                if(counter<uploadList.size()){
                    counter++;
                    Picasso.get().load(uploadList.get(counter).getUrl()).into(image);
                }
            }

            @Override
            public void onPrev() {

                if(counter>0){
                    counter--;
                    Picasso.get().load(uploadList.get(counter).getUrl()).into(image);
                }

            }

            @Override
            public void onComplete() {


            }
        });


    };

    @Override
    public void onFirebaseLoadFailed(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}