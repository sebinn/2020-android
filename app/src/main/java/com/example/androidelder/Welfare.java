package com.example.androidelder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Welfare extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    Spinner spinner;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout2;
    LinearLayout linearlayout;
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    // 디비
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    // 전체 리스트
    ArrayList<String> arrayList1;
    ArrayAdapter<String> arrayAdapter1;
    ListView listView1;

    // 검색 리스트
    ArrayList<String> arrayList2;
    ArrayAdapter<String> arrayAdapter2;
    ListView listView2;

    // 검색 아이콘
    ImageButton imgbtn;

    // 검색창
    EditText editText;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.welf_map);
        mapFragment.getMapAsync(this);

//        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.path_map);
//        mapFrag.getMapAsync(this);

        relativeLayout = (RelativeLayout)findViewById(R.id.welf_all_layout);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.welf_list_layout);
        linearlayout = (LinearLayout)findViewById(R.id.welf_map_layout);

        spinner = (Spinner)findViewById(R.id.spin_welf);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.search_list, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);
        linearlayout.setVisibility(View.INVISIBLE);

        //디비관련
        myDBHelper = new myDBHelper(this);
        sqlDB = myDBHelper.getReadableDatabase();
        final Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * From Welfare;", null);
        cursor.moveToFirst();

        int count = cursor.getCount();
        listView1 = (ListView)findViewById(R.id.welf_list_all);
        arrayList1 = new ArrayList<String>();
        arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        for(int k=0; k<count; k++){
            arrayList1.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        sqlDB.close();

        //이미지 버튼
        imgbtn = (ImageButton)findViewById(R.id.search_icon_welf);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = (EditText)findViewById(R.id.search_welf);
                text = editText.getText().toString();
                Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"선택된 아이템 : "+ s.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                if(i==0){
                    listView1.setAdapter(arrayAdapter1);
                    Toast.makeText(Welfare.this,"선택된 아이템 : "+ spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    linearlayout.setVisibility(View.INVISIBLE);
                } else if(i==1){
                    relativeLayout.setVisibility(View.INVISIBLE);
                    relativeLayout2.setVisibility(View.VISIBLE);
                    linearlayout.setVisibility(View.INVISIBLE);
                    imgbtn = (ImageButton)findViewById(R.id.search_icon_welf);
                    imgbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editText = (EditText)findViewById(R.id.search_welf);
                            text = editText.getText().toString();
                            if(text.length() > 0){

                                sqlDB = myDBHelper.getReadableDatabase();
                                final Cursor cursor;
                                cursor = sqlDB.rawQuery("SELECT * From Welfare WHERE name LIKE '%" + text + "%'", null);
                                cursor.moveToFirst();

                                int count = cursor.getCount();
                                listView2 = (ListView)findViewById(R.id.welf_list);
                                arrayList2 = new ArrayList<String>();
                                arrayAdapter2 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList2);

                                for(int k=0; k<count; k++){
                                    arrayList2.add(cursor.getString(0));
                                    cursor.moveToNext();
                                }
                                cursor.close();
                                sqlDB.close();

                                listView2.setAdapter(arrayAdapter2);

                                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        String store = "";
                                        float Lat = 0;
                                        float Lng = 0;
                                        sqlDB = myDBHelper.getReadableDatabase();
                                        Cursor cursor;
                                        cursor = sqlDB.rawQuery("SELECT * FROM Welfare WHERE name = '"+ arrayList2.get(i) + "';", null);
                                        cursor.moveToFirst();

                                        if(cursor.getCount() > 0){
                                            store = cursor.getString(0);
                                        }
                                        cursor.close();
                                        sqlDB.close();

                                        Intent intent = new Intent(getApplicationContext(), Welfare_Detail.class);
                                        intent.putExtra("store", store);
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                    });
                } else if(i==2){
                    Toast.makeText(Welfare.this,"선택된 아이템 : "+ spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    linearlayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });

        // 전체에서 리스트 뷰 클릭
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String store = "";
                float Lat = 0;
                float Lng = 0;
                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Welfare WHERE name = '"+ arrayList1.get(i) + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    store = cursor.getString(0);
                }
                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Welfare_Detail.class);
                intent.putExtra("store", store);
                startActivity(intent);
            }
        });
    }

    public void onMapReady(GoogleMap map) {
        gMap = map;
        String name = "";
        String address = "";
        String phoneNum = "";

        myDBHelper = new myDBHelper(this);
        sqlDB = myDBHelper.getReadableDatabase();
        MarkerOptions markerOptions = new MarkerOptions();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Welfare;", null);
        cursor.moveToFirst();
        int count = cursor.getCount();

        for(int i=0; i<count; i++){
            float lat = cursor.getFloat(8);
            float lng = cursor.getFloat(9);
            name = cursor.getString(0);
            address = cursor.getString(1);
            phoneNum = cursor.getString(6);

            markerOptions.position(new LatLng(lat, lng));
            markerOptions.snippet("업소명: " + name + "\n" + "주소: " + address + "\n" + "전화번호: " + phoneNum);
            gMap.addMarker(markerOptions);
            cursor.moveToNext();
        }

        cursor.close();
        sqlDB.close();
        gMap.setOnMarkerClickListener(this);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.945273, 126.682142)));
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