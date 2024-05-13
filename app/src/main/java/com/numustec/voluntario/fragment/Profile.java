package com.numustec.voluntario.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.numustec.voluntario.R;
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = view.findViewById(R.id.lvProfileItens);
        ArrayList<ProfileItem> itens = new ArrayList<>();
        ProfileItem item1 = new ProfileItem();
        item1.icon = R.mipmap.ic_profile;
        item1.titulo = getString(R.string.nav_profile);
        ProfileItem item2 = new ProfileItem();
        item2.icon = R.mipmap.ic_logout;
        item2.titulo = getString(R.string.nav_profile);

        itens.add(item1);
        ProfileItensAdapter adapter = new ProfileItensAdapter(getContext(),itens);
        listView.setAdapter(adapter);

        return view;
    }
}