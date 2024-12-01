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
            val modelTextView: TextView = itemView.findViewById(R.id.modelTextView)
            val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
            val colorTextView: TextView = itemView.findViewById(R.id.colorTextView)
            val licenseTextView: TextView = itemView.findViewById(R.id.licenseTextView)
            val vinTextView: TextView = itemView.findViewById(R.id.vinTextView)
            val mileageTextView: TextView = itemView.findViewById(R.id.mileageTextView)

            val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
            val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = vehicles.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = vehicles[position]
        holder.titleTextView.text = note.make
        holder.modelTextView.text = note.model
        holder.yearTextView.text = note.year.toString()
        holder.colorTextView.text = note.color
        holder.licenseTextView.text = note.license
        holder.vinTextView.text = note.vin
        holder.mileageTextView.text = note.mileage.toString()

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateVehicleActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Vehicle Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newVehicles: List<Vehicle>){
        vehicles = newVehicles
        notifyDataSetChanged()
    }
}