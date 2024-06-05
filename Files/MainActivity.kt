package com.example.termproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.termproject.SQLiteHelper
import java.util.ArrayList
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var sqLiteHelper: SQLiteHelper? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.addButton)
        button.setOnClickListener {
            val intent = Intent(this, AddFolderActivity::class.java)
            startActivity(intent)
        }

        sqLiteHelper = SQLiteHelper(this)
        textView = findViewById<TextView>(R.id.mainTextView)
    }

    override fun onResume() {
        super.onResume()
        updateTravelList()
    }

    private fun updateTravelList() {
        val nameList: ArrayList<SQLiteHelper.TravelListItem>? = sqLiteHelper?.getTravelList()
        val displayList = nameList?.joinToString("\n") { it.toString() }
        textView?.text = displayList ?: "No data available"
    }
}
