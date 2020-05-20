package com.example.androidelder;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;

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
    ImageButton btn_traf;
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

    }
}
//public class MainActivity extends Activity {
//    Spinner s;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_path);
//
//        final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.list_layout);
//        final LinearLayout linearlayout = (LinearLayout)findViewById(R.id.path_map);
//
////        final ListView listview = (ListView)findViewById(R.id.path_list);
////        final TextView textview = (TextView)findViewById(R.id.hiroo);
//
//        s = (Spinner)findViewById(R.id.search_spin);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.search_list, android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
//
//        relativeLayout.setVisibility(View.VISIBLE);
//        linearlayout.setVisibility(View.INVISIBLE);
//
//        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(MainActivity.this,"선택된 아이템 : "+ s.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
//                if(i==0){
//                    Toast.makeText(MainActivity.this,"선택된 아이템 : "+ s.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
//                    relativeLayout.setVisibility(View.VISIBLE);
//                    linearlayout.setVisibility(View.INVISIBLE);
//                } else if(i==1){
//                    Toast.makeText(MainActivity.this,"선택된 아이템 : "+ s.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
//                    relativeLayout.setVisibility(View.INVISIBLE);
//                    linearlayout.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                relativeLayout.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//}