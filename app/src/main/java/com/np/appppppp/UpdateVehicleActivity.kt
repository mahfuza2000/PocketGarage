package com.np.appppppp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.np.appppppp.databinding.ActivityUpdateNoteBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class UpdateVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: VehiclesDatabaseHelper
    private var vehicleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VehiclesDatabaseHelper(this)

        vehicleId = intent.getIntExtra("vehicle_id", -1)
        if (vehicleId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(vehicleId)
        binding.updateTitleEditText.setText(note.make)
        binding.updateModelText.setText(note.model)
        binding.updateYearText.setText(note.year.toString())
        binding.updateColorText.setText(note.color)
        binding.updateLicenseText.setText(note.license)
        binding.updateVinText.setText(note.vin)
        binding.updateMileageText.setText(note.mileage.toString())
        binding.updateLastOilChange.setText(note.lastOilChange.toString())

        binding.updateLastATFChange.setText(note.lastATFChange.toString())
        binding.updateLastAirFilterChange.setText(note.lastAirFilterChange.toString())
        binding.updateLastBrakeInspection.setText(note.lastBrakeInspection.toString())
        binding.updateLastTireRotation.setText(note.lastTireRotation.toString())
        binding.updateLastAnnualInspection.setText(note.lastAnnualInspection.toString())


        binding.updateLastOilChange.setOnClickListener {
            showDatePickerDialog(binding.updateLastOilChange)
        }
        binding.updateLastATFChange.setOnClickListener {
            showDatePickerDialog(binding.updateLastATFChange)
        }
        binding.updateLastAirFilterChange.setOnClickListener {
            showDatePickerDialog(binding.updateLastAirFilterChange)
        }
        binding.updateLastBrakeInspection.setOnClickListener {
            showDatePickerDialog(binding.updateLastBrakeInspection)
        }
        binding.updateLastTireRotation.setOnClickListener {
            showDatePickerDialog(binding.updateLastTireRotation)
        }
        binding.updateLastAnnualInspection.setOnClickListener {
            showDatePickerDialog(binding.updateLastAnnualInspection)
        }

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newModel = binding.updateModelText.text.toString()
            val newYear = binding.updateYearText.text.toString().toInt()
            val newColor = binding.updateColorText.text.toString()
            val newLicense = binding.updateLicenseText.text.toString()
            val newVin = binding.updateVinText.text.toString()
            val newMileage = binding.updateMileageText.text.toString().toDouble()
            val newLastOilChange = binding.updateLastOilChange.text.toString()
            val newLastATFChange = binding.updateLastATFChange.text.toString()
            val newLastAirFilterChange = binding.updateLastAirFilterChange.text.toString()
            val newLastBrakeInspection = binding.updateLastBrakeInspection.text.toString()
            val newLastTireRotation = binding.updateLastTireRotation.text.toString()
            val newLastAnnualInspection = binding.updateLastAnnualInspection.text.toString()

            val updatedVehicle = Vehicle(vehicleId, newTitle, newModel, newYear, newColor, newLicense,
                newVin, newMileage, newLastOilChange, newLastATFChange, newLastAirFilterChange,
                newLastBrakeInspection, newLastTireRotation, newLastAnnualInspection)
            db.updateNote(updatedVehicle)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }

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
                targetView.text = sdf.format(selectedDate.time) // Set the formatted date in the target TextView
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}