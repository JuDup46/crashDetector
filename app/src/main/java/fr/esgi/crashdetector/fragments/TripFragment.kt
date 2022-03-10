package fr.esgi.crashdetector.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import fr.esgi.crashdetector.R
import fr.esgi.crashdetector.adapter.CustomAdapter
import kotlinx.android.synthetic.main.fragment_trip.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TripFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonRun = requireView().findViewById<Button>(R.id.buttonRun);
        buttonRun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_tripFragment_to_runFragment);
        }
    }


}