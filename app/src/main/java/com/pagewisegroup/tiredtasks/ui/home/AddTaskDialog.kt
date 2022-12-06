package com.pagewisegroup.tiredtasks.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.pagewisegroup.tiredtasks.R

class AddTaskDialog: DialogFragment(), AdapterView.OnItemSelectedListener {

    lateinit var taskEditText: EditText
    lateinit var prioritySpinner: Spinner
    lateinit var energySpinner: Spinner
    lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.add_task_dialog, container, false)
        saveButton = view.findViewById<Button>(R.id.save_task_button)
        val db = this.context?.let{ TodoDatabaseManager(it) }

        //gets user input data and saves it to db before closing dialog
        saveButton.setOnClickListener{
            var task = taskEditText.text.toString()
            var priority = prioritySpinner.selectedItem.toString()
            var energy = energySpinner.selectedItem.toString()
            Log.d("why", energy)
            if(task != ""){
                db?.insert(task, priority, energy)
            }
            this.dismiss()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskEditText = view.findViewById(R.id.task_name)
        prioritySpinner = view.findViewById(R.id.priority)
        energySpinner = view.findViewById(R.id.energy_level)
        saveButton = view.findViewById(R.id.save_task_button)

        //setting up spinners
        val levels = listOf("High", "Medium", "Low")
        val priorityDataAdapter = ArrayAdapter(view.context, R.layout.add_task_spinner_item, levels)
        val energyDataAdapter = ArrayAdapter(view.context, R.layout.add_task_spinner_item, levels)
        priorityDataAdapter.setDropDownViewResource(R.layout.add_task_spinner_item)
        energyDataAdapter.setDropDownViewResource(R.layout.add_task_spinner_item)
        prioritySpinner.adapter = priorityDataAdapter
        energySpinner.adapter = energyDataAdapter

        //disabling save button until user adds task
        saveButton.isEnabled = false
        taskEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveButton.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    //tells parent fragment to update recyclerview since dialog is closing
    override fun onDismiss(dialog: DialogInterface) {
        val fragment = this.targetFragment as HomeFragment
        fragment.handleDialogClose(dialog)
        super.onDismiss(dialog)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}