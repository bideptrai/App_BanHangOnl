package com.example.app_banhangonl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.model.GioHang;
import com.example.app_banhangonl.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Chitietsanpham extends AppCompatActivity {
    Toolbar toolbarctsp;
    ImageView imageViewctsp;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id = 0;
    String Tenchitiet = "";
    int Gia=0;
    String Hinhanh = "";
    String Mota = "";
    int IdLoaiSp =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventClickButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventClickButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGiohang.size()>0){
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString()) ;
                    boolean exists = false;
                    for (int i = 0 ;i<MainActivity.mangGiohang.size();i++){
                        if (MainActivity.mangGiohang.get(i).idsp == id){
                            MainActivity.mangGiohang.get(i).setSoluongsp(MainActivity.mangGiohang.get(i).soluongsp+soluong);
                            MainActivity.mangGiohang.get(i).setGiasp(Gia*MainActivity.mangGiohang.get(i).soluongsp);
                            exists=true;
                        }
                    }
                    //khong tim thay san pham nay trong gio hang
                    if (exists==false){
                        int sl =Integer.parseInt(spinner.getSelectedItem().toString()) ;
                        int tongtien = sl*Gia;
                        MainActivity.mangGiohang.add(new GioHang(id,Tenchitiet,tongtien,Hinhanh,sl));
                    }
                }else {
                    int sl =Integer.parseInt(spinner.getSelectedItem().toString()) ;
                    int tongtien = sl*Gia;
                    MainActivity.mangGiohang.add(new GioHang(id,Tenchitiet,tongtien,Hinhanh,sl));
                }
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getID();
        Tenchitiet = sanPham.Ten_SP;
        Gia = sanPham.Gia_SP;
        Hinhanh =sanPham.Anh_SP;
        Mota = sanPham.Mota_SP;
        IdLoaiSp = sanPham.ID_LoaiSP;
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá : "+ decimalFormat.format(Gia) + "Đ");
        txtmota.setText(Mota);
        Picasso.with(getApplicationContext()).load(Hinhanh).into(imageViewctsp);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarctsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarctsp =  findViewById(R.id.toolbarctsp);
        imageViewctsp = findViewById(R.id.imgctsp);
        txtten = findViewById(R.id.txttenctsp);
        txtgia = findViewById(R.id.txtgiactsp);
        txtmota = findViewById(R.id.txtmotactsp);
        spinner =  findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.btngiohang);
    }
}