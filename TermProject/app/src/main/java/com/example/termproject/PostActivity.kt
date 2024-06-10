package com.example.termproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
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
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val folderNum = intent.getStringExtra("folderNum")
        val folderName = intent.getStringExtra("folderName")
        binding.postName.text = folderName



        sqLiteHelper = SQLiteHelper(this)
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null)
        posts = ArrayList<Array<String>>()
        posts = sqLiteHelper?.getTravelDetail(folderNum!!.toInt())

        binding.addButton.setOnClickListener {

            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("folderNum",folderNum)
            intent.putExtra("folderName",folderName)
            startActivity(intent)
            finish()

        }
        recyclerView = binding.recyclerView

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = posts?.let { PostAdapter(this, it) }
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

    fun DeletePopup(id: Int, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("해당 여행 기록을 삭제하시겠습니까?")
            .setPositiveButton("삭제") { dialog, _ ->
                DeletePost(id, position)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
    fun DeletePost(id: Int, position: Int) {
        sqLiteHelper?.deletePostItem(id)
        posts?.removeAt(position)
        //post 데이터 베이스에서도 같이 삭제해야함
        recyclerView.adapter?.notifyItemRemoved(position)
    }

}