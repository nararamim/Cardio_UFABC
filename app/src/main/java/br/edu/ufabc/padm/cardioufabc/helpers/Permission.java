package br.edu.ufabc.padm.cardioufabc.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import br.edu.ufabc.padm.cardioufabc.R;

public class Permission {
    private static final int PERMISSION_REQ_CODE = 0;

    private Activity context;

    public Permission(Activity context) {
        this.context = context;
    }

    private boolean hasPermission(String feature) {
        return ContextCompat.checkSelfPermission(context, feature) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean shouldShowRequestPermissionRationale(String feature) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, feature);
    }

    private void showRationaleDialog(final String feature) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.custom_dialog));
        AlertDialog dialog;

        switch (feature) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                builder.setMessage(R.string.location_rationale);
                break;
        }
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                requestPermission(feature);
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void requestPermission(String feature) {
        ActivityCompat.requestPermissions(context, new String[]{ feature }, PERMISSION_REQ_CODE);
    }

    public boolean verify(String feature) {
        if (hasPermission(feature)) {
            return true;
        } else {
            if (shouldShowRequestPermissionRationale(feature)) {
                showRationaleDialog(feature);
            } else {
                requestPermission(feature);
            }
        }
        return hasPermission(feature);
    }

    public void explainWhyThisShouldBeEnabled(String feature) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.custom_dialog));
        AlertDialog dialog;

        switch (feature) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                builder.setMessage(R.string.why_location);
                break;
        }
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public interface Permissible {
        public void onPermissionGranted(String feature);
        public void onNeverAskAgain(String feature);
    }
}
