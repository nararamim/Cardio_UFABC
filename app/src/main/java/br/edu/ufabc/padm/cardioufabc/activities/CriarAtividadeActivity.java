package br.edu.ufabc.padm.cardioufabc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import br.edu.ufabc.padm.cardioufabc.R;

public class CriarAtividadeActivity extends AppCompatActivity {

    private EditText tituloEditText;
    private CheckBox corridaCheckBox;
    private Button criarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_atividade);

        tituloEditText = (EditText)findViewById(R.id.tituloEditText);
        corridaCheckBox = (CheckBox)findViewById(R.id.corridaCheckBox);
        criarButton = (Button)findViewById(R.id.criarButton);

        criarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCriar();
            }
        });
    }

    private void handleCriar() {
        if (corridaCheckBox.isChecked()) { // inicia a atividade de corrida
            Intent intent = new Intent(this, AtividadeCorridaActivity.class);
            System.out.println(tituloEditText.getText());
            intent.putExtra("titulo", tituloEditText.getText().toString());
            startActivity(intent);
        } else { // inicia o medidor
            Intent intent = new Intent(this, MedidorActivity.class);
            intent.putExtra("titulo", tituloEditText.getText().toString());
            startActivity(intent);
        }
    }
}
