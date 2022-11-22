package com.pagewisegroup.tiredtasks.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pagewisegroup.tiredtasks.models.MedicationModel

class DatabaseHandler(
    context: Context?
) : SQLiteOpenHelper(context, "medicationListDatabase", null,1) {
    private var MED_TABLE = "medication"
    private var ID = "id"
    private var MEDDOSE = "dose"
    private var MEDNAME = "name"
    private var MEDFREQ = "frequency"
    private var MEDSTATUS = "status"
    private var CREATE_MED_TABLE =
        "CREATE TABLE " + MED_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        MEDDOSE + " TEXT, " + MEDNAME + " TEXT, " + MEDFREQ + " TEXT, " + MEDSTATUS + " INTEGER);"

    private lateinit var db: SQLiteDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL(CREATE_MED_TABLE)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL("DROP TABLE IF EXISTS $MED_TABLE")

            onCreate(p0)
        }
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    fun insertMedication(med: MedicationModel) {
        var cv= ContentValues()
        cv.put(MEDDOSE, med.dose)
        cv.put(MEDNAME, med.name)
        cv.put(MEDFREQ, med.frequency)
        cv.put(MEDSTATUS, if (med.status) 1 else 0)
        db.insert(MED_TABLE, null, cv)
    }

    fun getAllMedications(): ArrayList<MedicationModel> {
        var medList = ArrayList<MedicationModel>()
        var cur: Cursor
        db.beginTransaction()
        try {
            cur = db.query(MED_TABLE, null, null, null, null, null, null, null)
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        var medModel = MedicationModel()
                        medModel.id = cur.getInt(cur.getColumnIndex(ID))
                        medModel.name = cur.getString(cur.getColumnIndex(MEDNAME))
                        medModel.frequency = cur.getString(cur.getColumnIndex(MEDFREQ))
                        medModel.dose = cur.getString(cur.getColumnIndex(MEDDOSE))
                        medModel.status = cur.getInt(cur.getColumnIndex(MEDSTATUS)) != 0
                        medList.add(medModel)
                    } while (cur.moveToNext())
                }
                cur.close()
            }
        } finally {
            db.endTransaction()
        }
        return medList
    }

    fun updateStatus(id: Int, status: Boolean) {
        var cv = ContentValues()
        cv.put(MEDSTATUS, if (status) 1 else 0)
        db.update(MED_TABLE, cv, "$ID=?", arrayOf(id.toString()))
    }

    fun deleteMedication(id: Int) {
        db.delete(MED_TABLE, "$ID=?", arrayOf(id.toString()))
    }
}