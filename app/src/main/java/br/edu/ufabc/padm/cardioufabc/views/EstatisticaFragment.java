package br.edu.ufabc.padm.cardioufabc.views;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.adapters.AtividadeAdapter;

public class EstatisticaFragment extends Fragment {
    public EstatisticaFragment() {
        // Required empty public constructor
    }

    private ListView atividadesListView;
    private ImageButton criarAtividadeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atividades, container, false);

        atividadesListView = (ListView)view.findViewById(R.id.atividades);
        atividadesListView.setAdapter(new AtividadeAdapter(getActivity()));

        return inflater.inflate(R.layout.fragment_estatistica, container, false);
    }

}
