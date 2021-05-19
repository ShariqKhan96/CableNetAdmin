package app.cabill.admin.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import app.cabill.admin.interfaces.ILocationListener;


public class MyLocationProvider {
    public static MyLocationProvider myLocationProvider = null;
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    private LocationSettingsRequest mLocationSettingsRequest;
    private SettingsClient mSettingsClient;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    private String TAG = MyLocationProvider.class.getSimpleName();


    public static MyLocationProvider getMyLocationProvider() {
        if (myLocationProvider == null)
            myLocationProvider = new MyLocationProvider();
        return myLocationProvider;
    }

    public void getUserLocation(final Context c, Activity activity, FusedLocationProviderClient client, final ILocationListener locationListener) {
        LocationRequest request;
        request = new LocationRequest();
        request.setInterval(5000);
        request.setFastestInterval(4000);
        request.setSmallestDisplacement(1);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder.addLocationRequest(request);
        mLocationSettingsRequest = builder.build();
        mSettingsClient = LocationServices.getSettingsClient((Activity) c);
        int permission = ContextCompat.checkSelfPermission(c,
                android.Manifest.permission.ACCESS_FINE_LOCATION);


        if (permission == PackageManager.PERMISSION_GRANTED && Utils.isGPSEnabled(c)) {
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Log.e("onLocationResult", "here");

                    LocationCallback callback = this;
                    locationListener.onLocationReceived(locationResult, callback);
                }
            }, Looper.myLooper());
//            mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
//                    .addOnSuccessListener(locationSettingsResponse -> {
//                        client.requestLocationUpdates(request, new LocationCallback() {
//                            @Override
//                            public void onLocationResult(LocationResult locationResult) {
//                                Log.e("onLocationResult", "here");
//
//                                LocationCallback callback = this;
//                                locationListener.onLocationReceived(locationResult, callback);
//                            }
//                        }, Looper.myLooper());
//                    }).addOnFailureListener(e -> {
//                int statusCode = ((ApiException) e).getStatusCode();
//                switch (statusCode) {
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
//                                "location settings ");
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the
//                            // result in onActivityResult().
//                            ResolvableApiException rae = (ResolvableApiException) e;
//                            rae.startResolutionForResult((Activity) c, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException sie) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        String errorMessage = "Location settings are inadequate, and cannot be " +
//                                "fixed here. Fix in Settings.";
//                        Log.e(TAG, errorMessage);
//
//                        Toast.makeText(c, errorMessage, Toast.LENGTH_LONG).show();
//                }
//            });


        } else {

            new Handler().postDelayed(() -> {

                AlertDialog.Builder builder = Utils.getInstance().getAlertDialog(c, "Turn on the device location from device settings and enable permissions", "Error");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(intent);
                    dialog.dismiss();
                    activity.finish();
                });
                builder.show();
            }, 1000);

        }


    }


    public void stopUpdates(FusedLocationProviderClient client, LocationCallback locationCallback) {
        client.removeLocationUpdates(locationCallback);
    }
}