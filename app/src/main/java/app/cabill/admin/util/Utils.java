package app.cabill.admin.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Utils {
    public static ProgressDialog progressDialog;
    public static Utils instance;

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();
        return instance;
    }
    public FusedLocationProviderClient getFusedClient(Context context) {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
        return client;
    }

    public static boolean isGPSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }

    public AlertDialog.Builder getAlertDialog(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public void showAlertDialog(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();

    }

    public void showLoader(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(message);
            progressDialog.setTitle("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        } else {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void dismissLoader() {
        progressDialog.dismiss();
    }
}
