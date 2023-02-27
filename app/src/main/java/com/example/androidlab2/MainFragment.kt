package com.example.androidlab2

import DataContainer
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab2.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.HttpException
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {
    private var adapter: ListWeatherAdapter? = null
    private var cityRep: List<City>? = null
    private var binding: FragmentMainBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val api = DataContainer.weatherApi
    private var lat: Double? = null
    private var lon: Double? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat = location?.latitude
                lon = location?.longitude}
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    loadCities(lat, lon)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    loadCities(lat, lon)
                } else -> {
                loadCities(51.5098,-0.1180)
            }
            }
        }
        checkPermission(locationPermissionRequest)
        binding?.run {
            search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                           loadWeather(query)
                        }
                        return false
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
            if (cityRep != null) {
                val itemDecoration: RecyclerView.ItemDecoration =
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                adapter = ListWeatherAdapter(cityRep) { city ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(city.name))
                        .addToBackStack("MainFragment")
                        .commit()
                }
                rvNearest.adapter = adapter
                rvNearest.addItemDecoration(itemDecoration)
                rvNearest.layoutManager = LinearLayoutManager(context)
            }
        }
    }
    private fun checkPermission(locationPermissionRequest: ActivityResultLauncher<Array<String>>) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        } else {
            loadCities(51.5098,-0.1180)

        }
    }
    private fun loadCities(lat: Double?, lot: Double?) : List<City>? {
        var cities: CitiesResponse? = null
        lifecycleScope.launch{
            cities = api.getCities(lat, lot).also {
                Timber.e("load city ${it.list[0].name}")
                cityRep = it.list
                Timber.e("cities ${cityRep?.size}")
            }
        }
        Timber.e("cities2 ${cityRep?.size}")
        return cities?.list
    }
    private fun loadWeather(city: String) {
        lifecycleScope.launch {
            try {
                if (api.getWeather(city).name.isNotEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(city))
                        .addToBackStack("MainFragment")
                        .commit()
                }
            } catch (e: HttpException) {
                Snackbar.make(requireView(), "Not found", Snackbar.LENGTH_LONG).show()
            }

        }
    }
}
