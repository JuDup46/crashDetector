package fr.esgi.crashdetector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.esgi.crashdetector.R


class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateToFill: TextView
        val distanceToFill: TextView
        val timeToFill: TextView
        init {
            // Define click listener for the ViewHolder's View.
            dateToFill = view.findViewById(R.id.dateToFill)
            distanceToFill = view.findViewById(R.id.distanceToFill)
            timeToFill = view.findViewById(R.id.timeToFill)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_trip, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.dateToFill.text = "01/01/2022"
        viewHolder.timeToFill.text = "1h45"
        viewHolder.distanceToFill.text = "45km"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = 5

}

