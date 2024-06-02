package com.example.termproject

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
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.ui.theme.TermProjectTheme

class MainActivity : AppCompatActivity() {
    val databaseName = "trip"
    val tableName = "folder"
    private var sqLiteHelper: SQLiteHelper? = null
    var database :SQLiteDatabase? = null

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val binding = RecyclerViewBinding.inflate(layoutInflater)
        recyclerView = root.recyclerView
        recyclerView.adapter = FolderAdater()


        database = openOrCreateDatabase(databaseName, MODE_PRIVATE,null)
        database?.execSQL("create table if not exists ${tableName}"+
        "(id integer PRIMARY KEY autoincrement,"+
        )




    }
}

