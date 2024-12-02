package com.np.appppppp

data class Vehicle(
    val id: Int,
    val make: String,
    val model: String,
    val year: Int,
    val color: String,
    val license: String,
    val vin: String,
    val mileage: Double,
    val lastOilChange: String?,
    val lastATFChange: String?,
    val lastAirFilterChange: String?,
    val lastBrakeInspection: String?,
    val lastTireRotation: String?,
    val lastAnnualInspection: String?
) {
    val detail: String
        get() = "$year $make $model"
}

