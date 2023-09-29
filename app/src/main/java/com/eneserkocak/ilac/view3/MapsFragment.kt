package com.eneserkocak.ilac.view3

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentMapsBinding
import com.eneserkocak.ilac.db_maps.PlaceDao
import com.eneserkocak.ilac.db_maps.PlaceDatabase
import com.eneserkocak.ilac.model.Place
import com.eneserkocak.ilac.view.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class MapsFragment : BaseFragment<FragmentMapsBinding>(R.layout.fragment_maps),OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    var trackBoolean: Boolean? = null
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null
    private lateinit var db: PlaceDatabase
    private lateinit var placeDao: PlaceDao
    lateinit var gelinenYer : String

    /* @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->



          }*/

    /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        registerLauncher()

        sharedPreferences = requireActivity().getSharedPreferences("com.eneserkocak",AppCompatActivity.MODE_PRIVATE)
        trackBoolean = false


        db = Room.databaseBuilder(requireContext(), PlaceDatabase::class.java, "Places").allowMainThreadQueries().build()
        placeDao = db.placeDao()



        binding.saveButton.setOnClickListener {

             val place = Place(binding.placeText.text.toString(), selectedLatitude!!,selectedLongitude!!)
             placeDao.insert(place)
            navigate(R.id.mapsListeFragment)

        }
        binding.deleteButton.setOnClickListener {



        }


    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

        arguments?.let {
            gelinenYer = MapsFragmentArgs.fromBundle(it).gelinenSayfa
        }

        //EĞER MAPS LİSTE DEN GELİRSE BURAYI ÇALIŞTIR
        if (gelinenYer== MAPS_LISTE) {
            binding.placeText.visibility = View.VISIBLE
            binding.saveButton.visibility = View.GONE

            viewModel.selectedPlace.observe(viewLifecycleOwner) {
                it?.let {
                    mMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.name)                    )
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude,it.longitude), 15f))

                    binding.placeText.setText(it.name)
                }
            }
            return
        }

        //EĞER ADRES EKLE DEN GELİR SE AŞAĞIYI ÇALIŞTIRACAK ( RETURN YUKARIDA ELSE GÖREVİ GÖRÜYOR, AŞAĞIDAKİ KODLARA GEÇİRMİYOR)
        binding.deleteButton.visibility= View.GONE

        locationManager =requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager


        locationListener = object : LocationListener {override fun onLocationChanged(location: Location) {

                println(location.toString())

                trackBoolean = sharedPreferences.getBoolean("trackBoolean", false)
                if (trackBoolean == false) {

                    val userLocation = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))

                    sharedPreferences.edit().putBoolean("trackBoolean", true).apply()

                }

            }

            override fun onProviderDisabled(provider: String) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(
                    binding.root,
                    "Permission needed for location",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Give Permission") {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                }.show()

            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }


        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )

            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation != null) {
                val lastUserLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
            }
            mMap.isMyLocationEnabled = true

        }

    }


    private fun registerLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    //permission granted (izin verildi),(Yukarıda izin verilen yeri buraya da kopyala Aynı şeyi yapıp Konum isteyeceğiz)
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener
                        )

                        val lastLocation =
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (lastLocation != null) {
                            val lastUserLocation =
                                LatLng(lastLocation.latitude, lastLocation.longitude)
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    lastUserLocation,
                                    15f
                                )
                            )
                        }
                        mMap.isMyLocationEnabled = true

                    }


                } else {
                    Toast.makeText(requireContext(), "İzin vermeniz gerekiyor!", Toast.LENGTH_LONG).show()

                }
            }
    }

    override fun onMapLongClick(p0: LatLng) {

        mMap.clear()

        mMap.addMarker(MarkerOptions().position(p0))

        selectedLatitude = p0.latitude
        selectedLongitude = p0.longitude
    }




    }























