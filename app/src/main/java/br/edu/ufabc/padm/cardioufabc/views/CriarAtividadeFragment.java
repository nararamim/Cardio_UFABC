package br.edu.ufabc.padm.cardioufabc.views;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import br.edu.ufabc.padm.cardioufabc.MainActivity;
import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.helpers.Permission;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;


public class CriarAtividadeFragment extends Fragment implements Permission.Permissible {
    private Permission permission;
    private EditText tituloEditText;
    private EditText descricaoEditText;
    private CheckedTextView corridaCheckBox;
    private Button proximoButton;

    public CriarAtividadeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_atividade, container, false);

        permission = new Permission(getActivity());
        MainActivity.permissible = this;

        tituloEditText = (EditText) view.findViewById(R.id.titulo);
        descricaoEditText = (EditText) view.findViewById(R.id.descricao);

        proximoButton = (Button)view.findViewById(R.id.proximo);
        proximoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corridaCheckBox.isChecked()) {
                    if (permission.verify(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        irParaCorrida();
                    }
                } else {
                    irParaMedidor();
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

    public void irParaCorrida() {
        Bundle args = new Bundle();
        args.putString("Titulo", tituloEditText.getText().toString());
        args.putString("Descricao", descricaoEditText.getText().toString());
        CorridaFragment fragment = new CorridaFragment();
        fragment.setArguments(args);

        getActivity().setTitle("Corrida");
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

    public void irParaMedidor() {
        Bundle args = new Bundle();
        args.putString("Titulo", tituloEditText.getText().toString());
        args.putString("Descricao", descricaoEditText.getText().toString());
        MedidorFragment fragment = new MedidorFragment();
        fragment.setArguments(args);

        getActivity().setTitle("Medidor");
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onPermissionGranted(String feature) {
        switch (feature) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                irParaCorrida();
                break;
        }
    }

    @Override
    public void onNeverAskAgain(String feature) {
        permission.explainWhyThisShouldBeEnabled(feature);
    }
}
