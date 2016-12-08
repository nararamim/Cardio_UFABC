package br.edu.ufabc.padm.cardioufabc.views;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.adapters.AtividadeAdapter;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;

public class EstatisticaFragment extends Fragment {
    public EstatisticaFragment() {
        // Required empty public constructor
    }

    private ListView atividadesListView;
    private ImageButton criarAtividadeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatistica, container, false);

        final AtividadeAdapter adapter = new AtividadeAdapter(getActivity());
        atividadesListView = (ListView)view.findViewById(R.id.atividades_est);
        atividadesListView.setAdapter(adapter);

        atividadesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade = (Atividade) adapter.getItem(position);

                irParaDetalhesCorrida(atividade.getId());
            }
        });

        return view;
    }

    private void irParaDetalhesCorrida(long id) {
        Bundle args = new Bundle();
        args.putLong("id", id);
        DetalhesCorridaFragment fragment = new DetalhesCorridaFragment();
        fragment.setArguments(args);

        getActivity().setTitle("Detalhes Corrida");
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

}
