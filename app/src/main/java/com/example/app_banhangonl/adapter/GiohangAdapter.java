package com.example.app_banhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.activity.Giohang;
import com.example.app_banhangonl.activity.MainActivity;
import com.example.app_banhangonl.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayGiohang;


    public GiohangAdapter(Context context, ArrayList<GioHang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }

    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder{
        public TextView txttenspgiohang,txtgiaspgiohang;
        public ImageView imgspgiohang;
        public Button btncong,btntru,btnvalues;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttenspgiohang = view.findViewById(R.id.txtTenspgiohang);
            viewHolder.txtgiaspgiohang = view.findViewById(R.id.txtGiagiohang);
            viewHolder.imgspgiohang = view.findViewById(R.id.imgspgiohang);
            viewHolder.btncong = view.findViewById(R.id.btncong);
            viewHolder.btntru = view.findViewById(R.id.btntru);
            viewHolder.btnvalues = view.findViewById(R.id.btnvalues);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txttenspgiohang.setText(gioHang.tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaspgiohang.setText(decimalFormat.format(gioHang.giasp));
        Picasso.with(context).load(gioHang.getHinhsp()).into(viewHolder.imgspgiohang);
        viewHolder.btnvalues.setText(gioHang.getSoluongsp()+"");
        int sl = Integer.parseInt( viewHolder.btnvalues.getText().toString());
        if (sl >10){
            viewHolder.btncong.setVisibility(View.INVISIBLE);
            viewHolder.btntru.setVisibility(View.VISIBLE);
        }else  if (sl<=1){
            viewHolder.btntru.setVisibility(View.INVISIBLE);
        }else if(sl>1){
            viewHolder.btncong.setVisibility(View.VISIBLE);
            viewHolder.btntru.setVisibility(View.VISIBLE);
        }


        viewHolder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(viewHolder.btnvalues.getText().toString())+1;
                int slhientai= MainActivity.mangGiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(i).getGiasp();
                MainActivity.mangGiohang.get(i).setSoluongsp(slmoi);
                long giamoi = (giaht*slmoi)/slhientai;
                MainActivity.mangGiohang.get(i).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiaspgiohang.setText(decimalFormat.format(giamoi)+"D");
                Giohang.EvenUltil();
                if (slmoi>9){
                    viewHolder.btncong.setVisibility(View.INVISIBLE);
                    viewHolder.btntru.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoi));
                }
                else {
                    viewHolder.btncong.setVisibility(View.VISIBLE);
                    viewHolder.btntru.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoi));

                }
            }
        });
        viewHolder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int slmoi = Integer.parseInt(viewHolder.btnvalues.getText().toString())-1;
                int slhientai= MainActivity.mangGiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(i).getGiasp();
                MainActivity.mangGiohang.get(i).setSoluongsp(slmoi);
                long giamoi = (giaht*slmoi)/slhientai;
                MainActivity.mangGiohang.get(i).setGiasp(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiaspgiohang.setText(decimalFormat.format(giamoi)+"D");
                Giohang.EvenUltil();
                if (slmoi<2){
                    viewHolder.btncong.setVisibility(View.VISIBLE);
                    viewHolder.btntru.setVisibility(View.INVISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoi));
                }
                else {
                    viewHolder.btncong.setVisibility(View.VISIBLE);
                    viewHolder.btntru.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoi));
                }
            }
        });
        return view;
    }
}
