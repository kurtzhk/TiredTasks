package com.pagewisegroup.tiredtasks.ui.medication

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.adapters.MedicationAdapter
import com.pagewisegroup.tiredtasks.databinding.ActivityMedicationBinding
import com.pagewisegroup.tiredtasks.models.MedicationModel
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler
import kotlin.collections.ArrayList

class MedicationActivity : AppCompatActivity(), DialogCloseListener {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMedicationBinding
    private lateinit var medicationRecyclerView: RecyclerView
    private lateinit var medicationAdapter: MedicationAdapter

    private lateinit var medicationList: ArrayList<MedicationModel>
    private lateinit var db: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = DatabaseHandler(this)
        db.openDatabase()

        binding = ActivityMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicationRecyclerView = findViewById(R.id.medicationRecyclerView)
        medicationRecyclerView.layoutManager = LinearLayoutManager(this)

        medicationAdapter = MedicationAdapter(db)
        medicationRecyclerView.adapter = medicationAdapter

        var fab = findViewById<FloatingActionButton>(R.id.floatAction)

        medicationList = db.getAllMedications()
        medicationList.reverse()
        medicationAdapter.setMedicationList(medicationList)

        fab.setOnClickListener {
            AddNewMedication().show(supportFragmentManager, "ActionBottomDialog")
        }
    }

    override fun handleDialogClose(dialogInterface: DialogInterface) {
        medicationList = db.getAllMedications()
        medicationList.reverse()
        medicationAdapter.setMedicationList((medicationList))
        medicationAdapter.notifyDataSetChanged()
    }
}