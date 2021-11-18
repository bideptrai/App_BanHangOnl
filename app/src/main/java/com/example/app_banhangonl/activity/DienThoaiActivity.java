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
import com.example.app_banhangonl.adapter.DienThoaiAdapter;
import com.example.app_banhangonl.model.SanPham;
import com.example.app_banhangonl.ultil.CheckConnection;
import com.example.app_banhangonl.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView listViewdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPham> mangdt;
    View footerview;
    boolean isLoading;
    boolean limitData = false;
    myHandler myHandler;
    int page = 1;
    int iddt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
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
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",mangdt.get(i));
                startActivity(intent);
            }
        });
        listViewdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem+VisibleItem == TotalItem && TotalItem !=0 && isLoading == false && limitData == false ){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
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
                String Ten_DT = "";
                int Gia_DT =0;
                String Anh_DT ="";
                String Mota_DT="";
                int Id_SPDT=0;
                if (response != null && response.length()>0)
                {
                    listViewdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("ID");
                            Ten_DT = jsonObject.getString("Ten_SP");
                            Gia_DT = jsonObject.getInt("Gia_SP");
                            Anh_DT = jsonObject.getString("Anh_SP");
                            Mota_DT = jsonObject.getString("Mo_ta_SP");
                            Id_SPDT = jsonObject.getInt("ID_LoaiSP");
                            mangdt.add(new SanPham(ID,Ten_DT,Gia_DT,Anh_DT,Mota_DT,Id_SPDT));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData = true;
                    listViewdt.removeFooterView(footerview);
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
                param.put("ID_LoaiSP",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
/*
  private void GetData() {
       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://lehieugb.000webhostapp.com/Server/getsp.php?page=1", new Response.Listener<JSONArray>() {
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
                           mangdt.add(new SanPham(ID,Ten_SP,Gia_SP,Anh_SP,Mota_Sp,ID_LoaiSP));
                           dienThoaiAdapter.notifyDataSetChanged();

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
*/



    private void ActionToolBar() {

        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    private void GetIdLoaiSp() {
        iddt = getIntent().getIntExtra("ID_LoaiSP",-1);
        Log.d("idloaisp", iddt+"");

    }

    private void Anhxa() {
        toolbardt =(Toolbar) findViewById(R.id.toolbardienthoai);
        listViewdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangdt);
        listViewdt.setAdapter(dienThoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        myHandler = new myHandler();

    }
    public class myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewdt.addFooterView(footerview);
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