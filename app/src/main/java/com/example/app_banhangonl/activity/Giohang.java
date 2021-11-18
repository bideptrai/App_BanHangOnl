package com.example.app_banhangonl.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_banhangonl.R;
import com.example.app_banhangonl.adapter.GiohangAdapter;
import com.example.app_banhangonl.ultil.CheckConnection;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtThongbao;
    static TextView txttongtien;
    Button btnthanhtoan,btntieptuc;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolBar();
        CheckData();
        EvenUltil();
        CatchOnItemList();
        EvenButton();
        
    }

    private void EvenButton() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGiohang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(),ThongTinKH.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng trống");
                }
            }
        });
    }

    private void CatchOnItemList() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xóa sản phẩm này khỏi giỏ hàng ?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (MainActivity.mangGiohang.size()<=0){
                                    txtThongbao.setVisibility(View.VISIBLE);
                                }
                                else {
                                    MainActivity.mangGiohang.remove(position);
                                    giohangAdapter.notifyDataSetChanged();
                                    EvenUltil();
                                    if (MainActivity.mangGiohang.size()<=0){
                                        txtThongbao.setVisibility(View.VISIBLE);
                                    }else {
                                        txtThongbao.setVisibility(View.INVISIBLE);
                                        giohangAdapter.notifyDataSetChanged();
                                        EvenUltil();
                                    }

                                }
                            }
                        });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();


                return true;
            }
        });
    }

    public static void EvenUltil() {
        long tongtien =0;
        for (int i= 0;i<MainActivity.mangGiohang.size();i++){
            tongtien += MainActivity.mangGiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+"Đ");

    }

    private void CheckData() {
        if (MainActivity.mangGiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }
        else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void Anhxa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtThongbao = findViewById(R.id.txtthongbao);
        txttongtien = findViewById(R.id.txttongtien);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);
        btntieptuc = findViewById(R.id.btntieptucmua);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(Giohang.this,MainActivity.mangGiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}