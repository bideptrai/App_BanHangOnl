package com.example.app_banhangonl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_banhangonl.R;
import com.example.app_banhangonl.ultil.CheckConnection;
import com.example.app_banhangonl.ultil.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKH extends AppCompatActivity {
    EditText edtTenKH,edtemail,edtsdt;
    Button btnxacnhan,btntrove;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_kh);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hay kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String ten = edtTenKH.getText().toString().trim();
               final String sdt = edtsdt.getText().toString().trim();
               final String email = edtemail.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 &&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.LinkDonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ID_donhang) {
                            Log.d("madonhang",ID_donhang);
                            if (Integer.parseInt(ID_donhang)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Sever.LinkChitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("thanhcong")){
                                            MainActivity.mangGiohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã đặt hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }
                                        else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i =0 ;i<MainActivity.mangGiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("ID_donhang",ID_donhang);
                                                jsonObject.put("ID_sanpham",MainActivity.mangGiohang.get(i).getIdsp());
                                                jsonObject.put("Ten_sanpham",MainActivity.mangGiohang.get(i).getTensp());
                                                jsonObject.put("Gia_sanpham",MainActivity.mangGiohang.get(i).getGiasp());
                                                jsonObject.put("Soluong",MainActivity.mangGiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String>hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sdt",sdt);
                            hashMap.put("email",email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Kiem tra lai du lieu");
                }
            }
        });
    }

    private void Anhxa() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtemail = findViewById(R.id.edtEmail);
        edtsdt = findViewById(R.id.edtSDT);
        btnxacnhan = findViewById(R.id.btnXacnhan);
        btntrove = findViewById(R.id.btnTrove);

    }
}