package com.np.appppppp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.np.appppppp.databinding.ActivityAddNoteBinding

class AddVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: VehiclesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VehiclesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val make = binding.titleEditText.text.toString()
            val model = binding.modelText.text.toString()
            val year = binding.yearText.text.toString().toInt()
            val color = binding.colorText.text.toString()
            val license = binding.licenseText.text.toString()
            val vin = binding.vinText.text.toString()
            val mileage = binding.mileageText.text.toString().toDouble()
            val vehicle = Vehicle(0, make, model, year, color, license, vin, mileage)
            db.insertNote(vehicle)
            finish()
            Toast.makeText(this, "Vehicle Saved", Toast.LENGTH_SHORT).show()
        }
    }
}