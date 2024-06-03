package com.example.termproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.ActivityMainBinding
import com.example.termproject.ui.theme.TermProjectTheme

class MainActivity : AppCompatActivity() {
    val databaseName = "trip"
    val tableName = "TravelList"
    private var sqLiteHelper: SQLiteHelper? = null
    var database :SQLiteDatabase? = null

    lateinit var recyclerView: RecyclerView
    lateinit var folders: ArrayList<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //액티비티가 시작하면 자동으로 데이터베이스 가져오기
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE,null)

        /*
        database?.execSQL("create table if not exists ${tableName}"+
                "(id integer PRIMARY KEY autoincrement,"+
        )
        */

        folders = ArrayList<Array<String>>()
        //데이터 베이스에서 가져온 값을 Array<String>으로 만든 후 for문을 이용해 folders에 add 하기





        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = FolderAdapter(folders, itemClickListener = {
            val intent= Intent(this,PostActivity::class.java)

            startActivity(intent)
        })//folders는 arraylist<array<String>>형태의 이중 리스트로
        //sql에서 받아온 데이터값들임
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))








    }

}

