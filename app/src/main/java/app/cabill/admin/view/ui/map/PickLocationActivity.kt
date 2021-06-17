package app.cabill.admin.view.ui.map

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import app.cabill.admin.R
import app.cabill.admin.databinding.ActivityPickLocationBinding
import app.cabill.admin.interfaces.ILocationListener
import app.cabill.admin.util.MyLocationProvider
import app.cabill.admin.util.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class PickLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var binding: ActivityPickLocationBinding
    lateinit var location: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        binding = ActivityPickLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.save.visibility = View.GONE

        binding.save.setOnClickListener {
            val intent = Intent()
            intent.putExtra("longitude", location.longitude)
            intent.putExtra("latitude", location.longitude)
            intent.putExtra("address", location.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {


        mMap = googleMap
        try {

            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style
                )
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }

        val client: FusedLocationProviderClient = Utils.getInstance().getFusedClient(this)
        MyLocationProvider.getMyLocationProvider().getUserLocation(
            this,
            this,
            client
        ) { locationResult, callback ->
            MyLocationProvider.getMyLocationProvider().stopUpdates(client, callback)
            binding.save.visibility = View.VISIBLE
            location = LatLng(
                locationResult.lastLocation.latitude,
                locationResult.lastLocation.longitude
            )
            //  mMap.addMarker(MarkerOptions().position())
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        locationResult.lastLocation.latitude,
                        locationResult.lastLocation.longitude
                    ), 15f
                )
            )
            mMap.setOnCameraIdleListener {
                location = mMap.cameraPosition.target
            }

        }

    }
}