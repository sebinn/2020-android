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

public class Welfare_Detail extends AppCompatActivity {

    // 지도 버튼
    Button map;

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView name, address, operate, establish_date, land_area, manager, phoneNum, source;

    // 위도, 경도, 이름
    float Lat = 0;
    float Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welfare_detail);

        Intent intent = getIntent();
        final String store = intent.getStringExtra("store");

        myHelper = new myDBHelper(this);
        name = (TextView)findViewById(R.id.welfare_name);
        address = (TextView)findViewById(R.id.welfare_address);
        phoneNum = (TextView)findViewById(R.id.welfare_phone);
        operate = (TextView)findViewById(R.id.welfare_oper);
        establish_date = (TextView)findViewById(R.id.welfare_esta);
        land_area = (TextView)findViewById(R.id.welfare_area);
        manager = (TextView)findViewById(R.id.welfare_mng);
        source = (TextView)findViewById(R.id.welfare_src);

        name.setText(store);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Welfare WHERE name = '" + store + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            address.setText(cursor.getString(1));
            phoneNum.setText(cursor.getString(6));
            operate.setText(cursor.getString(2));
            establish_date.setText(cursor.getString(3));
            land_area.setText(cursor.getString(4));
            manager.setText(cursor.getString(5));
            source.setText(cursor.getString(7));
        }
        cursor.close();
        sqlDB.close();

        map = (Button)findViewById(R.id.welfare_map_btn);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Welfare WHERE name = '" + store + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    Lat = cursor.getFloat(8);
                    Lng = cursor.getFloat(9);
                }

                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Welfare_Map.class);
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
