package br.edu.ufabc.padm.cardioufabc.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;

import java.util.Date;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;


public class CriarAtividadeFragment extends Fragment {
    private CheckedTextView corridaCheckBox;
    private Button proximoButton;

    public CriarAtividadeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_atividade, container, false);

        proximoButton = (Button)view.findViewById(R.id.proximo);

        proximoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corridaCheckBox.isChecked()) {
                    getActivity().setTitle("Corrida");
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new CorridaFragment()).addToBackStack(null).commit();
                } else {
                    getActivity().setTitle("Medidor");
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MedidorFragment()).addToBackStack(null).commit();
                }
            }
        });

        corridaCheckBox = (CheckedTextView) view.findViewById(R.id.corrida);

        corridaCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corridaCheckBox.setChecked(!corridaCheckBox.isChecked());
            }
        });

        return view;
    }
}
