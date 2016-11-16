package br.edu.ufabc.padm.cardioufabc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.calendarAdapter;

public class CalendarioActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        final GridView gridview = (GridView) findViewById(R.id.calendar);
        gridview.setAdapter(new calendarAdapter(this));



    }
}