package com.example.androidelder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Record_Detail extends AppCompatActivity {

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 텍스트뷰
    TextView title, hos_name, doctor, checkup, precautions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_detail);

        Intent intent = getIntent();
        final String title_in = intent.getStringExtra("title");
        final int id = intent.getIntExtra("id", 0);

        title = (TextView)findViewById(R.id.record_title);
        hos_name = (TextView)findViewById(R.id.hos_name);
        doctor = (TextView)findViewById(R.id.doctor);
        checkup = (TextView)findViewById(R.id.checkup);
        precautions = (TextView)findViewById(R.id.precautions);

        myHelper = new myDBHelper(this);

        title.setText(title_in);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM Record WHERE rcdNum = '" + id + "';", null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            hos_name.setText(cursor.getString(1));
            doctor.setText(cursor.getString(2));
            checkup.setText(cursor.getString(3));
            precautions.setText(cursor.getString(4));
        }
        cursor.close();
        sqlDB.close();
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
