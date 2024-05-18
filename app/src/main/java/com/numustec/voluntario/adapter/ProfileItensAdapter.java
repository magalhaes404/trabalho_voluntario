package com.numustec.voluntario.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.numustec.voluntario.R;
import com.numustec.voluntario.entity.ProfileItem;
import java.util.List;

public class ProfileItensAdapter extends BaseAdapter {
    List<ProfileItem> itens;
    private Context context;

    public ProfileItensAdapter(Context context,List<ProfileItem> itens){
        this.itens=itens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    public List<ProfileItem> getItens() {
        return itens;
    }

    @Override
    public ProfileItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_menu_profile, parent, false); // Layout do item da lista
        }
        ProfileItem item = getItem(position);
        ImageView icon = (ImageView)convertView.findViewById(R.id.ivIcon);
        TextView name = (TextView)convertView.findViewById(R.id.txtName);
        name.setText(item.titulo);
        Drawable imageDrawable = context.getResources().getDrawable(item.icon);
        icon.setImageDrawable(imageDrawable);
        return convertView;
    }
}
