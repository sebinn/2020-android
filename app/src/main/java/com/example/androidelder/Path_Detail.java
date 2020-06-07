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

public class Path_Detail extends AppCompatActivity {

    // 지도 버튼
    Button map;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, address, phoneNum, min_age, discount, dis_amount, dis_target, weekday, weekend, holiday, mng_name, mng_phoneNum;

    // 위도, 경도, 이름
    float Lat = 0;
    float Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_detail);

        Intent intent = getIntent();
        final String store = intent.getStringExtra("store");

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.path_name);
        address = (TextView)findViewById(R.id.path_address);
        phoneNum = (TextView)findViewById(R.id.path_phone);
        min_age = (TextView)findViewById(R.id.path_min_age);
        discount = (TextView)findViewById(R.id.path_discount);
        dis_amount = (TextView)findViewById(R.id.path_dis_amount);
        dis_target = (TextView)findViewById(R.id.path_dis_target);
        weekday = (TextView)findViewById(R.id.path_weekday_open);
        weekend = (TextView)findViewById(R.id.path_weekend_open);
        holiday = (TextView)findViewById(R.id.path_holiday_open);
        mng_name = (TextView)findViewById(R.id.path_mng_name);
        mng_phoneNum = (TextView)findViewById(R.id.path_mng_phone);

        name.setText(store);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Prefer_elder WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address.setText(cursor.getString(1));
            phoneNum.setText(cursor.getString(4));
            min_age.setText(cursor.getString(5));
            discount.setText(cursor.getString(6));
            dis_amount.setText(cursor.getString(7));
            dis_target.setText(cursor.getString(8));
            weekday.setText(cursor.getString(10));
            weekend.setText(cursor.getString(12));
            holiday.setText(cursor.getString(14));
            mng_name.setText(cursor.getString(17));
            mng_phoneNum.setText(cursor.getString(18));
        }
        cursor.close();
        sqlDB.close();

        map = (Button)findViewById(R.id.path_map_btn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Prefer_elder WHERE name = '" + store + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    Lat = cursor.getFloat(2);
                    Lng = cursor.getFloat(3);
                }

                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Path_Map.class);
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
