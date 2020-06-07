package com.example.androidelder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Eldergroup_Detail extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, address, phoneNum, mng;

    // 위도, 경도, 이름
    float Lat = 0;
    float Lng = 0;

    String store = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eldergroup_detail);

        Intent intent = getIntent();
        store = intent.getStringExtra("store");

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.elder_map2);
        mapFragment.getMapAsync(this);

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.elder_name);
        address = (TextView)findViewById(R.id.elder_address);
        phoneNum = (TextView)findViewById(R.id.elder_phone);
        mng = (TextView)findViewById(R.id.elder_mng);

        name.setText(store);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM SrCenter WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address.setText(cursor.getString(2));
            phoneNum.setText(cursor.getString(3));
            mng.setText(cursor.getString(1));
        }
        cursor.close();
        sqlDB.close();
    }

    public void onMapReady(GoogleMap map){
        gMap = map;

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM SrCenter WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            Lat = cursor.getFloat(5);
            Lng = cursor.getFloat(4);
        }

        cursor.close();
        sqlDB.close();

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.snippet("주소 : " + address);
        markerOptions.position(new LatLng(Lat, Lng));
        gMap.addMarker(markerOptions);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Lat, Lng)));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "Elder.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
