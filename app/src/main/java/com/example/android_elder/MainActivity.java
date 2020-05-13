package com.example.android_elder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabHost tabHost = getTabHost();
        tabHost.getTabWidget().setDividerDrawable(null); // 탭라인 제거

        ImageView tabwg1 = new ImageView(this);
        tabwg1.setImageResource(R.drawable.tab_01);

        ImageView tabwg2 = new ImageView(this);
        tabwg2.setImageResource(R.drawable.tab_02);

        ImageView tabwg3 = new ImageView(this);
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
    }
}
