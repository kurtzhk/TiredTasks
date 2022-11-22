package com.pagewisegroup.tiredtasks.ui.medication

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.models.MedicationModel
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler

class AddNewMedication : BottomSheetDialogFragment() {
    var TAG = "ActionBottomDialog"

    lateinit var newMedDose: EditText
    lateinit var newMedName: EditText
    lateinit var newSubmitButton: Button
    lateinit var frequencyRadio: RadioGroup
    lateinit var db: DatabaseHandler
    // var newMedFreq:

    fun newInstance(): AddNewMedication {
        return AddNewMedication()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TiredTasks)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.new_medication, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newMedDose = getView()?.findViewById<EditText>(R.id.DoseInput)!!
        newMedName = getView()?.findViewById<EditText>(R.id.MedicationInput)!!
        newSubmitButton = getView()?.findViewById<Button>(R.id.addMediButton)!!
        frequencyRadio = getView()?.findViewById<RadioGroup>(R.id.FrequencyRadio)!!

        db = DatabaseHandler(activity)
        db.openDatabase()

        var isUpdate = false
        var bundle = arguments
        /*if (bundle != null) {
            isUpdate = true
            var medication = bundle.getString("")
        }*/
        newMedDose.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newSubmitButton.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString() == "") {
                    newSubmitButton.isEnabled = false
                    newSubmitButton.setTextColor(Color.GRAY)
                } else {
                    //TODO("make sure styles align with built in styles")
                    newSubmitButton.isEnabled = true
                    newSubmitButton.setTextColor(Color.BLACK)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        newMedName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString() == "") {
                    newSubmitButton.isEnabled = false
                    newSubmitButton.setTextColor(Color.GRAY)
                } else {
                    //TODO("make sure styles align with built in styles")
                    newSubmitButton.isEnabled = true
                    newSubmitButton.setTextColor(Color.BLACK)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })



        newSubmitButton.setOnClickListener {
            var dose = newMedDose.text.toString()
            var name = newMedName.text.toString()
            var freq =
                getView()?.findViewById<RadioButton>(frequencyRadio.checkedRadioButtonId)?.text.toString()
            var med = MedicationModel()

            med.status = false
            med.name = name
            med.dose = dose
            med.frequency = freq
            db.insertMedication(med)
            dismiss()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        if(activity is DialogCloseListener){
            (activity as DialogCloseListener).handleDialogClose(dialog)
        }
        super.onDismiss(dialog)

    }
}