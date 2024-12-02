package com.np.appppppp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class VehiclesAdapter(private var vehicles: List<Vehicle>, context: Context) :
    RecyclerView.Adapter<VehiclesAdapter.NoteViewHolder>() {

    private val db: VehiclesDatabaseHelper = VehiclesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        val colorTextView: TextView = itemView.findViewById(R.id.colorTextView)
        val licenseTextView: TextView = itemView.findViewById(R.id.licenseTextView)
        val vinTextView: TextView = itemView.findViewById(R.id.vinTextView)
        val mileageTextView: TextView = itemView.findViewById(R.id.mileageTextView)
        val lastOilChangeTextView: TextView = itemView.findViewById(R.id.lastOilChangeTextView)
        val lastATFChangeTextView: TextView = itemView.findViewById(R.id. lastATFChangeTextView)
        val lastAirFilterTextView: TextView = itemView.findViewById(R.id. lastAirFilterTextView)
        val lastBreakInspectionTextView: TextView = itemView.findViewById(R.id. lastBreakInspectionTextView)
        val lastTireRotationTextView: TextView = itemView.findViewById(R.id. lastTireRotationTextView)
        val lastAnnualInspectionTextView: TextView = itemView.findViewById(R.id. lastAnnualInspectionTextView)

        // The TextViews that will be hidden under the detailsContainer
        val detailsContainer: View = itemView.findViewById(R.id.detailsContainer)  // Entire details container

        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val expandButton: ImageView = itemView.findViewById(R.id.expandButton)  // The button to expand/collapse the details
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = vehicles.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.titleTextView.text = vehicle.detail

        holder.colorTextView.text = vehicle.color
        holder.licenseTextView.text = vehicle.license
        holder.vinTextView.text = vehicle.vin
        holder.mileageTextView.text = vehicle.mileage.toString()
        holder.lastOilChangeTextView.text = vehicle.lastOilChange
        holder.lastATFChangeTextView.text = vehicle.lastATFChange
        holder.lastAirFilterTextView.text = vehicle.lastAirFilterChange
        holder.lastBreakInspectionTextView.text = vehicle.lastBrakeInspection
        holder.lastTireRotationTextView.text = vehicle.lastTireRotation
        holder.lastAnnualInspectionTextView.text = vehicle.lastAnnualInspection

        // Initially hide the detailsContainer
        holder.detailsContainer.visibility = View.GONE

        // Set an OnClickListener for the expandButton
        holder.expandButton.setOnClickListener {
            val isVisible = holder.detailsContainer.visibility == View.VISIBLE

            // Toggle visibility of the detailsContainer
            val visibility = if (isVisible) View.GONE else View.VISIBLE
            holder.detailsContainer.visibility = visibility

            // Toggle the icon of the expandButton
            if (isVisible) {
                holder.expandButton.setImageResource(R.drawable.baseline_arrow_forward_ios_24)  // Down arrow
            } else {
                holder.expandButton.setImageResource(R.drawable.baseline_arrow_back_ios_24)  // Up arrow
            }
        }

        // Handle the update button click
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateVehicleActivity::class.java).apply {
                putExtra("vehicle_id", vehicle.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Handle the delete button click
        holder.deleteButton.setOnClickListener {
            db.deleteNote(vehicle.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Vehicle Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newVehicles: List<Vehicle>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }
}

