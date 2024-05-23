package com.numustec.voluntario.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.numustec.voluntario.MainActivity;
import com.numustec.voluntario.R;
import com.numustec.voluntario.activity.InfoActivity;
import com.numustec.voluntario.activity.ProfileActivity;
import com.numustec.voluntario.adapter.ProfileItensAdapter;
import com.numustec.voluntario.entity.ProfileItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    ListView itens;
    FirebaseAuth mauth;
    public Profile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mauth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = view.findViewById(R.id.lvProfileItens);
        // include header
        View header = inflater.inflate(R.layout.card_header, view.findViewById(R.id.inCardHeader));
        TextView title = header.findViewById(R.id.txtCardHeader);
        ImageView icon = header.findViewById(R.id.ivCardHeader);
        title.setText(getString(R.string.nav_profile));
        Drawable imageDrawable = getResources().getDrawable(R.mipmap.ic_profile);
        icon.setImageDrawable(imageDrawable);

        ArrayList<ProfileItem> itens = new ArrayList<>();
        ProfileItem item1 = new ProfileItem();
        item1.icon = R.mipmap.ic_profile_item_2;
        item1.titulo = getString(R.string.profile_item1);
        ProfileItem item2 = new ProfileItem();
        item2.icon = R.mipmap.ic_logout;
        item2.titulo = getString(R.string.profile_item3);
        ProfileItem item3 = new ProfileItem();
        item3.icon = R.mipmap.ic_info;
        item3.titulo = getString(R.string.profile_item4);
        itens.add(item1);
        itens.add(item3);
        itens.add(item2);
        ProfileItensAdapter adapter = new ProfileItensAdapter(getContext(),itens);
        listView.setDividerHeight(50);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if(position == 0)
            {
                personal_data();
            }
            else if(position == 1){
                info();
            }
            else if(position == 2){
                logout();
            }

        });
        return view;
    }

    public void info(){
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        startActivity(intent);
    }
    public void logout(){
        mauth.signOut();
        if(mauth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
    }

    public void personal_data(){
        Intent intent = new Intent(getActivity(), ProfileActivity.class); // Use getActivity() for context
        startActivity(intent);
    }
}