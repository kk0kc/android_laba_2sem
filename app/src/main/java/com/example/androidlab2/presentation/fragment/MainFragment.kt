package com.example.androidlab2.presentation.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab2.App
import com.example.androidlab2.presentation.recycle.ListWeatherAdapter
import com.example.androidlab2.R
import com.example.androidlab2.databinding.FragmentMainBinding
import com.example.androidlab2.domain.location.GetLocationUseCase
import com.example.androidlab2.domain.wheather.GetWeatherByNameUseCase
import com.example.androidlab2.domain.wheather.GetWeatherListUseCase
import com.example.androidlab2.domain.wheather.model.WeatherListInfo
import com.example.androidlab2.presentation.fragment.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private var adapter: ListWeatherAdapter? = null
    private var binding: FragmentMainBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var weatherByNameUseCase: GetWeatherByNameUseCase

    @Inject
    lateinit var weatherListUseCase: GetWeatherListUseCase

    @Inject
    lateinit var locationUseCase: GetLocationUseCase

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(weatherByNameUseCase, weatherListUseCase, locationUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectMain(this)
        super.onCreate(savedInstanceState)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        initAdapter()
        observeViewModel()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                lat = location?.latitude
//                lon = location?.longitude}
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    viewModel.onLocationPermsIsGranted(true)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    viewModel.onLocationPermsIsGranted(true)
                }
                else -> {
                    viewModel.onLocationPermsIsGranted(false)
            }
            }
        }
        checkPermission(locationPermissionRequest)
        binding?.run {
            search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            viewModel.onLoadClick(query)
                        }
                        return false
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
        }
    }

    private fun initAdapter() {
        binding?.run {
            val itemDecoration: RecyclerView.ItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            adapter = ListWeatherAdapter{ city ->
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



    @SuppressLint("SuspiciousIndentation")
    private fun observeViewModel() {
        with(viewModel) {
            error.observe(viewLifecycleOwner){
                if (it == null) return@observe
                    showError()
                }
            adapter.observe(viewLifecycleOwner) {
                    initAdapter()
                }
            location.observe(viewLifecycleOwner) {
                getList()
            }
            weatherList.observe(viewLifecycleOwner) {
                it?.let {
                    submitList(it)
                }
            }
            permission.observe(viewLifecycleOwner) {
                onNeedLocation()
            }
            navigateToDetails.observe(viewLifecycleOwner) {
                it?.let {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(it))
                        .addToBackStack("MainFragment")
                        .commit()
                }
            }

        }
    }
    private fun submitList(weatherListInfo: List<WeatherListInfo>) {
        adapter?.submitList(weatherListInfo)
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
        }
        else {
            viewModel.onLocationPermsIsGranted(false)
        }
    }

    private fun showError() {
        Snackbar.make(requireView(), "Not found", Snackbar.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
