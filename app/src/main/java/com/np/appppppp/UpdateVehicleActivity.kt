package com.np.appppppp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.np.appppppp.databinding.ActivityUpdateNoteBinding


class UpdateVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: VehiclesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VehiclesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.make)
        binding.updateModelText.setText(note.model)
        binding.updateYearText.setText(note.year.toString())
        binding.updateColorText.setText(note.color)
        binding.updateLicenseText.setText(note.license)
        binding.updateVinText.setText(note.vin)
        binding.updateMileageText.setText(note.mileage.toString())


        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newModel = binding.updateModelText.text.toString()
            val newYear = binding.updateYearText.text.toString().toInt()
            val newColor = binding.updateColorText.text.toString()
            val newLicense = binding.updateLicenseText.text.toString()
            val newVin = binding.updateVinText.text.toString()
            val newMileage = binding.updateMileageText.text.toString().toDouble()
            val updatedVehicle = Vehicle(noteId, newTitle, newModel, newYear, newColor, newLicense, newVin, newMileage)
            db.updateNote(updatedVehicle)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}