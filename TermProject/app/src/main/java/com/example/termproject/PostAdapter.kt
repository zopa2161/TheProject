package com.example.termproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.FolderItemBinding

class PostAdapter (main: PostActivity,
        val datas: ArrayList<Array<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount(): Int = datas.size
    val postA = main


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = FolderViewHolder(
        FolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        //항목 뷰를 가지는 뷰 홀더를 준비하기 위해 자동 호출
        val binding = (holder as FolderViewHolder).binding

        datas[position][0] // 이런식으로 데이터를 사용해야할듯?
        binding.num.text = ""
        binding.location.text = ""//받아온 데이터 값을 주르륵 연결해야함
        binding.date.text = ""
        binding.rating.text = ""
        binding.itemRoot.setOnClickListener {
            //클릭하면
            //여기서 포스트액티비티로 넘어가는 작업 수행
            //itemClickListener()//이 안에는 해당 데이터의 id값이 들어가야한다?)
            postA.clickPost(binding.num.text.toString().toInt())// 인자로 눌려진 데이터의 값이 들어가야한다.

        }


    }


}