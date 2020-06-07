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

public class Traffic_Detail extends AppCompatActivity {

    // 지도 버튼
    Button map;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, address, phoneNum, car_count, slope, lift, site, weekday_reserve, weekend_reserve, weekday_car,
            weekend_car, car_limit, target, car_fare, mng_name, mng_phoneNum;

    // 위도, 경도
    float Lat = 0;
    float Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traffic_detail);

        Intent intent = getIntent();
        final String store = intent.getStringExtra("store");

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.traff_name);
        address = (TextView)findViewById(R.id.traff_address);
        phoneNum = (TextView)findViewById(R.id.traff_phone);
        car_count = (TextView)findViewById(R.id.traff_car_count);
        slope = (TextView)findViewById(R.id.traff_slope);
        lift = (TextView)findViewById(R.id.traff_lift);
        site = (TextView)findViewById(R.id.traff_site);
        weekday_reserve = (TextView)findViewById(R.id.traff_reserve_weekday);
        weekend_reserve = (TextView)findViewById(R.id.traff_reserve_weekend);
        weekday_car = (TextView)findViewById(R.id.traff_car_weekday);
        weekend_car = (TextView)findViewById(R.id.traff_car_weekend);
        car_limit = (TextView)findViewById(R.id.traff_limit);
        target = (TextView)findViewById(R.id.traff_target);
        car_fare = (TextView)findViewById(R.id.traff_fare);
        mng_name = (TextView)findViewById(R.id.traff_mng);
        mng_phoneNum = (TextView)findViewById(R.id.traff_mng_phone);

        name.setText(store);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Vehicle_support WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address.setText(cursor.getString(1));
            phoneNum.setText(cursor.getString(8));
            car_count.setText(cursor.getString(4));
            slope.setText(cursor.getString(6));
            lift.setText(cursor.getString(7));
            site.setText(cursor.getString(9));
            weekday_reserve.setText(cursor.getString(11) + "~" + cursor.getString(12));
            weekend_reserve.setText(cursor.getString(13)+ "~" + cursor.getString(14));
            weekday_car.setText(cursor.getString(15)+ "~" + cursor.getString(16));
            weekend_car.setText(cursor.getString(17)+ "~" + cursor.getString(18));
            car_limit.setText(cursor.getString(20));
            target.setText(cursor.getString(23));
            car_fare.setText(cursor.getString(24));
            mng_name.setText(cursor.getString(25));
            mng_phoneNum.setText(cursor.getString(26));
        }
        cursor.close();
        sqlDB.close();

        map = (Button)findViewById(R.id.traff_map_btn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Vehicle_support WHERE name = '" + store + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    Lat = cursor.getFloat(2);
                    Lng = cursor.getFloat(3);
                }

                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Traffic_Map.class);
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
