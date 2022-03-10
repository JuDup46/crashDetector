package fr.esgi.crashdetector.fragments

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import fr.esgi.crashdetector.R
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt


class RunFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the sensor manager
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager

        // Specify the sensor you want to listen to
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
        // Inflate the layout for this fragment
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

                if (loAccelerationReader > 0.3 && loAccelerationReader < 0.5) {
                    requireView().findViewById<TextView>(R.id.runTextView).text = "Fall"
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}