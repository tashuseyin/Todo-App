package com.example.todoapp.util

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.todoapp.R
import com.example.todoapp.model.entities.Priority


fun listener(context: Context): AdapterView.OnItemSelectedListener =
    object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                }

                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow
                        )
                    )
                }

                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }


fun verifyDataCheck(title: String, description: String): Boolean {
    return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
        false
    } else !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
}


fun parsePriority(priority: String): Priority {
    return when (priority) {
        "High Priority" -> {
            Priority.HIGH
        }
        "Medium Priority" -> {
            Priority.MEDIUM
        }
        "Low Priority" -> {
            Priority.LOW
        }
        else -> Priority.LOW
    }
}



