package br.edu.ufabc.padm.cardioufabc.views;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.adapters.CalendarioAdapter;

public class CalendarioFragment extends Fragment {

    public CalendarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        final GridView gridview = (GridView) view.findViewById(R.id.calendar);
        gridview.setAdapter(new CalendarioAdapter(getActivity()));
        // Inflate the layout for this fragment
        return view;
    }
}
