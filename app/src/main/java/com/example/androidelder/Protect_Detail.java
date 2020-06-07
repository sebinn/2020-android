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

public class Protect_Detail extends AppCompatActivity {

    // 지도 버튼
    Button map;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, address, phoneNum, mng_name, police, speed_limit, cctv, cctv_cnt, load_width;

    // 위도, 경도, 이름
    float Lat = 0;
    float Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protect_detail);

        Intent intent = getIntent();
        final String store = intent.getStringExtra("store");
        final int id = intent.getIntExtra("id", 0);

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.protect_name);
        address = (TextView)findViewById(R.id.protect_address);
        phoneNum = (TextView)findViewById(R.id.protect_mng_phone);
        mng_name = (TextView)findViewById(R.id.protect_mng);
        speed_limit = (TextView)findViewById(R.id.protect_speed_limit);
        cctv = (TextView)findViewById(R.id.protect_cctv);
        cctv_cnt = (TextView)findViewById(R.id.protect_cctv_cnt);
        load_width = (TextView)findViewById(R.id.protect_load);
        police = (TextView)findViewById(R.id.protect_police);

        name.setText(store);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Protect_elder WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address.setText(cursor.getString(1));
            phoneNum.setText(cursor.getString(6));
            mng_name.setText(cursor.getString(5));
            speed_limit.setText(cursor.getString(4));
            cctv.setText(cursor.getString(8));
            cctv_cnt.setText(cursor.getString(9));
            load_width.setText(cursor.getString(10));
            police.setText(cursor.getString(7));
        }
        cursor.close();
        sqlDB.close();

        map = (Button)findViewById(R.id.path_map_btn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Protect_elder WHERE name = '" + store + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    Lat = cursor.getFloat(2);
                    Lng = cursor.getFloat(3);
                }

                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Protect_Map.class);
                intent.putExtra("Lat", Lat);
                intent.putExtra("Lng", Lng);
                intent.putExtra("store", store);
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
