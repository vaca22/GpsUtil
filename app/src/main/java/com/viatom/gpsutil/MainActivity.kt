package com.viatom.gpsutil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.viatom.gpsutil.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //startActivity(Intent(this,TestLocationActivity::class.java))
        val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
       val  isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
       val  isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 100);
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
            return
        }
        Log.e("fuck","fuck you")
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f,object:LocationListener{
            override fun onLocationChanged(p0: Location) {
                val location=p0
                if (location != null) {

                    val  latitude = location.getLatitude();
                    val longitude = location.getLongitude();
                    MainScope().launch {
                        Log.e("fuck2","$latitude    $longitude")
                  //      binding.fuck.text="fuck2       $latitude    $longitude"
                    }

                }
            }

        });
        if (locationManager != null) {
            Log.e("fuck2","0000")
           val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                getAddress(location)
              val  latitude = location.getLatitude();
               val longitude = location.getLongitude();
                Log.e("fuck","$latitude    $longitude")
                MainScope().launch {
                    Log.e("fuck2","$latitude    $longitude")
                 //   binding.fuck.text="fuck1       $latitude    $longitude"
                }
            }
        }
    }

    private fun getAddress(location: Location?): List<Address?>? {
        var result: List<Address?>? = null
        try {
            if (location != null) {
                val gc = Geocoder(this, Locale.getDefault())
                result = gc.getFromLocation(
                    location.latitude,
                    location.longitude, 1
                )
              //  Toast.makeText(this, "获取地址信息：$result", Toast.LENGTH_LONG).show()
              //  Log.v("TAG", "获取地址信息：$result")
                MainScope().launch {
                    var fuck=""
                    for(k in 0..3){
                        fuck+=result[0]!!.getAddressLine(k)
                    }


                    binding.fuck.text=fuck
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}