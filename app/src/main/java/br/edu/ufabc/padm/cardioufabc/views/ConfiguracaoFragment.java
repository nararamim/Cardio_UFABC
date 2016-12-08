package br.edu.ufabc.padm.cardioufabc.views;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.adapters.CalendarioAdapter;

public class ConfiguracaoFragment extends Fragment {

    public ConfiguracaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);

        return view;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_som:
                if (checked)
                    Toast.makeText(view.getContext(),
                            String.format("Notificações ativadas"),
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(view.getContext(),
                            String.format("Notificações desativadas"),
                            Toast.LENGTH_LONG).show();
                break;

        }
    }
}

