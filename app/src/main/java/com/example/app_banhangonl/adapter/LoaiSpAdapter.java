package com.example.app_banhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.model.LoaiSp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSpAdapter extends BaseAdapter {
    ArrayList<LoaiSp> arraylistloaisp;
    Context context;

    public LoaiSpAdapter(ArrayList<LoaiSp> arraylistloaisp, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView textloaisp;
        ImageView imageloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.textloaisp = view.findViewById(R.id.textloaisp);
            viewHolder.imageloaisp = view.findViewById(R.id.imageloaisp);
            view.setTag(viewHolder);
        }
        else {
          viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSp loaiSp = (LoaiSp) getItem(i);
        viewHolder.textloaisp.setText(loaiSp.getTenLoaiSP());
        Picasso.with(context).load(loaiSp.getHinhAnhSP()).into(viewHolder.imageloaisp);
        return view;
    }
}
