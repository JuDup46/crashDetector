package fr.esgi.crashdetector.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        super.onCreate(savedInstanceState)
        view.recycler_trip.layoutManager = LinearLayoutManager(requireContext())
        view.recycler_trip.adapter = CustomAdapter();

    }


}