
package com.example.termproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFolderActivity : AppCompatActivity() {
    private var sqLiteHelper: SQLiteHelper? = null

    private var travelTitle: EditText? = null

    private var datePicker: DatePicker? = null
    private var startDateButton: Button? = null
    private var endDateButton: Button? = null
    private var startDateInfo: TextView? = null
    private var endDateInfo: TextView? = null

    private var mapInfo: TextView? = null
    private var mapsaveButton: Button? = null
    private var mapcancelButton: Button? = null
    private var ratingBar: RatingBar? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private var startDate: Calendar? = Calendar.getInstance()
    private var endDate: Calendar? = Calendar.getInstance()

    private var cityName: String? = null

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfolder)

        travelTitle = findViewById(R.id.inputTitle)

        datePicker = findViewById(R.id.datePicker)
        startDateButton = findViewById(R.id.startDateButton)
        endDateButton = findViewById(R.id.endDateButton)
        startDateInfo = findViewById(R.id.startDateInfo)
        endDateInfo = findViewById(R.id.endDateInfo)

        mapInfo = findViewById(R.id.mapInfo)
        cityName = intent.getStringExtra("cityName")
        mapInfo?.text = "장소 정보: ${cityName ?: "없음"}"

        mapsaveButton = findViewById(R.id.mapsaveButton)
        mapcancelButton = findViewById(R.id.mapcancelButton)
        ratingBar = findViewById(R.id.ratingBar)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        sqLiteHelper = SQLiteHelper(this)

        // 초기 날짜 설정
        startDate?.let {
            startDateInfo?.text = "여행 시작 날짜: ${formatDate(startDate)}"
        }
        endDate?.let {
            endDateInfo?.text = "여행 종료 날짜: ${formatDate(endDate)}"
        }

        startDateButton?.setOnClickListener {
            startDate = getSelectedDate(datePicker)
            startDateInfo?.text = "여행 시작 날짜: ${formatDate(startDate)}"
        }

        endDateButton?.setOnClickListener {
            endDate = getSelectedDate(datePicker)
            endDateInfo?.text = "여행 종료 날짜: ${formatDate(endDate)}"
        }

        showMapFragment()

        // mapsaveButton 클릭 시 cityName을 저장
        mapsaveButton?.setOnClickListener {
            cityName = (supportFragmentManager.findFragmentById(R.id.fragment_container) as? MapFragment)?.cityName
            mapInfo?.text = "장소 정보: ${cityName ?: "없음"}"
        }

        // mapcancelButton 클릭 시 cityName을 "없음"으로 설정
        mapcancelButton?.setOnClickListener {
            cityName = null
            mapInfo?.text = "장소 정보: ${cityName ?: "없음"}"
        }

        saveButton?.setOnClickListener {
            val title = travelTitle?.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, "여행 제목을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (startDate!!.after(endDate)) {
                // Toast 메시지 표시
                Toast.makeText(this, "여행 시작 날짜는 여행 종료 날짜와 같거나 이전이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val startDate_str = formatDate(startDate)
            val endDate_str = formatDate(endDate)

            val cityName = cityName

            val rating = ratingBar?.rating?.toInt() ?: return@setOnClickListener

            if (cityName == null) {
                Toast.makeText(this, "여행 장소를 지정해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqLiteHelper?.insertTravelItem(title, startDate_str, endDate_str, cityName, rating)

            val intent = Intent(this@AddFolderActivity, MainActivity::class.java)
            startActivity(intent)
        }

        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun getSelectedDate(datePicker: DatePicker?): Calendar {
        val calendar = Calendar.getInstance()
        datePicker?.let {
            calendar.set(it.year, it.month, it.dayOfMonth)
        }
        return calendar
    }

    private fun formatDate(calendar: Calendar?): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return if (calendar != null) dateFormat.format(calendar.time) else ""
    }

    private fun showMapFragment() {
        val fragment = MapFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
