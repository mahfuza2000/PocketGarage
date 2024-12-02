package com.np.appppppp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.np.appppppp.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: VehiclesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VehiclesDatabaseHelper(this)


        binding.lastOilChange.setOnClickListener {
            showDatePickerDialog(binding.lastOilChange)
        }

        binding.lastATFChange.setOnClickListener {
            showDatePickerDialog(binding.lastATFChange)
        }

        binding.lastAirFilterChange.setOnClickListener {
            showDatePickerDialog(binding.lastAirFilterChange)
        }

        binding.lastBrakeInspection.setOnClickListener {
            showDatePickerDialog(binding.lastBrakeInspection)
        }

        binding.lastTireRotation.setOnClickListener {
            showDatePickerDialog(binding.lastTireRotation)
        }

        binding.lastAnnualInspection.setOnClickListener {
            showDatePickerDialog(binding.lastAnnualInspection)
        }


        // Handle save button click
        binding.saveButton.setOnClickListener {
            val make = binding.titleEditText.text.toString()
            val model = binding.modelText.text.toString()
            val year = binding.yearText.text.toString().toInt()
            val color = binding.colorText.text.toString()
            val license = binding.licenseText.text.toString()
            val vin = binding.vinText.text.toString()
            val mileage = binding.mileageText.text.toString().toDouble()
            val lastOilChange = binding.lastOilChange.text.toString()
            val lastATFChange = binding.lastATFChange.text.toString()
            val lastAirFilterChange = binding.lastAirFilterChange.text.toString()
            val lastBrakeInspection = binding.lastBrakeInspection.text.toString()
            val lastTireRotation = binding.lastTireRotation.text.toString()
            val lastAnnualInspection = binding.lastAnnualInspection.text.toString()

            // Ensure that the date is not empty
            if (lastOilChange.isEmpty()) {
                Toast.makeText(this, "Please select the last oil change date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val vehicle = Vehicle(0, make, model, year, color, license, vin, mileage, lastOilChange, lastATFChange,
                lastAirFilterChange, lastBrakeInspection, lastTireRotation, lastAnnualInspection)
            db.insertNote(vehicle)
            finish()
            Toast.makeText(this, "Vehicle Saved", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to open DatePickerDialog
    private fun showDatePickerDialog(targetView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date as "YYYY-MM-DD"
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                targetView.text = sdf.format(selectedDate.time)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
