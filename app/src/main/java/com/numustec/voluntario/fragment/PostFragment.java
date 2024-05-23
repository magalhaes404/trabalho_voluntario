package com.numustec.voluntario.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.numustec.voluntario.R;
import com.numustec.voluntario.activity.PostDetailsActivity;
import com.numustec.voluntario.adapter.PostItensAdapter;
import com.numustec.voluntario.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    ListView listView;
    CardView loading;
    FirebaseFirestore db;
    LinearLayout posts;
    List<Post> post;
    public PostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        listView = view.findViewById(R.id.lvPost);
        loading = (CardView)view.findViewById(R.id.inLoading);
        posts = (LinearLayout)view.findViewById(R.id.layoutPost);

        db = FirebaseFirestore.getInstance();
        post = new ArrayList<>();
        // include header
        View header = inflater.inflate(R.layout.card_header, view.findViewById(R.id.inCardHeader));
        TextView title = header.findViewById(R.id.txtCardHeader);
        ImageView icon = header.findViewById(R.id.ivCardHeader);
        title.setText(getString(R.string.nav_home));
        Drawable imageDrawable = getResources().getDrawable(R.mipmap.ic_home);
        icon.setImageDrawable(imageDrawable);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if(post.get(position).title != ""){
                Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
                intent.putExtra("title",post.get(position).title);
                intent.putExtra("descript",post.get(position).descript);
                intent.putExtra("address",post.get(position).address);
                intent.putExtra("date",post.get(position).datetime);
                intent.putExtra("id",post.get(position).id);
                intent.putExtra("author",post.get(position).author);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        getPost();
        super.onStart();
    }

    public void setLoading(boolean x){
        if(x == true){
            loading.setVisibility(View.VISIBLE);
            posts.setVisibility(View.GONE);
        }
        else{
            loading.setVisibility(View.GONE);
            posts.setVisibility(View.VISIBLE);
        }
    }
    public void getPost() {
        post.clear();
        PostItensAdapter adapter = new PostItensAdapter(getContext(),post);
        listView.setAdapter(adapter);
        setLoading(true);
        db.collection("post")
                //.orderBy("date_time")
                .get()
                .addOnSuccessListener(command -> {
                    List<DocumentSnapshot> datas = command.getDocuments();
                    Log.e("Post","Size => "+datas.size());
                    for (int i=0;i<datas.size();i++) {
                        Post e = new Post();
                        String title = datas.get(i).getString("title");
                        String descript = datas.get(i).getString("descript");
                        String address = datas.get(i).getString("local");
                        String id = datas.get(i).getId();
                        String author = datas.get(i).getString("uuid_login");
                        e.id = id;
                        e.title = title;
                        e.descript = descript;
                        e.address = address;
                        e.author = author;
                        this.post.add(e);
                    }
                    PostItensAdapter adapter2 = new PostItensAdapter(getContext(),post);
                    listView.setAdapter(adapter2);
                })
                .addOnFailureListener(command -> {

                })
                .addOnCompleteListener(command -> {
                  setLoading(false);
                });
    }
}