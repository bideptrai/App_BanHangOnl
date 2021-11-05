package com.example.app_banhangonl.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.app_banhangonl.R;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.Marker;

public class LienHeActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    TextView textView;
    HuaweiMap hMap;
    Marker mMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        Anhxa();
        ActionToolBar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "sdk >= 23 M");
            // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permissions for your app.
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                // Request permissions.
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        }

       if (null != mMarker) {
            mMarker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(19.956988, 105.857590))
                .title("Hello Huawei Map")
                .snippet("This is a snippet!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconvt));

        mMarker = hMap.addMarker(options);*/

    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION})
    @Override
    public void onMapReady(HuaweiMap map){
        Log.d(TAG, "onMapReady: ");
        HuaweiMap hMap = map;
        // Enable the my-location layer.
        hMap.setMyLocationEnabled(true);
        // Enable the my-location icon. 
        hMap.getUiSettings().setMyLocationButtonEnabled(true);
        hMap.setPadding(20 ,40,50,60);
    }
    private void ActionToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Anhxa(){
        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment_mapfragmentdemo);
        mMapFragment.getMapAsync(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarlienhe);
        textView = (TextView) findViewById(R.id.txtlienhe);

    }

}