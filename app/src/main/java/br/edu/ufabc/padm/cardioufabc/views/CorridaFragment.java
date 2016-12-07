package br.edu.ufabc.padm.cardioufabc.views;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.app.FragmentManager;
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
import br.edu.ufabc.padm.cardioufabc.services.HeartRateService;
import br.edu.ufabc.padm.cardioufabc.services.LocationService;

public class CorridaFragment extends Fragment {
    private static final String LOGTAG = CorridaFragment.class.getSimpleName();

    private TextView tituloTextView;
    private TextView bpmTextView;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button actionButton;

    private boolean iniciado;
    private List<LatLng> locations;
    private List<Integer> heartRates;

    // localização
    private Intent locationService;
    private LocationReceiver locationReceiver;

    // batimentos cardíacos por minuto
    private Intent heartRateService;
    private HeartRateReceiver heartRateReceiver;

    private AtividadeDAO dao;
    private Atividade atividade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = AtividadeDAO.getInstance(getActivity());

        iniciado = false;
        locations = new ArrayList<>();
        heartRates = new ArrayList<>();
        atividade = new Atividade();
        Bundle args = getArguments();
        atividade.setTitulo(args.getString("Titulo"));
        atividade.setDescricao(args.getString("Descricao"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_corrida, container, false);

        tituloTextView = (TextView)view.findViewById(R.id.titulo);
        tituloTextView.setText(atividade.getTitulo());

        bpmTextView = (TextView)view.findViewById(R.id.bpm);

        initGoogleMap(view, savedInstanceState);

        actionButton = (Button)view.findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iniciado) { // parar
                    atividade.setDataHoraFim(new Date());
                    atividade.setLocations(locations);
                    atividade.setHeartRates(heartRates);
                    iniciado = false;
                    LocationService.running = false;
                    long id = dao.create(atividade);

                    irParaDetalhesCorrida(id);
                } else { // iniciar
                    atividade.setDataHoraInicio(new Date());
                    iniciado = true;
                    actionButton.setText(R.string.parar);
                    actionButton.setBackgroundColor(0xFFE25041);
                    actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0, 0, 0);
                    startHeartRateService();
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
            public void onMapReady(GoogleMap gm) {
                googleMap = gm;

                try {
                    googleMap.setMyLocationEnabled(true);

                    startLocationService();
                } catch (SecurityException e) {
                    Log.e(LOGTAG, "Permissão negada", e);
                }
            }
        });
    }

    private void startLocationService() {
        locationReceiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter(LocationService.LOCATION_CHANGED);
        LocalBroadcastManager.getInstance(getActivity().getBaseContext()).registerReceiver(locationReceiver, filter);
        locationService = new Intent(getActivity().getBaseContext(), LocationService.class);
        getActivity().getBaseContext().startService(locationService);
    }

    private void startHeartRateService() {
        heartRateReceiver = new HeartRateReceiver();
        IntentFilter filter = new IntentFilter(HeartRateService.HEART_RATE_CHANGED);
        LocalBroadcastManager.getInstance(getActivity().getBaseContext()).registerReceiver(heartRateReceiver, filter);
        heartRateService = new Intent(getActivity().getBaseContext(), HeartRateService.class);
        getActivity().getBaseContext().startService(heartRateService);
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
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                    if (iniciado) {
                        if (locations.size() > 0) {
                            googleMap.addPolyline(new PolylineOptions().add(latLng, locations.get(locations.size() - 1)).width(5).color(Color.GREEN).geodesic(true));
                        }
                        locations.add(latLng);
                    }
                }
            }
        }
    }

    private class HeartRateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(HeartRateService.HEART_RATE_CHANGED)) {
                int hr = intent.getIntExtra("HeartRate", 0);
                heartRates.add(hr);
                bpmTextView.setText(hr + " bpm");
            }
        }
    }
}
