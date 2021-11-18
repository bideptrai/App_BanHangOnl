package com.example.app_banhangonl.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttenlt,txtgia,txtmota;
        public ImageView imglt;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlt =(TextView) view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgia =(TextView) view.findViewById(R.id.textviewgialt);
            viewHolder.txtmota =(TextView) view.findViewById(R.id.textviewmotalt);
            viewHolder.imglt =(ImageView) view.findViewById(R.id.imagelt);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txttenlt.setText(sanPham.getTen_SP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("Giá : "+decimalFormat.format(sanPham.getGia_SP()) + "Đ");
        viewHolder.txtmota.setMaxLines(2);
        viewHolder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmota.setText(sanPham.getMota_SP());
        Picasso.with(context).load(sanPham.getAnh_SP()).into(viewHolder.imglt);
        return view;
    }
}
