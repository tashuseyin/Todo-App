package com.example.todoapp.view.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.RowLayoutBinding
import com.example.todoapp.model.entities.Priority
import com.example.todoapp.model.entities.TodoData

class ListViewHolder(private val binding: RowLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemView: TodoData, onItemClickListener: (Int) -> Unit) {
        binding.titleText.text = itemView.title
        binding.descriptionText.text = itemView.description

        when (itemView.priority) {
            Priority.HIGH -> {
                binding.priorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.priorityIndicator.context,
                        R.color.red
                    )
                )
            }

            Priority.MEDIUM -> {
                binding.priorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.priorityIndicator.context,
                        R.color.yellow
                    )
                )
            }
            Priority.LOW -> {
                binding.priorityIndicator.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.priorityIndicator.context,
                        R.color.green
                    )
                )
            }
        }


        binding.rowBackground.setOnClickListener {
            onItemClickListener(adapterPosition)
        }


    }
}