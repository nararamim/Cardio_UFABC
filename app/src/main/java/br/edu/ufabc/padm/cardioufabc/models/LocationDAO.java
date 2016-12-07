package br.edu.ufabc.padm.cardioufabc.models;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import br.edu.ufabc.padm.cardioufabc.R;

// Salva as localizações em arquivos json
// escrita/leitura interna apenas
public class LocationDAO {
    private static LocationDAO instance;

    private static final String PREFIX = "locations_";
    private static final String SUFIX = ".json";

    public static LocationDAO getInstance(Context c) {
        if (instance == null) {
            instance = new LocationDAO(c);
        } else {
            instance.context = c;
        }

        return instance;
    }

    private Context context;

    public LocationDAO(Context context) {
        this.context = context;
    }

    // recebe o id da atividade e a lista locations
    // converte a lista locations para o formato json e salva o arquivo com o nome locations_{id}.json
    public void create(long id, List<LatLng> locations) {
        OutputStreamWriter outputStream;

        String fileName = PREFIX + id + SUFIX;
        String locationsJSON = LocationSerializer.serialize(locations);

        try {
            outputStream = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.write(locationsJSON);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("FileWriter", String.format("File %1$s not found", fileName));
        } catch (IOException e) {
            Log.e("FileWriter", String.format("Failed to write to %1$s", fileName));
        }
    }

    public List<LatLng> getByAtividadeId(long id) {
        InputStreamReader inputStream;
        BufferedReader reader;
        StringBuilder builder = new StringBuilder();
        String line;

        String fileName = PREFIX + id + SUFIX;

        try {
            inputStream = new InputStreamReader(context.openFileInput(fileName));
            reader = new BufferedReader(inputStream);

            while ((line = reader.readLine()) != null)
                builder.append(line);
        } catch (FileNotFoundException e) {
            Log.e("FileReader", String.format("Failed to open %1$s", fileName));
        } catch (IOException e) {
            Log.e("FileReader", String.format("Failed to read %1$s", fileName));
        }

        return LocationSerializer.deserialize(builder.toString());
    }
}
