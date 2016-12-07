package br.edu.ufabc.padm.cardioufabc.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ufabc.padm.cardioufabc.R;

public class DetalhesCorridaFragment extends android.app.Fragment {
    public DetalhesCorridaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_corrida, container, false);
        return view;
    }
}
