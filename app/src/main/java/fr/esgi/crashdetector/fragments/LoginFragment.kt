package fr.esgi.crashdetector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.esgi.crashdetector.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class LoginFragment :  Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button_login)
        val action = LoginFragmentDirections.actionLoginFragmentToRunFragment()
        button.setOnClickListener {
            findNavController().navigate(action)
        }
    }
}