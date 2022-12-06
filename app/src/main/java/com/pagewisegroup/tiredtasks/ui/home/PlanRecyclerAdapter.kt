package com.pagewisegroup.tiredtasks.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pagewisegroup.tiredtasks.R

class PlanRecyclerAdapter: RecyclerView.Adapter<PlanRecyclerAdapter.ViewHolder>() {

    //test object array
    val text = mutableListOf<String>("30 min Exercise", "CS Homework", "Read")

    //grabs the fields from the recycler list item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemText: RadioButton
        var batteryImage: ImageView

        init{
            itemText = itemView.findViewById(R.id.radio_button)
            batteryImage = itemView.findViewById(R.id.plan_battery)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.plan_list_item, parent, false)
        return ViewHolder(v)
    }

    //populates the fields retrieved in ViewHolder class with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemText.text = text[position]
        if(position == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }else if(position == 1){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            holder.batteryImage.setImageResource(R.drawable.battery2)
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            holder.batteryImage.setImageResource(R.drawable.battery3)
        }

        holder.itemText.setOnClickListener{
            if(holder.itemText.isChecked){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            }
        }
    }

    override fun getItemCount(): Int {
        return text.size
    }
}