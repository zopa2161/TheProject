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
    var posts: ArrayList<Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val folderNum = intent.getStringExtra("folderNum")
        binding.mainTextView.text = folderNum.toString()



        sqLiteHelper = SQLiteHelper(this)
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        posts = ArrayList<Array<String>>()
        posts = sqLiteHelper?.getTravelDetail(folderNum!!.toInt())

        binding.addButton.setOnClickListener {

            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("folderNum",folderNum)
            startActivity(intent)
            finish()

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = PostAdapter(this, posts)
        //folders는 arraylist<array<String>>형태의 이중 리스트로
        //sql에서 받아온 데이터값들임
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )




    }


    fun clickPost(num :Int){
        
        //해당 아이디 등의 정보를 제공 받아서 해당 포스트 보여주기

    }

}