package com.mmrbd.barikoitask.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mmrbd.barikoitask.BuildConfig
import com.mmrbd.barikoitask.R
import com.mmrbd.barikoitask.data.model.Place
import com.mmrbd.barikoitask.databinding.ActivityMainBinding
import com.mmrbd.barikoitask.utils.AppLogger
import com.mmrbd.barikoitask.utils.network.ApiResult
import com.mmrbd.barikoitask.utils.network.NetworkFailureMessage
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapboxMap
    private lateinit var mapView: MapView

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var currentLocation: Location

    private val networkFailureMessage: NetworkFailureMessage by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel()

        val styleUrl =
            "https://map.barikoi.com/styles/barikoi-bangla/style.json?key=${BuildConfig.BARIKOI_API_KEY}"


        // Create map view
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            this.map = map
            map.setStyle(styleUrl)

            map.getCameraForLatLngBounds(LatLngBounds.fromLatLngs(listOf(LatLng(23.6850, 90.3563))))
                ?.let {
                    val newCameraPosition = CameraPosition.Builder()
                        .target(it.target)
                        .zoom(5.0)
                        .build()
                    map.cameraPosition = newCameraPosition
                }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermission()
        }



        binding.btnNearByBank.setOnClickListener {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    currentLocation = loc!!

                    getNearByData()
                }
        }

    }

    private fun addMarkersToMap(data: List<Place>) {
        val bounds = mutableListOf<LatLng>()

        // Get bitmaps for marker icon
        val infoIconDrawable = ResourcesCompat.getDrawable(
            this.resources,
            R.drawable.baseline_location_on_24,
            null
        )!!
        val bitmapBlue = infoIconDrawable.toBitmap()
        val bitmapRed = infoIconDrawable
            .mutate()
            .apply { setTint(Color.RED) }
            .toBitmap()

        // Add symbol for each point feature
        data.filter { it.pType == "Bank" }.forEach { place ->

            val latLng = LatLng(place.latitude.toDouble(), place.longitude.toDouble())
            bounds.add(latLng)

            // Contents in InfoWindow of each marker
            val title = place.name
            val address = place.address

            // Use MarkerOptions and addMarker() to add a new marker in map
            val markerOptions = MarkerOptions()
                .position(latLng)
                .title(address)
                .snippet(title)

            map.addMarker(markerOptions)
        }

        // Move camera to newly added annotations
        map.getCameraForLatLngBounds(LatLngBounds.fromLatLngs(bounds))?.let {
            val newCameraPosition = CameraPosition.Builder()
                .target(it.target)
                .zoom(15.0)
                .build()
            map.cameraPosition = newCameraPosition
        }

    }

    private fun getNearByData() {
        viewModel.getNearByData(
            BuildConfig.BARIKOI_API_KEY,
            currentLocation.latitude.toString(),
            currentLocation.longitude.toString()
        )
        lifecycleScope.launch {
            viewModel.nearByState.collect {
                when (it) {
                    is ApiResult.Error -> {
                        binding.progress.isVisible = false
                        Toast.makeText(
                            baseContext,
                            networkFailureMessage.handleFailure(it.error!!),
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is ApiResult.Loading -> {
                        binding.progress.isVisible = true
                    }

                    is ApiResult.Success -> {
                        binding.progress.isVisible = false
                        addMarkersToMap(it.data!!.places)
                        AppLogger.log(it.data.toString())
                    }
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    false
                ) -> {
                    // Precise location access granted.
                    Toast.makeText(this, "Precise location access granted.", Toast.LENGTH_SHORT)
                        .show()
                }

                permissions.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> {
                    // Only approximate location access granted.
                    Toast.makeText(
                        this,
                        "Only approximate location access granted.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // No location access granted.
                    Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}