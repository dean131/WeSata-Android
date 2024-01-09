package com.kelompoktmr.wesata.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kelompoktmr.wesata.R


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        val title = intent.getStringExtra("title").toString()
        val latitude = intent.getStringExtra("latitude")!!.toDouble()
        val longitude = intent.getStringExtra("longitude")!!.toDouble()
        val latLng = LatLng(latitude, longitude)
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
        val zoomLevel = 17f
        map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoomLevel)
        )
    }
}