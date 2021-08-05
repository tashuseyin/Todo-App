package com.example.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.RowLayoutBinding
import com.example.todoapp.model.entities.TodoData

class ListAdapter(private val onItemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<ListViewHolder>() {


    var dataList = emptyList<TodoData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataList[position], onItemClickListener)
    }

    override fun getItemCount() = dataList.size


    fun setData(todoData: List<TodoData>) {
        dataList = todoData
        notifyDataSetChanged()
    }
}
