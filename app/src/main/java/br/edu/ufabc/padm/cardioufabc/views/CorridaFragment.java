package br.edu.ufabc.padm.cardioufabc.views;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.helpers.Permission;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;
import br.edu.ufabc.padm.cardioufabc.services.LocationService;

public class CorridaFragment extends Fragment {
    private static final String LOGTAG = CorridaFragment.class.getSimpleName();

    private TextView tituloTextView;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button actionButton;

    private boolean iniciado;
    private List<LatLng> locations;

    private Intent service;
    private LocationReceiver receiver;

    private AtividadeDAO dao;
    private Atividade atividade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = AtividadeDAO.getInstance(getActivity());

        iniciado = false;
        locations = new ArrayList<>();
        atividade = new Atividade();
        Bundle args = getArguments();
        System.out.println("CorridaFragment: " + args.getString("Titulo"));
        atividade.setTitulo(args.getString("Titulo"));
        atividade.setDescricao(args.getString("Descricao"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_corrida, container, false);

        tituloTextView = (TextView)view.findViewById(R.id.titulo);
        tituloTextView.setText(atividade.getTitulo());

        initGoogleMap(view, savedInstanceState);

        actionButton = (Button)view.findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iniciado) { // parar
                    atividade.setDataHoraFim(new Date());
                    atividade.setLocations(locations);
                    iniciado = false;
                    LocationService.running = false;
                    dao.create(atividade);
                } else { // iniciar
                    atividade.setDataHoraInicio(new Date());
                    iniciado = true;
                    actionButton.setText(R.string.parar);
                    actionButton.setBackgroundColor(0xFFE25041);
                    actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0, 0, 0);
                }
            }
        });

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
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;

                try {
                    googleMap.setMyLocationEnabled(true);
                } catch (SecurityException e) {
                    Log.e(LOGTAG, "Permissão negada", e);
                }

                startLocationService();
            }
        });
    }

    private void startLocationService() {
        receiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter(LocationService.LOCATION_CHANGED);
        LocalBroadcastManager.getInstance(getActivity().getBaseContext()).registerReceiver(receiver, filter);
        service = new Intent(getActivity().getBaseContext(), LocationService.class);
        getActivity().getBaseContext().startService(service);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LocationService.LOCATION_CHANGED)) {
                double latitude = intent.getDoubleExtra("Latitude", 0);
                double longitude = intent.getDoubleExtra("Longitude", 0);
                LatLng latLng = new LatLng(latitude, longitude);
                if(googleMap != null){
                    System.out.println("not null");
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                    if (iniciado) {
                        if (locations.size() > 0) {
                            googleMap.addPolyline(new PolylineOptions().add(latLng, locations.get(locations.size() - 1)).width(5).color(Color.GREEN).geodesic(true));
                        }
                        locations.add(latLng);
                    }
                } else {
                    System.out.println("null");
                }
            }
        }
    }
}
