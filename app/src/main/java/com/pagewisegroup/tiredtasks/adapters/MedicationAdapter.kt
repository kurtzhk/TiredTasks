package com.pagewisegroup.tiredtasks.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.models.MedicationModel
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler

class MedicationAdapter(dbH: DatabaseHandler) : RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {

    var medicationModel: ArrayList<MedicationModel> = ArrayList<MedicationModel>()
    lateinit var db: DatabaseHandler

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val frequencyView: TextView
        val checkBoxView: CheckBox
        val deleteButton: MaterialButton


        init {
            nameView = view.findViewById(R.id.NameText)
            checkBoxView = view.findViewById(R.id.checkBox)
            frequencyView = view.findViewById(R.id.FrequencyText)
            deleteButton = view.findViewById(R.id.deleteButton)
        }
    }

    init {
        db = dbH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_item_layout, parent, false)

        return ViewHolder(view)
    }

    fun setMedicationList(newModel: ArrayList<MedicationModel>) {
        medicationModel = newModel
        notifyDataSetChanged()
    }

    fun deleteItem(pos: Int) {
        db.deleteMedication(medicationModel[pos].id)
        medicationModel.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        holder.deleteButton.setOnClickListener {
            Log.d("L", "delete?")
            deleteItem(position)
        }
        holder.nameView.text = medicationModel[position].dose + " " + medicationModel[position].name
        holder.frequencyView.text = medicationModel[position].frequency
        holder.checkBoxView.isChecked = medicationModel[position].status
        holder.checkBoxView.setOnCheckedChangeListener()
        { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                db.updateStatus(medicationModel[position].id, true)
            } else {
                db.updateStatus(medicationModel[position].id, false)
            }
        }
    }

    /* fun editItem(pos: Int) {
        var item = medicationModel[pos]
        var bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("name", item.name)
        bundle.putString("dose", item.dose)
        bundle.putString("frequency", item.frequency)
    } */

    override fun getItemCount(): Int {
        return medicationModel.size
    }
}