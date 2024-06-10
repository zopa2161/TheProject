package com.example.termproject

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
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
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.ActivityMainBinding
import com.example.termproject.databinding.ActivityWriteBinding
import com.example.termproject.ui.theme.TermProjectTheme


class WriteActivity : AppCompatActivity(){

    lateinit var binding: ActivityWriteBinding
    private var sqLiteHelper: SQLiteHelper? = null
    var imageUri :Uri? = null
    lateinit var folderName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val folderNum = intent.getStringExtra("folderNum")
        val fN = intent.getStringExtra("folderName")
        folderName = fN!!
        sqLiteHelper = SQLiteHelper(this)

        binding.imageWrite.isClickable = true
        binding.imageWrite.setOnClickListener{
            openGallery()
        }
        binding.addButton.setOnClickListener {

            if(imageUri == null){
                Toast.makeText(this, "이미지가 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener



            }
            else{
                this.contentResolver.takePersistableUriPermission(imageUri!!,Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val imageString = imageUri.toString()//uri를 가져오자
                val textWriting = binding.txtWrite.text.toString()
                sqLiteHelper?.insertTravelDetailItem(imageString,textWriting, folderNum!!.toInt())

                val intent = Intent(this,PostActivity::class.java)
                intent.putExtra("folderNum", folderNum)
                intent.putExtra("folderName", folderName)
                startActivity(intent)
                finish()

            }

        }

        binding.cancle.setOnClickListener {

            val intent = Intent(this,PostActivity::class.java)
            intent.putExtra("folderNum", folderNum)
            intent.putExtra("folderName", folderName)
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
                    imageUri = ImageData
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