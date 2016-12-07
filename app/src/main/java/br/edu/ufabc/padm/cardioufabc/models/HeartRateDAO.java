package br.edu.ufabc.padm.cardioufabc.models;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class HeartRateDAO {
    private static HeartRateDAO instance;

    private static final String PREFIX = "heartrates_";
    private static final String SUFIX = ".json";

    public static HeartRateDAO getInstance(Context c) {
        if (instance == null) {
            instance = new HeartRateDAO(c);
        } else {
            instance.context = c;
        }

        return instance;
    }

    private Context context;

    public HeartRateDAO(Context context) {
        this.context = context;
    }

    public void create(long id, List<Integer> heartRates) {
        OutputStreamWriter outputStream;

        String fileName = PREFIX + id + SUFIX;
        String heartRatesJSON = HeartRateSerializer.serialize(heartRates);

        try {
            outputStream = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.write(heartRatesJSON);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("FileWriter", String.format("File %1$s not found", fileName));
        } catch (IOException e) {
            Log.e("FileWriter", String.format("Failed to write to %1$s", fileName));
        }
    }

    public List<Integer> getByAtividadeId(long id) {
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

        return HeartRateSerializer.deserialize(builder.toString());
    }
}
