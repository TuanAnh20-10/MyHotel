package com.xinchaongaymoi.hotelbookingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.xinchaongaymoi.hotelbookingapp.R
import com.xinchaongaymoi.hotelbookingapp.adapter.MarkerInfoWindowAdapter
import com.xinchaongaymoi.hotelbookingapp.helper.BitmapHelper
import com.xinchaongaymoi.hotelbookingapp.model.Place
import com.xinchaongaymoi.hotelbookingapp.service.PlaceRenderer
import com.xinchaongaymoi.hotelbookingapp.service.PlacesReader

class GoogleMapActivity : AppCompatActivity() {
    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }
    private val hotelIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)
        BitmapHelper.vectorToBitmap(this, R.drawable.baseline_hotel_24, color)
    }
    /**
     * Adds marker representations of the places list on the provided GoogleMap object
     */
    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.latLng)
                    .icon(hotelIcon)
            )

            // Set place as the tag on the marker object so it can be referenced within
            // MarkerInfoWindowAdapter
            marker?.tag = place
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addClusteredMarkers(googleMap)
        }

    }
    /**
     * Adds markers to the map with clustering support.
     */
    private fun addClusteredMarkers(googleMap: GoogleMap) {
        // Create the ClusterManager class and set the custom renderer.
        val clusterManager = ClusterManager<Place>(this, googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                this,
                googleMap,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        // Add the places to the ClusterManager.
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Set ClusterManager as the OnCameraIdleListener so that it
        // can re-cluster when zooming in and out.
        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }
}