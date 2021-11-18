package com.example.app_banhangonl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_banhangonl.R;
import com.example.app_banhangonl.adapter.LaptopAdapter;
import com.example.app_banhangonl.model.SanPham;
import com.example.app_banhangonl.ultil.CheckConnection;
import com.example.app_banhangonl.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlt;
    ListView listViewlt;
    LaptopAdapter laptopAdapter;
    ArrayList<SanPham> manglt;
    View footerview;
    boolean isLoading;
    boolean limitData = false;
    LaptopActivity.myHandler myHandler;
    int page = 1;
    int idlaptop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetIdLoaiSp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();

        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Lỗi mạng");
            finish();
        }
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
private void LoadMoreData() {
        listViewlt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",manglt.get(i));
                startActivity(intent);
            }
        });
        listViewlt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem+VisibleItem == TotalItem && TotalItem !=0 && isLoading == false && limitData == false ){
                    isLoading = true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void GetData( int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Sever.LinkDienThoai+Page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID =0 ;
                String Ten_LT = "";
                int Gia_LT =0;
                String Anh_LT ="";
                String Mota_LT="";
                int Id_SPLT=0;
                if (response != null && response.length() != 2)
                {
                    listViewlt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("ID");
                            Ten_LT = jsonObject.getString("Ten_SP");
                            Gia_LT = jsonObject.getInt("Gia_SP");
                            Anh_LT = jsonObject.getString("Anh_SP");
                            Mota_LT = jsonObject.getString("Mo_ta_SP");
                            Id_SPLT = jsonObject.getInt("ID_LoaiSP");
                            manglt.add(new SanPham(ID,Ten_LT,Gia_LT,Anh_LT,Mota_LT,Id_SPLT));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewlt.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Het san pham");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                HashMap<String,String> param = new HashMap<String,String >();
                param.put("ID_LoaiSP",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void ActionToolBar() {

        setSupportActionBar(toolbarlt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdLoaiSp() {
        idlaptop = getIntent().getIntExtra("ID_LoaiSP",-1);
        Log.d("idloaisp", idlaptop+"");

    }

    private void Anhxa() {
        toolbarlt =(Toolbar) findViewById(R.id.toolbarlaptop);
        listViewlt = findViewById(R.id.listviewlaptop);
        manglt = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),manglt);
        listViewlt.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        myHandler = new LaptopActivity.myHandler();
    }
    public class myHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewlt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading =false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message =myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}

