package br.edu.ufabc.padm.cardioufabc.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.helpers.Util;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;

public class DetalhesCorridaFragment extends android.app.Fragment {
    private AtividadeDAO dao;
    private Atividade atividade;

    // campos
    private TextView tituloTextView;
    private TextView descricaoTextView;
    private TextView dataTextView;
    private TextView horaInicioTextView;
    private TextView horaFimTextView;
    private TextView minimoTextView;
    private TextView mediaTextView;
    private TextView maximoTextView;

    private MapView mapView;
    private GoogleMap googleMap;

    public DetalhesCorridaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = AtividadeDAO.getInstance(getActivity());

        Bundle args = getArguments();
        long id = args.getLong("id");
        atividade = dao.getById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_corrida, container, false);

        tituloTextView = (TextView) view.findViewById(R.id.titulo);
        tituloTextView.setText(atividade.getTitulo());
        descricaoTextView = (TextView)view.findViewById(R.id.descricao);
        descricaoTextView.setText(atividade.getDescricao());
        dataTextView = (TextView)view.findViewById(R.id.data);
        dataTextView.setText(Util.formatFromDate(atividade.getDataHoraInicio(), getActivity().getString(R.string.format_date)));
        horaInicioTextView = (TextView)view.findViewById(R.id.horaInicio);
        horaInicioTextView.setText(Util.formatFromDate(atividade.getDataHoraInicio(), getActivity().getString(R.string.format_time)));
        horaFimTextView = (TextView)view.findViewById(R.id.horaFim);
        horaFimTextView.setText(Util.formatFromDate(atividade.getDataHoraFim(), getActivity().getString(R.string.format_time)));
        minimoTextView = (TextView)view.findViewById(R.id.minimo);
        minimoTextView.setText(atividade.getMinBpm() + " bpm");
        mediaTextView = (TextView)view.findViewById(R.id.media);
        mediaTextView.setText(atividade.getBpm() + " bpm");
        maximoTextView = (TextView)view.findViewById(R.id.maximo);
        maximoTextView.setText(atividade.getMaxBpm() + " bpm");

        initGoogleMap(view, savedInstanceState);

        return view;
    }

    public void initGoogleMap(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gm) {
                googleMap = gm;

                drawRoute();
            }
        });
    }

    private void drawRoute() {
        if (atividade.getLocations().size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < atividade.getLocations().size(); i++) {
                builder.include(atividade.getLocations().get(i));
                if (i > 0) {
                    LatLng source = atividade.getLocations().get(i - 1);
                    LatLng target = atividade.getLocations().get(i);
                    googleMap.addPolyline(new PolylineOptions().add(source, target).width(5).color(Color.GREEN).geodesic(true));
                }
            }
            LatLngBounds bounds = builder.build();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        }
    }
}
