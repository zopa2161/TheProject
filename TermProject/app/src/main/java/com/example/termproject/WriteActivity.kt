package com.example.termproject

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
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
import com.example.termproject.databinding.ActivityWriteBinding
import com.example.termproject.ui.theme.TermProjectTheme


class WriteActivity : AppCompatActivity(){
    private var sqLiteHelper: SQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageWrite.isClickable = true
        binding.imageWrite.setOnClickListener{
            openGallery()
        }

        val folderNum = intent.getStringExtra("folderNum")
        sqLiteHelper = SQLiteHelper(this)

    }
    fun openGallery(){

        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,1)

    }

    private fun resizeBitmap(bitmap: Bitmap, width:Int, height:Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap,width,height,false)
    }
}