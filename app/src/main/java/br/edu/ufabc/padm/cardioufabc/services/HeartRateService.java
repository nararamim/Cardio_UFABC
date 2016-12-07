package br.edu.ufabc.padm.cardioufabc.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

public class HeartRateService extends IntentService {
    private static final int REFRESH_RATE = 2000; // 2 segundos
    private static final int MIN_RATE = 40;
    private static final int MAX_RATE = 120;
    private static final int MAX_DEVIATION = 5;
    public static final String HEART_RATE_CHANGED = "HEART_RATE_CHANGED";

    public int currentHR; // batimentos por minuto atual

    public HeartRateService() {
        super("HeartRateService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Random random = new Random();

        currentHR = random.nextInt((MAX_RATE - MIN_RATE) + 1) + MIN_RATE;

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int minRate = (currentHR - MAX_DEVIATION < MIN_RATE) ? MIN_RATE : currentHR - MAX_DEVIATION;
                int maxRate = (currentHR + MAX_DEVIATION > MAX_RATE) ? MAX_RATE : currentHR + MAX_DEVIATION;

                currentHR = random.nextInt((maxRate - minRate) + 1) + minRate;

                Intent intent = new Intent(HEART_RATE_CHANGED);
                intent.putExtra("HeartRate", currentHR);
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);

                handler.postDelayed(this, REFRESH_RATE);
            }
        }, REFRESH_RATE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
