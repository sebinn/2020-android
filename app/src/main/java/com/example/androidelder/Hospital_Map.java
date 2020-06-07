package com.example.androidelder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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

public class Hospital_Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 불러오기
    float Lat = 0;
    float Lng = 0;
    String hospital = "";

    //주소
    String address = "";
    //이름
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_map);

        Intent intent = getIntent();
        Lat = intent.getFloatExtra("Lat", 0);
        Lng = intent.getFloatExtra("Lng", 0);
        hospital = intent.getStringExtra("hospital");

        name = (TextView)findViewById(R.id.hos_map_name);
        name.setText(hospital);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hos_map2);
        mapFragment.getMapAsync(this);

    }
    public void onMapReady(GoogleMap map) {
        gMap = map;

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Hospital WHERE name = '" + hospital + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address = cursor.getString(4);
        }

        cursor.close();
        sqlDB.close();

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.snippet("주소 : " + address);
        markerOptions.position(new LatLng(Lat, Lng));
        gMap.addMarker(markerOptions);

        gMap.setOnMarkerClickListener(this);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Lat, Lng)));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    public boolean onMarkerClick(Marker marker){
        Toast.makeText(this, marker.getSnippet(), Toast.LENGTH_LONG).show();
        return true;
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