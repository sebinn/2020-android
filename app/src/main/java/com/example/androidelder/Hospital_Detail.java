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

import androidx.appcompat.app.AppCompatActivity;

public class Hospital_Detail extends AppCompatActivity {

    // 지도 버튼
    Button map;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, hos_type, phoneNum, postcode, address, site;

    // 위도, 경도, 이름
    float Lat = 0;
    float Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_detail);

        Intent intent = getIntent();
        final String hospital = intent.getStringExtra("hospital");

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.hos_name);
        hos_type = (TextView)findViewById(R.id.hos_type);
        address = (TextView)findViewById(R.id.hos_address);
        phoneNum = (TextView)findViewById(R.id.hos_phone);
        postcode = (TextView)findViewById(R.id.hos_postcode);
        site = (TextView)findViewById(R.id.hos_site);

        name.setText(hospital);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Hospital WHERE name = '" + hospital + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            hos_type.setText(cursor.getString(1));
            address.setText(cursor.getString(4));
            phoneNum.setText(cursor.getString(2));
            postcode.setText(cursor.getString(3));
            site.setText(cursor.getString(5));
        }
        cursor.close();
        sqlDB.close();

        map = (Button)findViewById(R.id.path_map_btn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Hospital WHERE name = '" + hospital + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    Lat = cursor.getFloat(6);
                    Lng = cursor.getFloat(7);
                }

                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Hospital_Map.class);
                intent.putExtra("Lat", Lat);
                intent.putExtra("Lng", Lng);
                intent.putExtra("hospital", hospital);
                startActivity(intent);
            }
        });
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
