
package com.example.termproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.termproject.SQLiteHelper
import java.util.ArrayList
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val databaseName = "trip"
    val tableName = "TravelList"
    private var sqLiteHelper: SQLiteHelper? = null
    var database : SQLiteDatabase? = null

    lateinit var recyclerView: RecyclerView
    var folders: ArrayList<Array<String>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 폴더 추가 버튼 intent
        val button: Button = findViewById(R.id.addButton)
        button.setOnClickListener {
            val intent = Intent(this, AddFolderActivity::class.java)
            startActivity(intent)
        }

        //액티비티가 시작하면 자동으로 데이터베이스 가져오기
        sqLiteHelper = SQLiteHelper(this)
        folders = sqLiteHelper?.getTravelList()

        //리사이클러 뷰 초기화
        recyclerView = binding.recyclerView

        //리사이클러 뷰 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = folders?.let { FolderAdapter(this, it) }
        //folders는 arraylist<array<String>>형태의 이중 리스트로
        //sql에서 받아온 데이터값들임
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    fun clickFolder(num :Int, name : String) {
        val intent= Intent(this,PostActivity::class.java)
        intent.putExtra("folderNum",num.toString())
        intent.putExtra("folderName",name)
        startActivity(intent)
    }

    fun DeletePopup(id: Int, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("해당 여행 기록을 삭제하시겠습니까?")
            .setPositiveButton("삭제") { dialog, _ ->
                deleteFolder(id, position)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun deleteFolder(id: Int, position: Int) {
        sqLiteHelper?.deletePostItem(id)
        folders?.removeAt(position)
        //post 데이터 베이스에서도 같이 삭제해야함
        recyclerView.adapter?.notifyItemRemoved(position)
    }
}
