package br.edu.ufabc.padm.cardioufabc.views;

import android.app.FragmentManager;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.adapters.AtividadeAdapter;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;

public class AtividadesFragment extends Fragment {
    private ListView atividadesListView;
    private ImageButton criarAtividadeButton;

    public AtividadesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_atividades, container, false);

        final AtividadeAdapter adapter = new AtividadeAdapter(getActivity());
        atividadesListView = (ListView)view.findViewById(R.id.atividades);
        atividadesListView.setAdapter(adapter);

        atividadesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade = (Atividade) adapter.getItem(position);

                irParaDetalhesCorrida(atividade.getId());
            }
        });

        atividadesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                fragment = new ShowAtividadeFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });

        criarAtividadeButton = (ImageButton)view.findViewById(R.id.criar_atividade);
        criarAtividadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle("Criar Atividade");
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CriarAtividadeFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.atividades, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_add:
                fragment = new CriarAtividadeFragment();
                getActivity().setTitle("Criar Atividade");
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
