package br.edu.ufabc.padm.cardioufabc.views;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;

public class CorridaFragment extends Fragment {
    public CorridaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_corrida, container, false);

        ((Button)view.findViewById(R.id.criarAtividadeFake)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Atividade fake = new Atividade();
                fake.setTitulo("Corridinha");
                fake.setDescricao("Corridinha saudável no parque Ibirapuera");
                fake.setDataHoraInicio(new Date(2016, 9, 27, 10, 35, 26));
                fake.setDataHoraFim(new Date(2016, 9, 27, 11, 45, 11));
                fake.setBpm(102);

                AtividadeDAO.getInstance(getActivity()).create(fake);
            }
        });

        return view;
    }
}
