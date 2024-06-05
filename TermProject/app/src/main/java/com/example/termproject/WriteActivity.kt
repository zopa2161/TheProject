package com.example.termproject

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
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

    lateinit var binding: ActivityWriteBinding
    private var sqLiteHelper: SQLiteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val folderNum = intent.getStringExtra("folderNum")
        sqLiteHelper = SQLiteHelper(this)

        binding.imageWrite.isClickable = true
        binding.imageWrite.setOnClickListener{
            openGallery()
        }
        binding.addButton.setOnClickListener {
            //여기서 sql에 데이터 추가
            /*
            if(!binding.imageWrite.isActivated() or !binding.txtWrite.isActivated()){
                //토스트 메세지
                //암튼 두개중 하나만 없어도 안된다는 메세지를 뛰워야함
            }

            */

        }

        binding.cancle.setOnClickListener {

            val intent = Intent(this,PostActivity::class.java)
            intent.putExtra("folderNum", folderNum)
            startActivity(intent)
            finish()

        }



    }
    fun openGallery(){

        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,1)

    }

    private fun resizeBitmap(bitmap: Bitmap, width:Int, height:Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap,width,height,false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {


                    var ImageData: Uri? = data?.data
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, ImageData)
                        binding.imageWrite.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}