package com.example.app_banhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraysanpham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham_moi_nhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arraysanpham.get(position);
        holder.txtTenSP.setText(sanPham.Ten_SP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá : "+decimalFormat.format(sanPham.getGia_SP()) + "Đ");
        Picasso.with(context).load(sanPham.getAnh_SP()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView txtGiaSP,txtTenSP;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.imageviewsp);
            txtTenSP = (TextView) itemView.findViewById(R.id.txtsp);
            txtGiaSP = (TextView) itemView.findViewById(R.id.txtgiasp);

        }
    }
}
