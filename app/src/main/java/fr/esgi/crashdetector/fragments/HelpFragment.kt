package fr.esgi.crashdetector.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HelpFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(HelpFragmentDirections.actionHelpFragmentToAlertFragment())
    }

    /*
    If besoin d'aide Changer de page avec ce code
    val action = HelpFragmentDirections.actionHelpFragmentToAlertFragment()
        findNavController().navigate(action)
     */
}