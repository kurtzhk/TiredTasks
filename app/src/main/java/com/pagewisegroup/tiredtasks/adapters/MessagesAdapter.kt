package com.pagewisegroup.tiredtasks.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.models.MessagesModel
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler

class MessagesAdapter(dbH: DatabaseHandler) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    var messagesModel: ArrayList<MessagesModel> = ArrayList()
    var db: DatabaseHandler

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messagesText: TextView
        val checkBox: CheckBox
        val deleteButton: MaterialButton

        init {
            messagesText = view.findViewById(R.id.messageText)
            checkBox = view.findViewById(R.id.checkBox)
            deleteButton = view.findViewById(R.id.deleteButton)
        }
    }

    init {
        db = dbH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_card, parent, false)

        return ViewHolder(view)
    }

    fun setList(newModel: ArrayList<MessagesModel>) {
        messagesModel = newModel
        notifyDataSetChanged()
    }

    private fun deleteItem(pos: Int) {
        db.deleteTemplate(messagesModel[pos].id)
        messagesModel.removeAt(pos)
        notifyItemRemoved(pos)

        //Toast.makeText(this, "Template deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()

        holder.checkBox.isChecked = messagesModel[position].checked
        holder.messagesText.text = messagesModel[position].message

        holder.deleteButton.setOnClickListener {
            deleteItem(position)
        }
    }

    override fun getItemCount(): Int {
        return messagesModel.size
    }
}