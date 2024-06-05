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
import com.example.termproject.databinding.ActivityPostBinding
import com.example.termproject.ui.theme.TermProjectTheme
class PostActivity :AppCompatActivity(){
    val databaseName = "trip"
    val tableName = "TraveDetail"
    var database :SQLiteDatabase? = null
    private var sqLiteHelper: SQLiteHelper? = null
    lateinit var posts: ArrayList<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val folderNum = intent.getStringExtra("num")
        binding.mainTextView.text = folderNum.toString()


        val numFolder = intent.getStringExtra("num")?.toInt()
        sqLiteHelper = SQLiteHelper(this)
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        posts = ArrayList<Array<String>>()

        binding.addButton.setOnClickListener {

            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("folderNum",folderNum)
            startActivity(intent)

        }
        /*

        val cursor = database?.rawQuery("select *"+
                "from ${tableName}",null)

        if(cursor != null){
            for (index in 0 until cursor.count){
                cursor.moveToNext()
                val id = cursor.getInt(0).toString()
                val date = cursor.getString(1)
                val map = cursor.getString(2)
                val fk = cursor.getInt(3)
                if(fk == folderNum?.toInt()){
                    val tempList: Array<String> =
                        arrayOf(id, date, map, fk.toString())
                    posts.add(tempList)
                }

            }

        }

         */



    }


    fun clickPost(num :Int){
        
        //해당 아이디 등의 정보를 제공 받아서 해당 포스트 보여주기

    }

}