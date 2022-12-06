package com.pagewisegroup.tiredtasks.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.DialogFragment
import com.pagewisegroup.tiredtasks.R

interface CreateCardDialogListener{
    fun onFinishEditDialog(inputText: String)
}

class CreateCardDialog : DialogFragment(), OnEditorActionListener {

    private var conditionEditText: EditText? = null
    private var symptomEditText: EditText? = null
    private var notesEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_card_dialog, container, false)
        //val saveButton = view.findViewById<Button>(R.id.save_button)


        /*saveButton.setOnClickListener{
            this.dismiss()
        }*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conditionEditText = view.findViewById<EditText>(R.id.condition_input)
        symptomEditText = view.findViewById<EditText>(R.id.symptom_input)
        notesEditText = view.findViewById<EditText>(R.id.notes_input)
        conditionEditText?.setOnEditorActionListener(this)
        symptomEditText?.setOnEditorActionListener(this)
        notesEditText?.setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(EditorInfo.IME_ACTION_DONE == actionId){
            val editListener = this.activity as CreateCardDialogListener
            editListener.onFinishEditDialog(conditionEditText?.text.toString())
            editListener.onFinishEditDialog(symptomEditText?.text.toString())
            editListener.onFinishEditDialog(notesEditText?.text.toString())
            sendBackResult()
            dismiss()
            return true
        }
        return false
    }

    fun sendBackResult(){
        val listener = this.targetFragment as CreateCardDialogListener
        Log.d(conditionEditText?.text.toString(), "result")
        listener.onFinishEditDialog(conditionEditText?.text.toString())
        listener.onFinishEditDialog((symptomEditText?.text.toString()))
        listener.onFinishEditDialog(notesEditText?.text.toString())
        dismiss()
    }
}