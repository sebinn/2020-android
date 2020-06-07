package com.example.androidelder;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Record_Write extends AppCompatActivity {

    // dB 관련
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    // 에디트 텍스트
    EditText title, hos_name, doctor, checkup, precautions;

    //버튼
    Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_write);

        title = (EditText) findViewById(R.id.record_title_wr);
        hos_name = (EditText) findViewById(R.id.hos_name_wr);
        doctor = (EditText) findViewById(R.id.doctor_wr);
        checkup = (EditText) findViewById(R.id.checkup_wr);
        precautions = (EditText) findViewById(R.id.precautions_wr);

        add_btn = (Button)findViewById(R.id.record_btn);
        myHelper = new myDBHelper(this);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO Record (title, hos_name, doctor, checkup_name, precautions) VALUES( '" + title.getText().toString() +"', '"
                        + hos_name.getText().toString() + "', '"
                        + doctor.getText().toString() +  "','"
                        + checkup.getText().toString() +  "','"
                        + precautions.getText().toString() + "');");
                sqlDB.close();

                Intent intent2 = getIntent();
                intent2.putExtra(title.getText().toString(), "return");
                setResult(RESULT_OK, intent2);
                Toast.makeText(getApplicationContext(), "저장했습니다!!", Toast.LENGTH_SHORT).show();
                finish();
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
