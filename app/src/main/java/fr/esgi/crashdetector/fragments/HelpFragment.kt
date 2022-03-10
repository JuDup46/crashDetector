package fr.esgi.crashdetector.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.esgi.crashdetector.R
import java.io.File


class HelpFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val soundCountDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
            }

            override fun onFinish() {
                try {
                    val action = HelpFragmentDirections.actionHelpFragmentToAlertFragment()
                    findNavController().navigate(action)
                } catch (e: Exception) {
                    print(e)
                }
            }
        }

        val countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                soundCountDownTimer.start()
            }
        }

        countDownTimer.start()

        view.findViewById<Button>(R.id.buttonYesFall).setOnClickListener {
            try {
                val action = HelpFragmentDirections.actionHelpFragmentToAlertFragment()
                findNavController().navigate(action)
            } catch (e: Exception) {
                print(e)
            }
        }

        view.findViewById<Button>(R.id.buttonNotFall).setOnClickListener {
            try {
                val action = HelpFragmentDirections.actionHelpFragmentToRunFragment()
                findNavController().navigate(action)
            } catch (e: Exception) {
                print(e)
            }
        }
    }

    private fun playSound() {
        MediaPlayer.create(requireContext(),R.raw.chevre_sound).start()
    }
}