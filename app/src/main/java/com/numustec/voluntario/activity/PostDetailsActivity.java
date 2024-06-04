package com.numustec.voluntario.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.numustec.voluntario.R;
import com.numustec.voluntario.entity.Post;

public class PostDetailsActivity extends AppCompatActivity {

    TextView title,descript,address,date;
    ImageView icon;
    Post post;
    Button edit;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);
        mauth = FirebaseAuth.getInstance();
        title = (TextView)findViewById(R.id.tvTitle);
        descript = (TextView)findViewById(R.id.tvContent);
        address = (TextView)findViewById(R.id.tvAddress);
        date = (TextView)findViewById(R.id.tvDate);
        icon = (ImageView)findViewById(R.id.ivIcon);
        edit = (Button)findViewById(R.id.btnEdit);
        Drawable imageDrawable = getResources().getDrawable(R.mipmap.ic_work);
        icon.setImageDrawable(imageDrawable);
        post = new Post();
        Intent intent = getIntent();
        post.title = intent.getStringExtra("title");
        post.descript = intent.getStringExtra("descript");
        post.address = intent.getStringExtra("address");
        post.author = intent.getStringExtra("author");
        post.id = intent.getStringExtra("id");
        post.datetime = intent.getLongExtra("date",0);
        Log.e("POSTDETAILS","DATE => "+post.datetime);
        /*if(date1 != null && date1.length() > 0){
            post.datetime = Long.parseLong(date1);*/
            date.setText((post.datetime == 0 ? getString(R.string.date_empty): post.getDate()));
        /*}*/
        title.setText(post.title);
        descript.setText(post.descript);
        address.setText(getString(R.string.address)+": "+post.address);
        edit.setOnClickListener(v -> {
            Intent it = new Intent(this,PostNewEditActivity.class);
            it.putExtra("title",post.title);
            it.putExtra("descript",post.descript);
            it.putExtra("address",post.address);
            it.putExtra("author",post.author);
            it.putExtra("datetime",post.getDate());
            it.putExtra("date",post.datetime);
            it.putExtra("id",post.id);
            startActivity(it);
        });
        Log.e("Post","Author => "+post.author);
        if(mauth.getCurrentUser().getUid().equals(post.author)){
            edit.setVisibility(View.VISIBLE);
        }
        else{
            edit.setVisibility(View.INVISIBLE);
        }
    }
}