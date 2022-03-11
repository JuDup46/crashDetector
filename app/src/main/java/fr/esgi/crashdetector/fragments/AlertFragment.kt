package fr.esgi.crashdetector.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import fr.esgi.crashdetector.R
import fr.esgi.crashdetector.api.LocationApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class AlertFragment : Fragment() {

    var locationManager: LocationManager? = null
    var hasGps: Boolean = false
    var hasNetwork: Boolean= false
    var locationGps: Location? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
        val btn: Button = view.findViewById(R.id.btn_home)
        val imgView: ImageView = view.findViewById(R.id.img_ambu)
        btn.setOnClickListener {
            val action = AlertFragmentDirections.actionAlertFragmentToRunFragment()
            findNavController().navigate(action)
        }
        Glide.with(this).load("file:///android_asset/pls.gif").into(imgView);
    }

    private fun getLocation() {
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {

                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                    }
                }
                locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 0F
                ) { p0 ->
                    if (p0 != null) {
                        locationGps = p0
                    }
                }

                val localGpsLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation

                val path = requireContext().filesDir
                val directory = File(path, "mail")
                val file = File(directory, "email.txt")
                val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
                Log.d("TAG", inputAsString)
                //appel api avec :
                val locationToSend:String = locationGps?.latitude.toString() + "," + locationGps?.longitude.toString()

                MainScope().launch(Dispatchers.Main) {
                    try {
                        withContext(Dispatchers.Main) {
                            LocationApiClient.sendCall(inputAsString, locationToSend)
                        }
                    } catch (e: Exception) {
                        Log.d("CALL", "Email or coordinate doesn't exist")
                    }
                }
            }
        }
    }
}