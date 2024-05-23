package com.numustec.voluntario.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.numustec.voluntario.R;
import com.numustec.voluntario.entity.Post;
import com.numustec.voluntario.entity.ProfileItem;

import java.util.List;

public class PostItensAdapter extends BaseAdapter {
    List<Post> itens;
    private Context context;

    public PostItensAdapter(Context context,List<Post> itens){
        this.itens=itens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Post getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post, parent, false); // Layout do item da lista
        }
        Post item = getItem(position);
        ImageView icon = (ImageView)convertView.findViewById(R.id.ivIcon);
        TextView title = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView descript = (TextView)convertView.findViewById(R.id.tvContent);
        title.setText(item.title);
        descript.setText(item.descript);
        Drawable imageDrawable = context.getResources().getDrawable(R.mipmap.ic_work);
        icon.setImageDrawable(imageDrawable);
        return convertView;

    }
}
