package app.cabill.admin.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

public class Utils {
    public static ViewProgressDialog progressDialog;
    public static Utils instance;

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();
        return instance;
    }

    public boolean anyFieldEmpty(String[] strings) {
        boolean isEmpty = false;
        for (int i = 0; i < strings.length; i++) {
            if (TextUtils.isEmpty(strings[i])) {
                isEmpty = true;
                break;

            }
        }
        return isEmpty;
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
        progressDialog = new ViewProgressDialog();
        progressDialog.show(context, message);

    }

    public void dismissLoader() {
        progressDialog.dialog.dismiss();
    }

    public void call(@NotNull String phone, @NotNull Context con) {
        Dexter.withContext(con)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        con.startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void openWhatsapp(@NotNull String phone, @NotNull Context con) {
        String url = "https://api.whatsapp.com/send?phone=+92" + phone.substring(1);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        con.startActivity(i);

    }
}
