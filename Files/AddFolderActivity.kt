package com.example.termproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.termproject.SQLiteHelper
import java.util.Calendar

class AddFolderActivity : AppCompatActivity() {
    private var sqLiteHelper: SQLiteHelper? = null
    private var datePicker: DatePicker? = null
    private var mapButton: Button? = null
    private var mapInfo: TextView? = null
    private var ratingBar: RatingBar? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private var cityName: String? = null;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfolder)  // Set the content view to input_main.xml

        datePicker = findViewById<DatePicker>(R.id.datePicker)

        mapButton = findViewById<Button>(R.id.mapButton)
        mapInfo = findViewById<TextView>(R.id.mapInfo)
        cityName = intent.getStringExtra("cityName")
        mapInfo?.text = "장소 정보: ${cityName ?: "없음"}"

        ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        saveButton = findViewById<Button>(R.id.saveButton)
        cancelButton = findViewById<Button>(R.id.cancelButton)

        sqLiteHelper = SQLiteHelper(this)

        mapButton?.setOnClickListener {
            val intent = Intent(this@AddFolderActivity, MapActivity::class.java)
            startActivity(intent)
        }

        saveButton?.setOnClickListener {
            val year = datePicker?.year ?: return@setOnClickListener
            val month = datePicker?.month ?: return@setOnClickListener
            val day = datePicker?.dayOfMonth ?: return@setOnClickListener
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val date = "$year-${month + 1}-$day"

            val cityName = intent.getStringExtra("cityName")

            val rating = ratingBar?.rating?.toInt() ?: return@setOnClickListener

            if (cityName != null) {
                sqLiteHelper?.insertTravelItem(date, cityName, rating)
            }

            val intent = Intent(this@AddFolderActivity, MainActivity::class.java)
            startActivity(intent)
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }
}
