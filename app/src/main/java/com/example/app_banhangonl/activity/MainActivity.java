package com.example.app_banhangonl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_banhangonl.R;
import com.example.app_banhangonl.adapter.LoaiSpAdapter;
import com.example.app_banhangonl.adapter.SanPhamAdapter;
import com.example.app_banhangonl.model.LoaiSp;
import com.example.app_banhangonl.model.SanPham;
import com.example.app_banhangonl.ultil.CheckConnection;
import com.example.app_banhangonl.ultil.Sever;
import com.google.android.material.navigation.NavigationView;
import com.huawei.hms.maps.MapFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView ;
    NavigationView navigationview;
    ListView listView;
    DrawerLayout drawerLayout;

    ArrayList<LoaiSp> mangloaisp;
    LoaiSpAdapter loaiSpAdapter;
    MapFragment mMapFragment;


    int ID = 0;
    String Ten_SP = "";
    String Anh_Sp = "";

    ArrayList<SanPham> mangsp;
    SanPhamAdapter sanPhamAdapter;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetLoaiSP();
            GetSPMoiNhat();
            CatchOnItemListView();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối ");
            finish();
        }
}

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("ID_LoaiSP",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("ID_LoaiSP",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.LinkSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID = 0;
                    String Ten_SP="";
                    int Gia_SP = 0;
                    String Anh_SP ="";
                    String Mota_Sp ="";
                    int ID_LoaiSP = 0;
                    for (int i = 0 ;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("ID");
                            Ten_SP = jsonObject.getString("Ten_SP");
                            Gia_SP = jsonObject.getInt("Gia_SP");
                            Anh_SP = jsonObject.getString("Anh_SP");
                            Mota_Sp = jsonObject.getString("Mo_ta_SP");
                            ID_LoaiSP = jsonObject.getInt("ID_LoaiSP");
                            SanPham sp = new SanPham(ID,Ten_SP,Gia_SP,Anh_SP,Mota_Sp,ID_LoaiSP);
                            mangsp.add(new SanPham(ID,Ten_SP,Gia_SP,Anh_SP,Mota_Sp,ID_LoaiSP));
                            sanPhamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetLoaiSP() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.LinkLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i= 0 ; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("ID");
                            Ten_SP = jsonObject.getString("Ten_SP");
                            Anh_Sp = jsonObject.getString("Anh_SP");
                            mangloaisp.add(new LoaiSp(ID,Ten_SP,Anh_Sp));
                            loaiSpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new LoaiSp(0,"Liên hệ","https://banner2.cleanpng.com/20180331/fae/kisspng-iphone-telephone-logo-computer-icons-clip-art-contact-5abf09868e2c44.1573922815224692545824.jpg  "));
                    mangloaisp.add(4,new LoaiSp(0,"Thông tin","https://e7.pngegg.com/pngimages/140/851/png-clipart-exclamation-mark-question-mark-computer-icons-symbol-attention-miscellaneous-city.png "));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://st.quantrimang.com/photos/image/2018/07/14/quang-cao-smartphone-650.jpg");
        mangquangcao.add("https://genk.mediacdn.vn/2019/6/22/promotiondoublestorage-1561186618199280355933.jpg");
        mangquangcao.add("https://i.ytimg.com/vi/v8tvXnQiFMo/maxresdefault.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Files/2021/03/09/1333890/lisa2_800x629.jpg");
        for (int i =0 ; i<mangquangcao.size();i++)
        {
            ImageView imageView =  new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_side_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.side_in_right);
        Animation animation_side_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.side_out_right);
        viewFlipper.setInAnimation(animation_side_in);
        viewFlipper.setOutAnimation(animation_side_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {

        toolbar = (Toolbar) findViewById(R.id.toolbartrangchu);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflippertrangchu);
        recyclerView = (RecyclerView)  findViewById(R.id.recyclerview);
        navigationview = (NavigationView) findViewById(R.id.navigationview);
        listView = (ListView) findViewById(R.id.listviewtrangchu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new LoaiSp(0,"Trang chủ","https://png.pngtree.com/png-vector/20190129/ourlarge/pngtree-home-icon-graphic-design-template-vector-png-image_358126.jpg"));
        loaiSpAdapter = new LoaiSpAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaiSpAdapter);
        mangsp = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
    }
    }