package com.example.app_banhangonl.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView listViewdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPham> mangdt;
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
            GetData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Lỗi mạng");
            finish();
        }
    }
   /* private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Sever.LinkDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID =0 ;
                String Ten_DT = "";
                int Gia_DT =0;
                String Anh_DT ="";
                String Mota_DT="";
                int Id_SPDT=0;
                if (response != null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<mangdt.size();i++){
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String ,String >();
                param.put("ID_LoaiSP",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }*/
   private void GetData() {
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
        listViewdt =(ListView) findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangdt);
        listViewdt.setAdapter(dienThoaiAdapter);

    }
}