package com.pagewisegroup.tiredtasks.ui.dashboard

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.pagewisegroup.tiredtasks.R

class CreateCardDialog : DialogFragment(){

    lateinit var conditionEditText: EditText
    lateinit var symptomEditText: EditText
    lateinit var notesEditText: EditText
    lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_card_dialog, container, false)
        saveButton = view.findViewById(R.id.save_button)

        val db = this.context?.let { CardDatabaseManager(it) }
        saveButton.setOnClickListener{
            var condition = conditionEditText.text.toString()
            var symptoms = symptomEditText.text.toString()
            var notes = notesEditText.text.toString()
            Log.d("condition", condition)
            if(condition != "") {
                db?.insert(condition, symptoms, notes)
            }
            this.dismiss()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conditionEditText = view.findViewById<EditText>(R.id.condition_input)
        symptomEditText = view.findViewById<EditText>(R.id.symptom_input)
        notesEditText = view.findViewById<EditText>(R.id.notes_input)

        saveButton.isEnabled = false
        conditionEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveButton.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        val fragment = this.targetFragment as DashboardFragment
        fragment.handleDialogClose(dialog)
        super.onDismiss(dialog)
    }
}