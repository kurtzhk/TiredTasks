package com.pagewisegroup.tiredtasks.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pagewisegroup.tiredtasks.R
import org.w3c.dom.Text

class TodoRecyclerAdapter(task: MutableList<String>, prior: MutableList<String>, ener: MutableList<String>) : RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>() {

    var tasks: MutableList<String> = task
    var priorities: MutableList<String> = prior
    var energies: MutableList<String> = ener

    //grabs the fields from the recycler list item
    inner class ViewHolder(itemView: View, task: MutableList<String>, prior: MutableList<String>, ener: MutableList<String>) : RecyclerView.ViewHolder(itemView){
        var taskText: TextView
        var priorityImage: ImageView

        var tasks: MutableList<String>
        var priorities: MutableList<String>
        var energies: MutableList<String>

        val db: TodoDatabaseManager

        init{
            taskText = itemView.findViewById(R.id.todo_text)
            priorityImage = itemView.findViewById(R.id.todo_battery)

            tasks = task
            priorities = prior
            energies = ener
            db = TodoDatabaseManager(itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        return ViewHolder(v, tasks, priorities, energies)
    }

    //populates the fields retrieved in ViewHolder class with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tasks = holder.tasks
        priorities = holder.priorities
        energies = holder.energies

        holder.taskText.text = tasks[position]

        if(energies[position] == "Low"){
            holder.priorityImage.setImageResource(R.drawable.battery3)
        }else if(energies[position] == "High"){
            holder.priorityImage.setImageResource(R.drawable.battery)
        }

        if(priorities[position] == "Low"){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }else if(priorities[position] == "High"){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}