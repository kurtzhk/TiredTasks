package com.pagewisegroup.tiredtasks.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pagewisegroup.tiredtasks.R

class TodoRecyclerAdapter : RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>() {
    private val text = arrayOf("one", "two", "three")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemText: TextView

        init{
            itemText = itemView.findViewById(R.id.todo_text)

            //itemView.setOnClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemText.text = text[position]
    }

    override fun getItemCount(): Int {
        return text.size
    }
}