package com.indie.whitstan.dohanyradar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.get
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLngBounds.Builder
import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TobaccoShopsMapFragment : Fragment() , OnMapReadyCallback {
    private var mRetrofitClient : RetrofitClient = RetrofitClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tobacco_shops_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        loadTobaccoShopDataToMap(googleMap)
        setOnMarkerClickListenerToMap(googleMap)
    }

    private fun setOnMarkerClickListenerToMap(googleMap: GoogleMap?){
        googleMap?.setOnMarkerClickListener { marker ->
            val args = bundleOf(
                Pair("id", marker?.tag),
            )
            view?.findNavController()?.navigate(R.id.navigate_to_tobacco_shop_details_from_map, args)
            true
        }
    }

    private fun loadTobaccoShopDataToMap(googleMap: GoogleMap?) {
        lateinit var tobaccoShopsList : List<TobaccoShop>

        mRetrofitClient.createTobaccoShopListCall()?.enqueue(object : Callback<List<TobaccoShop>?> {
            override fun onResponse(
                call: Call<List<TobaccoShop>?>,
                response: Response<List<TobaccoShop>?>
            ) {
                response.body()?.let {
                    tobaccoShopsList = it
                }!!

                val builder = addMarkersToMapByTobaccoShopsList(googleMap, tobaccoShopsList)
                zoomIntoMapByBuilder(googleMap, builder)
            }

            override fun onFailure(call: Call<List<TobaccoShop>?>, error: Throwable) {
                Toast.makeText(context, R.string.error_while_loading_tobacco_shop_list, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    private fun addMarkersToMapByTobaccoShopsList(googleMap: GoogleMap?, tobaccoShopsList : List<TobaccoShop>) : Builder{
        val builder = Builder()
        for (tobaccoShop in tobaccoShopsList) {
            val shopLocation = LatLng(tobaccoShop.latitude, tobaccoShop.longitude)
            val shopIcon: BitmapDescriptor = if (tobaccoShop.isOpen) {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            } else {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            }

            val markerOptions = MarkerOptions()
                .icon(shopIcon)
                .position(shopLocation)
                .title(tobaccoShop.name)

            builder.include(markerOptions.position)

            googleMap?.apply {
                val marker = addMarker(markerOptions)
                marker.tag = tobaccoShop.id
            }
        }
        return builder
    }

    private fun zoomIntoMapByBuilder(googleMap: GoogleMap?, builder: Builder){
        val bounds: LatLngBounds = builder.build()
        val padding = 200
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap?.animateCamera(cameraUpdate)
    }
}