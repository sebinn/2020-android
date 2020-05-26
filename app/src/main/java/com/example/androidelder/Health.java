package com.example.androidelder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Health extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.health_map);
        mapFragment.getMapAsync(this);

    }
    public void onMapReady(GoogleMap map) {
        gMap = map;

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        MarkerOptions markerOptions = new MarkerOptions();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT address, equip, Lng, Lat  FROM Health;", null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        for(int i=0; i<count; i++){
            float lat = cursor.getFloat(3);
            float lng = cursor.getFloat(2);
            String add = cursor.getString(0);
            String equip = cursor.getString(1);
            markerOptions.position(new LatLng(lat, lng));
            markerOptions.snippet("주소: " + add + "\n\n" + "설치기구 종류: " + equip);
            gMap.addMarker(markerOptions);
            cursor.moveToNext();
        }

        cursor.close();
        sqlDB.close();

        gMap.setOnMarkerClickListener(this);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(36.047078, 126.857074)));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    public boolean onMarkerClick(Marker marker){
        Toast toast = Toast.makeText(getApplicationContext(), marker.getSnippet(), Toast.LENGTH_LONG);
        View viewtoast = toast.getView();
        viewtoast.setBackgroundColor(Color.rgb(255,255,255));
        toast.show();
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