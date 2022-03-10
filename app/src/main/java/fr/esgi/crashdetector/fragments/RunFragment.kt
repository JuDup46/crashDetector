package fr.esgi.crashdetector.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
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


class RunFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var notificationManager: NotificationManager? = null
    val channelId = "My_Channel_ID"
    val notificationId = 1

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
        if (event != null) {

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
                    findNavController().navigate(action)

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

    /*
    If chute = changer de page avec ce code
    val action = RunFragmentDirections.actionRunFragmentToHelpFragment()
        findNavController().navigate(action)
     */




}