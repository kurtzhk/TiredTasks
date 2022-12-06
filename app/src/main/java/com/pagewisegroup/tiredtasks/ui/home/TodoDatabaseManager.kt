package com.pagewisegroup.tiredtasks.ui.home

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabaseManager(context: Context): SQLiteOpenHelper(context, "SavedToDosDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS TODO(task, priority, energy)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insert(task: String, priority: String, energy: String){
        writableDatabase.execSQL("INSERT INTO TODO VALUES(\"$task\", \"$priority\", \"$energy\")")
    }

    fun removeTask(task: String){
        val db = this.writableDatabase
        val taskList = arrayOf(task)
        db.delete("TODO", "task=?", taskList)
    }

    fun readAllTasks(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT task FROM TODO", null)

        while(cursor.moveToNext()){
            val task = cursor.getString(0)
            result.add(task)
        }

        return result
    }

    fun readAllPriorities(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT priority FROM TODO", null)

        while(cursor.moveToNext()){
            val priority = cursor.getString(0)
            result.add(priority)
        }

        return result
    }

    fun readAllEnergy(): MutableList<String>{
        val result = mutableListOf<String>()
        val cursor = writableDatabase.rawQuery("SELECT energy FROM TODO", null)

        while(cursor.moveToNext()){
            val energy = cursor.getString(0)
            result.add(energy)
        }

        return result
    }

    fun findTask(task: String): String{
        lateinit var result: String
        val cursor = writableDatabase.rawQuery("SELECT task FROM TODO WHERE task = \'$task\'", null)

        if(cursor.moveToNext()){
            result = cursor.getString(0)
        }

        return result
    }

    fun containsTask(task: String): Boolean{

        val cursor = writableDatabase.rawQuery("SELECT task FROM TODO WHERE task = \'$task\'", null)

        if(cursor.moveToNext()){
            return true
        }

        return false
    }
}