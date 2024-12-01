package com.np.appppppp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.np.appppppp.databinding.ActivityMainBinding


class MyVehiclesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: VehiclesDatabaseHelper
    private lateinit var vehiclesAdapter: VehiclesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = VehiclesDatabaseHelper(this)
        vehiclesAdapter = VehiclesAdapter(db.getAllNotes(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = vehiclesAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        vehiclesAdapter.refreshData(db.getAllNotes())
    }
}