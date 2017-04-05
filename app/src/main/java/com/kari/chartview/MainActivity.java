package com.kari.chartview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kari.chart.ChartView;
import com.kari.chart.RingChartView;

public class MainActivity extends AppCompatActivity {

    RingChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = (RingChartView) findViewById(R.id.ringChat);

        int values[] = {3, 2, 1};
        String colors[] = {"#ff00dd", "#ffdd00", "#ccddcc"};
        chartView.setChartInfo(ChartView.ChartInfo.build(values, colors));
    }
}
