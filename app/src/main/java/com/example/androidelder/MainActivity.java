package com.example.androidelder;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;

import com.example.androidelder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

//public class MainActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.alarm_time);
//    }
//}
//

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends TabActivity {


    // 탭 호스트 변수 선언
    TabHost tabHost;
    ImageView tabwg1;
    ImageView tabwg2;
    ImageView tabwg3;

    // 이미지 버튼 변수 (메뉴)
    // 탭1

    //경로우대
    ImageButton btn_cane;
    //교통약지 이동지원
    ImageButton btn_traff;
    //노인장애인 보호구역
    ImageButton btn_protect;
    //병원
    ImageButton btn_hosp;

    //탭2
    //경로당
    ImageButton btn_elder;
    //약수터
    ImageButton btn_mineral;
    //실외운동기구
    ImageButton btn_health;
    //복지센터
    ImageButton btn_welfare;

    //탭3
    Button btn_record;
    Button btn_alarm;
    Button plus_btn;
    Button plus_btn2;

    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;

    // 진료기록 리스트
    ArrayList<String> arrayList1;
    ArrayAdapter<String> arrayAdapter1;
    ListView listView1;

    // 알람 리스트
    ArrayList<String> arrayList2;
    ArrayAdapter<String> arrayAdapter2;
    ListView listView2;

    // 디비
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    //알람
    AlarmManager timeManager;
    PendingIntent timependingIntent;
    Intent timeIntent;

    // 다이얼로그
    EditText alarm_text;
    public static String alarm_text2;
    TimePicker timePicker;
    DatePicker datePicker;
    View timeView;

    //날짜
    SimpleDateFormat format;
    Date time;
    String curtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = getTabHost();
        tabHost.getTabWidget().setDividerDrawable(null); // 탭라인 제거

        tabwg1 = new ImageView(this);
        tabwg1.setImageResource(R.drawable.tab_01);

        tabwg2 = new ImageView(this);
        tabwg2.setImageResource(R.drawable.tab_02);

        tabwg3 = new ImageView(this);
        tabwg3.setImageResource(R.drawable.tab_03);

        TabHost.TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator(tabwg1);
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);

        TabHost.TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator(tabwg2);
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);

        TabHost.TabSpec tabSpecTab3 = tabHost.newTabSpec("TAB3").setIndicator(tabwg3);
        tabSpecTab3.setContent(R.id.tab3);
        tabHost.addTab(tabSpecTab3);

        tabHost.setCurrentTab(0);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        // 탭 배경색 설정!(빈공간 생기는 것 때문에)
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#fdf9f9"));
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override

            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#ffffff"));
                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#fdf9f9"));
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#ffffff"));
                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#fdf9f9"));
            }

        });

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (screenHeight * 15) / 200;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = (screenHeight * 15) / 200;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = (screenHeight * 15) / 200;


        // 메뉴 이동
        // 경로우대
        btn_cane = (ImageButton)findViewById(R.id.cane);
        btn_cane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), Path_old.class);
                startActivity(intent1);
            }
        });

        //약수터
        btn_mineral = (ImageButton)findViewById(R.id.mineral);
        btn_mineral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), Mineral.class);
                startActivity(intent2);
            }
        });

        //실외 운동기구
        btn_health = (ImageButton)findViewById(R.id.health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Health.class);
                startActivity(intent3);
            }
        });

        //교통약자 이동지원
        btn_traff = (ImageButton)findViewById(R.id.traffic);
        btn_traff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), Traffic.class);
                startActivity(intent4);
            }
        });

        //노인장애인 보호구역
        btn_protect = (ImageButton)findViewById(R.id.protect);
        btn_protect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(), Protect_elder.class);
                startActivity(intent5);
            }
        });

        //병원
        btn_hosp = (ImageButton)findViewById(R.id.hospital);
        btn_hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(getApplicationContext(), Hospital.class);
                startActivity(intent6);
            }
        });

        // 경로당
        btn_elder = (ImageButton)findViewById(R.id.elder);
        btn_elder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(getApplicationContext(), Elder_Group.class);
                startActivity(intent7);
            }
        });

        // 복지시설
        btn_welfare = (ImageButton)findViewById(R.id.welfare);
        btn_welfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(getApplicationContext(), Welfare.class);
                startActivity(intent8);
            }
        });


        // 탭3
        relativeLayout1 = (RelativeLayout)findViewById(R.id.record_list_layout);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.alarm_list_layout);

        relativeLayout1.setVisibility(View.VISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);

        btn_record = (Button)findViewById(R.id.button1);
        btn_alarm = (Button)findViewById(R.id.button2);
        plus_btn = (Button)findViewById(R.id.plusBtn);
        plus_btn2 = (Button)findViewById(R.id.plusBtn2);

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.INVISIBLE);
            }
        });

        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout1.setVisibility(View.INVISIBLE);
                relativeLayout2.setVisibility(View.VISIBLE);
            }
        });

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Record_Write.class);
                startActivityForResult(intent, 0);
            }
        });


        // 디비관련 진료기록
        myHelper = new myDBHelper(this);

        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM Record;", null);
        cursor.moveToFirst();

        int count = cursor.getCount();
        listView1 = (ListView)findViewById(R.id.record_list);
        arrayList1 = new ArrayList<String>();
        arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        for(int i=0; i<count; i++){
            arrayList1.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        sqlDB.close();

        listView1.setAdapter(arrayAdapter1);

        // 디비관련 알람
        myHelper = new myDBHelper(this);

        sqlDB = myHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM Alarm;", null);
        cursor.moveToFirst();

        count = cursor.getCount();
        listView2 = (ListView)findViewById(R.id.alarm_list);
        arrayList2 = new ArrayList<String>();
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        for(int i=0; i<count; i++){
            arrayList2.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        sqlDB.close();

        listView2.setAdapter(arrayAdapter2);

        // 전체에서 리스트 뷰 클릭
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "";
                int id = 0;
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM Record WHERE title = '"+ arrayList1.get(i) + "';", null);
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    title = cursor.getString(0);
                    id = cursor.getInt(5);
                } else{
                    title = "없어.";
                }
                cursor.close();
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), Record_Detail.class);
                intent.putExtra("title", title);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        plus_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timeView = (View) View.inflate(MainActivity.this, R.layout.alarm_time, null);
                AlertDialog.Builder timedlg = new AlertDialog.Builder(MainActivity.this);
                timedlg.setTitle("알람 설정");
                timedlg.setView(timeView);

                timedlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alarm_text = (EditText)timeView.findViewById(R.id.alarm_text);
                        alarm_text2 = alarm_text.getText().toString();
                        timePicker = (TimePicker) timeView.findViewById(R.id.timepicker);
                        timePicker.setIs24HourView(true);
                        datePicker = (DatePicker) timeView.findViewById(R.id.datepicker);

                        int date = datePicker.getDayOfMonth();
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;

                        int hour1 = timePicker.getCurrentHour();
                        int minute1 = timePicker.getCurrentMinute();

                        String cal = year + "-" + month + "-" + date;
                        String timep = hour1 + ":" + minute1;

                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO Alarm (alarmtext, alarm_date, alarm_time) VALUES( '" + alarm_text2 + "', '"
                                        + cal + "', '" + timep + "');");
                        sqlDB.close();

                        arrayList2.add(alarm_text2);
                        arrayAdapter2.notifyDataSetChanged();

                        int hour, hour_24, minute;
                        String am_pm;
                        if (Build.VERSION.SDK_INT >= 23 ){
                            hour_24 = timePicker.getHour();
                            minute = timePicker.getMinute();
                        }
                        else{
                            hour_24 = timePicker.getCurrentHour();
                            minute = timePicker.getCurrentMinute();
                        }
                        if(hour_24 > 12) {
                            am_pm = "PM";
                            hour = hour_24 - 12;
                        }
                        else
                        {
                            hour = hour_24;
                            am_pm="AM";
                        }

                        // 현재 지정된 시간으로 알람 시간 설정
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                        if (calendar.before(Calendar.getInstance())) {
                            calendar.add(Calendar.DATE, 1);
                        }

                        Date currentDateTime = calendar.getTime();
                        String date_text2 = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                        Toast.makeText(getApplicationContext(),date_text2 + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                        //  Preference에 설정한 값 저장
                        SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                        editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                        editor.apply();

                        diaryNotification(calendar);


                    }
                });
                timedlg.show();
            }
        });

    }

    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, TimeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            sqlDB = myHelper.getReadableDatabase();
            Cursor cursor = sqlDB.rawQuery("SELECT * FROM Record;", null);
            cursor.moveToFirst();

            int count = cursor.getCount();
            listView1 = (ListView)findViewById(R.id.record_list);
            arrayList1 = new ArrayList<String>();
            arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
            for(int i=0; i<count; i++){
                arrayList1.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
            sqlDB.close();
            listView1.setAdapter(arrayAdapter1);
            arrayAdapter1.notifyDataSetChanged();
        }
    }
}