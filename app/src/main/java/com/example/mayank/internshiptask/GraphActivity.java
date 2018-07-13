package com.example.mayank.internshiptask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mGraph = findViewById(R.id.graph);
        if(getIntent().getStringExtra("name").equals("first"))
        readCVSFromAssetFolder(R.raw.amulcheesespread_a);
        else if(getIntent().getStringExtra("name").equals("second"))
            readCVSFromAssetFolder(R.raw.amulcoolbadam_a);
        else if(getIntent().getStringExtra("name").equals("third"))
            readCVSFromAssetFolder(R.raw.amulgoldmilk_a);
    }


    private List<String[]> readCVSFromAssetFolder(int file){
        List<String[]> csvLine = new ArrayList<>();
        String[] content = null;
        try {
            InputStream inputStream = getResources().openRawResource(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while((line = br.readLine()) != null){
                content = line.split(",");
                csvLine.add(content);
            }
            csvLine.remove(0);
            createLineGraph(csvLine);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLine;
    }

    private void createLineGraph(List<String[]> result){
        DataPoint[] dataPoints = new DataPoint[result.size()];
        for (int i = 0; i < result.size(); i++){
            String [] rows = result.get(i);
//            Log.d(TAG, "Output " + Integer.parseInt(rows[0]) + " " + Integer.parseInt(rows[1]));
            dataPoints[i] = new DataPoint(Double.parseDouble(rows[0]), Double.parseDouble(rows[1]));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        mGraph.addSeries(series);
    }
}
