package com.example.termproject

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.databinding.FolderItemBinding
import com.example.termproject.databinding.PostItemBinding

class PostViewHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root)

class PostAdapter (main: PostActivity,
        val datas: ArrayList<Array<String>>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount(): Int = datas?.size ?:0
    val postA = main


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = PostViewHolder(
        PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        //항목 뷰를 가지는 뷰 홀더를 준비하기 위해 자동 호출
        val binding = (holder as PostViewHolder).binding

        val num = datas!![position][0].toInt()

        binding.imageWrite.setImageURI(Uri.parse(datas!![position][1]))

        binding.txt.text = datas[position][2]

        binding.itemRoot.setOnClickListener {

            postA.clickPost(num)// 인자로 눌려진 데이터의 값이 들어가야한다.

        }


    }


}