package cl.evilgenius.project_foody_v3.backdoor_test;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import cl.evilgenius.project_foody_v3.R;

public class backDoor_menu extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_door_menu);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Categoria");
        mDatabase.keepSynced(true);


        mBlogList = (RecyclerView) findViewById(R.id.myReciclerView);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<backDoor_Blog,BlogViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<backDoor_Blog, BlogViewHolder>
                (backDoor_Blog.class,R.layout.blog_row_backdoor,BlogViewHolder.class,mDatabase) {




            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull backDoor_Blog model) {


            }

            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;


            }
        };
    }

    public static  class BlogViewHolder extends  RecyclerView.ViewHolder{

        View mView;
        public BlogViewHolder (View itemView){
            super(itemView);
            mView=itemView;
        }


    }*/
}
