package br.edu.ufabc.padm.cardioufabc.tasks;

import android.content.Context;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by guilhermeosaka on 15/11/16.
 */

public class TraceRouteTask extends TimerTask {
    private Context context;
    private TextView textView;
    private float elapsed;

    public TraceRouteTask(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void run() {
    }
}
