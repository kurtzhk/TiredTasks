package com.pagewisegroup.tiredtasks.ui.dashboard

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CardDatabaseManager(context: Context): SQLiteOpenHelper(context, "SavedCardsDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS CARDS(condition, symptoms, notes)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insert(condition: String, symptoms: String, notes: String){
        writableDatabase.execSQL("INSERT INTO CARDS VALUES(\"$condition\", \"$symptoms\", \"$notes\")")
    }

    fun removeCard(condition: String){
        val db = this.writableDatabase
        val conditionList = arrayOf(condition)
        db.delete("CARDS", "condition=?", conditionList)
    }

    fun readAllConditions(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT condition FROM CARDS", null)

        while(cursor.moveToNext()){
            val condition = cursor.getString(0)
            result.add(condition)
        }

        return result
    }

    fun readAllSymptoms(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT symptoms FROM CARDS", null)

        while(cursor.moveToNext()){
            val symptom = cursor.getString(0)
            result.add(symptom)
        }

        return result
    }

    fun readAllNotes(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT notes FROM CARDS", null)

        while(cursor.moveToNext()){
            val note = cursor.getString(0)
            result.add(note)
        }

        return result
    }

    fun findCondition(condition: String): String{
        lateinit var result: String
        val cursor = writableDatabase.rawQuery("SELECT condition FROM CARDS WHERE condition = \'$condition\'", null)

        if(cursor.moveToNext()){
            result = cursor.getString(0)
        }

        return result
    }

    fun containsCard(condition: String): Boolean{

        val cursor = writableDatabase.rawQuery("SELECT condition FROM CARDS WHERE condition = \'$condition\'", null)

        if(cursor.moveToNext()){
            return true
        }

        return false
    }
}