package br.edu.ufabc.padm.cardioufabc.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.tasks.TraceRouteTask;

public class RunningActivity extends FragmentActivity {

    private Button runButton;
    private TextView bpmTextView;
    private float elapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        runButton = (Button)findViewById(R.id.runButton);
        bpmTextView = (TextView)findViewById(R.id.bpmTextView);

        final RunningActivity self = this;

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a princípio pode rodar sem permissão
                requestPermission();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bpmTextView.setText("Elapsed: " + elapsed++);
                        System.out.println("teste elapsed");
                        System.out.println(elapsed);
                        handler.postDelayed(this, 1000); //now is every 2 minutes
                    }
                }, 1000); //Every 120000 ms (2 minutes)

//                Timer timer = new Timer("TraceRouteTaskTimer", true);
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        bpmTextView.setText("Elapsed: " + elapsed++);
//                    }
//                }, 0, 1000);
            }
        });

    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }
}
