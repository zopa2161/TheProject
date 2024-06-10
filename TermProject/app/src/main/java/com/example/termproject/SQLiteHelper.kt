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
                "travelTitle TEXT, " +
                "startDate TEXT, " +
                "endDate TEXT, " +
                "cityName TEXT, " +
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

    fun getTravelList(): ArrayList<Array<String>>? {
        val result = ArrayList<Array<String>>()
        var db: SQLiteDatabase? = null
        try {
            db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM TravelList", null)
            //val cursor = db.rawQuery("SELECT TravelTitle, startDate, endDate, map, rate FROM TravelList", null)
            while (cursor.moveToNext()) {
                val id = cursor.getString(0)
                val travelTitle = cursor.getString(1)
                val startDate = cursor.getString(2)
                val endDate = cursor.getString(3)
                val map = cursor.getString(4) ?: "없음"
                val rate = cursor.getInt(5)
                val temp : Array<String> = arrayOf(id, travelTitle, startDate, endDate, map, rate.toString())
                result.add(temp)
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

    fun getTravelDetail(fid :Int): ArrayList<Array<String>>? {
        val result = ArrayList<Array<String>>()
        var db: SQLiteDatabase? = null
        try {
            db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM TravelDetail where d_ID = $fid", null)
            while (cursor.moveToNext()) {
                val id = cursor.getString(0)
                val picture = cursor.getString(1)
                val txt = cursor.getString(2)
                val temp : Array<String> = arrayOf(id, picture, txt)
                result.add(temp)
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("SQLiteHelper", "Error while getting travel detail", e)
        } finally {
            db?.close()
        }
        return result
    }

    fun insertTravelItem(travelTitle: String, startDate: String, endDate: String, cityName: String, rate: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("travelTitle", travelTitle)
            put("startDate", startDate)
            put("endDate", endDate)
            put("cityName", cityName)
            put("rate", rate)
        }
        db.insert("TravelList", null, contentValues)
    }

    fun insertTravelDetailItem(image:String, text:String, fNum:Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("d_Id", fNum)
            put("picture", image)
            put("txt", text)
        }
        db.insert("TravelDetail", null, contentValues)
    }

    fun deleteTravelItem(id: Int) {
        val db = this.writableDatabase
        db.delete("TravelList", "ID=?", arrayOf(id.toString()))
        db.delete("TravelDetail", "d_ID=?", arrayOf(id.toString()))
    }
}