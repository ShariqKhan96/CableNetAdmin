package app.cabill.admin.view.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cabill.admin.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

        // Add a marker in Sydney and move the camera
        val office = LatLng(24.8662069, 67.0701854)
        val random1 = LatLng(24.9405263, 67.0667855)
        val random2 = LatLng(24.8697215, 67.0618008)
        val random3 = LatLng(24.8939495, 67.0489402)
        mMap.addMarker(MarkerOptions().position(office).title(""))
        mMap.addMarker(MarkerOptions().position(random1).title(""))
        mMap.addMarker(MarkerOptions().position(random2).title(""))
        mMap.addMarker(MarkerOptions().position(random3).title(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(office,12f))
    }

}