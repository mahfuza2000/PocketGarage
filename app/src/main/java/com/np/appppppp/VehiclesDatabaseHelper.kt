package com.np.appppppp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VehiclesDatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "pgaVehicles.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allVehicles"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MAKE = "make"
        private const val COLUMN_MODEL = "model"
        private const val COLUMN_YEAR = "year"
        private const val COLUMN_COLOR = "color"
        private const val COLUMN_LICENSE = "license"
        private const val COLUMN_VIN = "vin"
        private const val COLUMN_MILEAGE = "mileage"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_MAKE TEXT, $COLUMN_MODEL TEXT, $COLUMN_YEAR INTEGER, $COLUMN_COLOR TEXT, $COLUMN_LICENSE TEXT, $COLUMN_VIN TEXT, $COLUMN_MILEAGE DOUBLE)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    fun insertNote(vehicle: Vehicle){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MAKE, vehicle.make)
            put(COLUMN_MODEL, vehicle.model)
            put(COLUMN_YEAR, vehicle.year)
            put(COLUMN_COLOR, vehicle.color)
            put(COLUMN_LICENSE, vehicle.license)
            put(COLUMN_VIN, vehicle.vin)
            put(COLUMN_MILEAGE, vehicle.mileage)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotes(): List<Vehicle> {
        val notesList = mutableListOf<Vehicle>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val make = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAKE))
            val model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))
            val license = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSE))
            val vin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIN))
            val mileage = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MILEAGE))

            val vehicle = Vehicle(id, make, model, year, color, license, vin, mileage)
            notesList.add(vehicle)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(vehicle: Vehicle){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MAKE, vehicle.make)
            put(COLUMN_MODEL, vehicle.model)
            put(COLUMN_YEAR, vehicle.year)
            put(COLUMN_COLOR, vehicle.color)
            put(COLUMN_LICENSE, vehicle.license)
            put(COLUMN_VIN, vehicle.vin)
            put(COLUMN_MILEAGE, vehicle.mileage)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(vehicle.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteID: Int): Vehicle{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val make = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAKE))
        val model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL))
        val year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))
        val license = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSE))
        val vin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIN))
        val mileage = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MILEAGE))

        cursor.close()
        db.close()
        return Vehicle(id, make, model, year, color, license, vin, mileage)
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}