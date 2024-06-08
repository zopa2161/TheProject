package com.example.termproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.FolderItemBinding

class FolderViewHolder(val binding: FolderItemBinding): RecyclerView.ViewHolder(binding.root)


class FolderAdapter(main: MainActivity,
                    val datas: ArrayList<Array<String>>,
) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int = datas.size
    val mainA = main


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = FolderViewHolder(
        FolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        //항목 뷰를 가지는 뷰 홀더를 준비하기 위해 자동 호출
        val binding = (holder as FolderViewHolder).binding

        val num = datas[position][0].toString().toInt()

        //datas[position][0] // 이런식으로 데이터를 사용해야할듯?
        //binding.num.text = datas!![position][0]
        binding.title.text = datas[position][1]
        binding.date.text = "날짜: " + datas[position][2] + " ~ " + datas[position][3]
        binding.location.text = "장소: " + datas[position][4]
        binding.rating.text = "평점: " + "★".repeat(datas[position][5].toInt())
        binding.itemRoot.setOnClickListener {
            //클릭하면
            //여기서 포스트액티비티로 넘어가는 작업 수행

            mainA.clickFolder(num)// 인자로 눌려진 데이터의 값이 들어가야한다.

        }

        binding.itemRoot.setOnLongClickListener {
            mainA.DeletePopup(num, position)
            true
        }

    }

}