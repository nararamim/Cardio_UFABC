package br.edu.ufabc.padm.cardioufabc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidplot.xy.XYPlot;


import br.edu.ufabc.padm.cardioufabc.R;

public class EstatisticaActivity extends AppCompatActivity  {
    // Array of strings...

    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);

        String[] activities_array = getResources().getStringArray(R.array.activities_list);

    }
}

