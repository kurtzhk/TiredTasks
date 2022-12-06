package com.pagewisegroup.tiredtasks.ui.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.pagewisegroup.tiredtasks.ExpandCardActivity
import com.pagewisegroup.tiredtasks.R

class CardRecylcerAdapter(cond: MutableList<String>, symp: MutableList<String>, not: MutableList<String>): RecyclerView.Adapter<CardRecylcerAdapter.ViewHolder>() {
    var conditions: MutableList<String> = cond
    var symptoms: MutableList<String> = symp
    var notes: MutableList<String> = not

    inner class ViewHolder(itemView: View, cond: MutableList<String>, symp: MutableList<String>, not: MutableList<String>): RecyclerView.ViewHolder(itemView){
        var conditionText: EditText
        var symptomText: EditText
        var notesText: EditText
        var editImage: ImageView
        var fullscreenImage: ImageView
        var favImage: ImageView

        var conditions: MutableList<String>
        var symptoms: MutableList<String>
        var notes: MutableList<String>

        val db: CardDatabaseManager

        init{
            conditionText = itemView.findViewById(R.id.condition)
            symptomText = itemView.findViewById(R.id.symptoms)
            notesText = itemView.findViewById(R.id.notes)
            editImage = itemView.findViewById(R.id.edit_icon)
            fullscreenImage = itemView.findViewById(R.id.fullscreen_icon)
            favImage = itemView.findViewById(R.id.fav_icon)

            conditionText.isEnabled = false
            symptomText.isEnabled = false
            notesText.isEnabled = false

            conditions = cond
            symptoms = symp
            notes = not

            db = CardDatabaseManager(itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.condition_list_item, parent, false)
        return ViewHolder(v, conditions, symptoms, notes)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        conditions = holder.conditions
        symptoms = holder.symptoms
        notes = holder.notes

        holder.conditionText.setText(conditions[position])
        holder.symptomText.setText(symptoms[position])
        holder.notesText.setText(notes[position])

        holder.editImage.setOnClickListener{
            if(holder.conditionText.isEnabled){
                holder.editImage.setImageResource(R.drawable.edit_icon)
                holder.conditionText.isEnabled = false
                holder.symptomText.isEnabled = false
                holder.notesText.isEnabled = false
            }else{
                holder.editImage.setImageResource(R.drawable.check_mark)
                holder.conditionText.isEnabled = true
                holder.symptomText.isEnabled = true
                holder.notesText.isEnabled = true
            }
        }

        holder.fullscreenImage.setOnClickListener{
            Intent(holder.itemView.context, ExpandCardActivity::class.java).also{
                it.putExtra("condition", holder.conditions[position])
                it.putExtra("symptoms", holder.symptoms[position])
                it.putExtra("notes", holder.notes[position])
                startActivity(holder.itemView.context, it, null)
            }
        }

        holder.favImage.setOnClickListener{
            if(holder.db.containsCard(holder.conditionText.text.toString())){
                holder.favImage.setImageResource(R.drawable.white_heart)
                holder.db.removeCard(holder.conditionText.text.toString())
                holder.conditions.removeAt(position)
                holder.symptoms.removeAt(position)
                holder.notes.removeAt(position)
                this.notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return conditions.size
    }

    fun setData(cond: MutableList<String>, symp: MutableList<String>, not: MutableList<String>){
        conditions = cond
        symptoms = symp
        notes = not
    }
}