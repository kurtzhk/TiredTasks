package com.pagewisegroup.tiredtasks.ui.medication

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.adapters.MedicationAdapter
import com.pagewisegroup.tiredtasks.databinding.ActivityMedicationBinding
import com.pagewisegroup.tiredtasks.models.MedicationModel
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler
import kotlin.collections.ArrayList

class MedicationFragment : Fragment(), DialogCloseListener {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMedicationBinding
    private lateinit var medicationRecyclerView: RecyclerView
    private lateinit var medicationAdapter: MedicationAdapter

    private lateinit var medicationList: ArrayList<MedicationModel>
    private lateinit var db: DatabaseHandler


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var retView = inflater.inflate(R.layout.activity_medication, container, false)
        super.onCreate(savedInstanceState)

        db = DatabaseHandler(activity)
        db.openDatabase()

        binding = ActivityMedicationBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        medicationRecyclerView = retView.findViewById(R.id.medicationRecyclerView)
        medicationRecyclerView.layoutManager = LinearLayoutManager(activity)

        medicationAdapter = MedicationAdapter(db)
        medicationRecyclerView.adapter = medicationAdapter

        medicationList = db.getAllMedications()
        medicationList.reverse()
        medicationAdapter.setMedicationList(medicationList)

        retView.findViewById<FloatingActionButton>(R.id.floatAction)?.setOnClickListener {
            Log.d("DEBUG", "Click")
            activity?.let { it1 -> AddNewMedication().show(it1.supportFragmentManager, "ActionBottomDialog") }
        }

        return retView
    }

    override fun handleDialogClose(dialogInterface: DialogInterface) {
        medicationList = db.getAllMedications()
        medicationList.reverse()
        medicationAdapter.setMedicationList((medicationList))
        medicationAdapter.notifyDataSetChanged()
    }
}