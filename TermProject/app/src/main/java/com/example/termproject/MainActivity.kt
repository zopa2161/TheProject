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
    var folders: ArrayList<Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        //액티비티가 시작하면 자동으로 데이터베이스 가져오기
        //database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        sqLiteHelper = SQLiteHelper(this)
        folders = sqLiteHelper?.getTravelList();
        //database에서 불러옴
        /*
        val cursor = database?.rawQuery("select *"+
            "from ${tableName}",null)

        if(cursor != null){
            for (index in 0 until cursor.count){
                cursor.moveToNext()
                val id = cursor.getInt(0).toString()
                val date = cursor.getString(1)
                val map = cursor.getString(2)
                val rate = cursor.getInt(3).toString()
                var tempList: Array<String> =
                    arrayOf(id, date, map, rate)
                folders.add(tempList)
            }

        }
        */

        //리사이클러 뷰 연결
        val test1 : Array<String> = arrayOf("1","2","3","4")
        folders?.add(test1)
        folders?.add(test1)
        folders?.add(test1)
        folders?.add(test1)
        folders?.add(test1)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = FolderAdapter(this, folders)
        //folders는 arraylist<array<String>>형태의 이중 리스트로
        //sql에서 받아온 데이터값들임
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

    }

    //리사이클러 뷰 아이템을 눌렀을 때
    fun clickFolder(num :Int){

        val intent= Intent(this,PostActivity::class.java)
        intent.putExtra("num",num.toString())
        startActivity(intent)


    }

}

