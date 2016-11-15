package br.edu.ufabc.padm.cardioufabc.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import br.edu.ufabc.padm.cardioufabc.R;

public class AtividadeCorridaActivity extends FragmentActivity {

    private TextView tituloTextView;
    private Button runButton;
    private TextView elapsedTextView;
    private TextView bpmTextView;
    private float elapsed;
    private String titulo;
    private boolean running;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_corrida);

        titulo = getIntent().getExtras().getString("titulo");
        System.out.println(titulo);
        tituloTextView = (TextView)findViewById(R.id.tituloTextView);
        tituloTextView.setText(titulo);

        runButton = (Button)findViewById(R.id.runButton);
        elapsedTextView = (TextView)findViewById(R.id.elapsedTextView);
        bpmTextView = (TextView)findViewById(R.id.bpmTextView);

        final AtividadeCorridaActivity self = this;

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // a princípio pode rodar sem permissão
                requestPermission();

                if (!running) {
                    runButton.setText("Parar");
                    running = true;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            elapsedTextView.setText(String.format("Tempo: %.1f s", elapsed));
                            elapsed += 0.1;
                            Random random = new Random();
                            // gera valores aleatórios entre 80 e 100 (apenas para simulação)
                            int bpm = random.nextInt(21) + 80;

                            bpmTextView.setText(String.format("%d bpm", bpm));

                            handler.postDelayed(this, 100);
                        }
                    }, 0);
                } else {
                    runButton.setText("Iniciar");
                    running = false;
                    handler.removeCallbacksAndMessages(null);
                }
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
