package com.example.termproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.RatingBar
import androidx.activity.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddFolderActivityActivity : AppCompatActivity() {
    private var sqLiteHelper: SQLiteHelper? = null
    private var datePicker: DatePicker? = null
    private var ratingBar: RatingBar? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_main)  // Set the content view to input_main.xml

        datePicker = findViewById<DatePicker>(R.id.datePicker)
        ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        saveButton = findViewById<Button>(R.id.saveButton)
        cancelButton = findViewById<Button>(R.id.cancelButton)

        sqLiteHelper = SQLiteHelper(this)

        saveButton?.setOnClickListener {
            val year = datePicker?.year ?: return@setOnClickListener
            val month = datePicker?.month ?: return@setOnClickListener
            val day = datePicker?.dayOfMonth ?: return@setOnClickListener
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val date = "$year-${month + 1}-$day"
            val rating = ratingBar?.rating?.toInt() ?: return@setOnClickListener

            // Save to database (you need to implement the insert function in SQLiteHelper)
            sqLiteHelper?.insertTravelItem(date, rating)

            val intent = Intent(this@InputActivity, MainActivity::class.java)
            startActivity(intent)
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }
}
