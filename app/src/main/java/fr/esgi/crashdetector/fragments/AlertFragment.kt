package fr.esgi.crashdetector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import fr.esgi.crashdetector.R

class AlertFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn: Button = view.findViewById(R.id.btn_home)
        val imgView: ImageView = view.findViewById(R.id.img_ambu)
        btn.setOnClickListener {
            val action = AlertFragmentDirections.actionAlertFragmentToRunFragment()
            findNavController().navigate(action)
        }
        Glide.with(this).load("file:///android_asset/pls.gif").into(imgView);
    }
}