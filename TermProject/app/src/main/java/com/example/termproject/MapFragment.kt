package com.example.termproject

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.Marker
import java.io.IOException
import java.util.Locale

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private var myNaver: NaverMap? = null
    private var marker = Marker()
    var cityName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        myNaver = naverMap
        myNaver?.setOnMapClickListener { _, coord -> marking(coord.latitude, coord.longitude) }
    }

    private fun marking(latitude: Double, longitude: Double) {
        marker.position = LatLng(latitude, longitude)
        marker.map = myNaver
        getAddress(latitude, longitude)
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
        try {
            val addresses: MutableList<Address>? = geocoder?.getFromLocation(latitude, longitude, 10)
            if (addresses != null) {
                for (address in addresses) {
                    if (address != null) {
                        val city = address.locality
                        if (city != null && city.isNotEmpty()) {
                            cityName = city
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
