package fr.esgi.crashdetector.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Chronometer
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.esgi.crashdetector.R
import kotlin.math.pow
import kotlin.math.sqrt
import android.widget.CompoundButton
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import fr.esgi.crashdetector.MainActivity
import java.lang.Exception


class RunFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var notificationManager: NotificationManager? = null
    val channelId = "My_Channel_ID"
    val notificationId = 1
    var sensorEnabled = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(channelId)

        sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager!!.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && sensorEnabled) {

            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

                val loX: Float = event.values[0]
                val loY: Float = event.values[1]
                val loZ: Float = event.values[2]

                val loAccelerationReader = sqrt(
                    loX.toDouble().pow(2.0)
                            + loY.toDouble().pow(2.0)
                            + loZ.toDouble().pow(2.0)
                )

                if (loAccelerationReader > 0.3 && loAccelerationReader < 0.5) { //FAll

                    val action = RunFragmentDirections.actionRunFragmentToHelpFragment()
                    try {
                        this.view?.let { Navigation.findNavController(it).navigate(action) }
                    } catch (e:Exception){
                        print(e)
                    }


                    val builder = NotificationCompat.Builder(requireContext(), channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Attention!")
                        .setContentText("Etes vous tombé?")

                    NotificationManagerCompat.from(requireContext()).apply {
                        notify(notificationId, builder.build())
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch: SwitchCompat = view.findViewById(R.id.switch1)
        val chrono: Chronometer = view.findViewById(R.id.chrono)
        switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked){
//                val action = RunFragmentDirections.actionRunFragmentToHelpFragment()
//                findNavController().navigate(action)
                sensorEnabled = true
                chrono.start()

            }else{
                sensorEnabled = false
                chrono.stop()
            }

        })

    }

    private fun createNotificationChannel(channelId:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance)
            channel.apply {
                description = channelDescription
            }

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}