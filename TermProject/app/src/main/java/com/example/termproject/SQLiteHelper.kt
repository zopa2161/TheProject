package com.example.termproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TravelList (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT, " +
                "map TEXT, " +
                "rate INTEGER);")
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS TravelDetail (" +
                "d_ID INTEGER, " +
                "picture TEXT, " +
                "txt TEXT, " +
                "FOREIGN KEY (d_ID) REFERENCES TravelList(ID));")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TravelList")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TravelDetail")
        onCreate(sqLiteDatabase)
    }

    inner class TravelListItem(var date: String, var rate: Int) {
        //inner class TravelListItem(var date: String, var map: String, var rate: Int) {
        override fun toString(): String {
            return "$date - $rate"
        }
    }
    inner class TravelDetailItem(var picture: String, var txt: String)

    companion object {
        private const val DATABASE_NAME = "mytestgo.db"
        private const val DATABASE_VERSION = 1
    }

    fun getTravelList(): ArrayList<TravelListItem>? {
        val result = ArrayList<TravelListItem>()
        var db: SQLiteDatabase? = null
        try {
            db = this.readableDatabase
            val cursor = db.rawQuery("SELECT Date, rate FROM TravelList", null)
            //val cursor = db.rawQuery("SELECT Date, map, rate FROM TravelList", null)
            while (cursor.moveToNext()) {
                val date = cursor.getString(0)
                //val map = cursor.getString(1)
                val rate = cursor.getInt(1)
                result.add(TravelListItem(date, rate))
                //result.add(TravelListItem(date, map, rate))
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("SQLiteHelper", "Error while getting travel list", e)
        } finally {
            db?.close()
        }
        return result
    }

    fun getTravelDetail(): ArrayList<TravelDetailItem>? {
        val result = ArrayList<TravelDetailItem>()
        var db: SQLiteDatabase? = null
        try {
            db = this.readableDatabase
            val cursor = db.rawQuery("SELECT picture, txt FROM TravelDetail", null)
            while (cursor.moveToNext()) {
                val picture = cursor.getString(0)
                val txt = cursor.getString(1)
                result.add(TravelDetailItem(picture, txt))
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("SQLiteHelper", "Error while getting travel detail", e)
        } finally {
            db?.close()
        }
        return result
    }

    fun insertTravelItem(date: String, rate: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("Date", date)
            put("rate", rate)
        }
        db.insert("TravelList", null, contentValues)
    }
}