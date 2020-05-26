package com.example.androidelder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mineral extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mineral);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mineral_map);
        mapFragment.getMapAsync(this);

    }
    public void onMapReady(GoogleMap map) {
        gMap = map;

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        MarkerOptions markerOptions = new MarkerOptions();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT Name, Lat, Lng, address, insp_date, bool, reason  FROM Mineral;", null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        for(int i=0; i<count; i++){
            float lat = cursor.getFloat(1);
            float lng = cursor.getFloat(2);
            String title = cursor.getString(0);
            String add = cursor.getString(3);
            String date = cursor.getString(4);
            String bool = cursor.getString(5);
            String reason = cursor.getString(6);
            markerOptions.position(new LatLng(lat, lng));
            if (bool.equals("적합")) {
                String text = title + "\n" + "주소: " + add + "수질검사일자: " + date + "\n" + "적합여부: " + bool;
                markerOptions.snippet(text);
            } else {
                String text = title + "\n" + "주소: " + add + "수질검사일자: " + date + "\n" + "적합여부: " + bool + "\n" + "부적합 이유: " + reason;
                markerOptions.snippet(text);
            }
            gMap.addMarker(markerOptions);
            cursor.moveToNext();
        }

        cursor.close();
        sqlDB.close();

        gMap.setOnMarkerClickListener(this);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.75827969, 127.1299364606)));
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