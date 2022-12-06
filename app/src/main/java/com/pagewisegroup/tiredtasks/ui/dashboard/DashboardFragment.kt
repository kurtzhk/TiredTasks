package com.pagewisegroup.tiredtasks.ui.dashboard

import android.content.DialogInterface
import android.graphics.drawable.Drawable.ConstantState
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.databinding.FragmentDashboardBinding

interface DialogCloseListener{
    fun handleDialogClose(sialogInterface: DialogInterface)
}

class DashboardFragment : Fragment(), DialogCloseListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var layoutManager: RecyclerView.LayoutManager? = null
    //private var cardAdapter: RecyclerView.Adapter<CardRecylcerAdapter.ViewHolder>? = null
    private lateinit var cardAdapter: CardRecylcerAdapter
    private lateinit var cardRecycler: RecyclerView
    private var entries = 0
    lateinit private var v: View

    lateinit var db: CardDatabaseManager
    var conditions = mutableListOf<String>()
    var symptoms = mutableListOf<String>()
    var notes = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.create_card).setOnClickListener{
            val createCardDialog = CreateCardDialog()
            createCardDialog.setTargetFragment(this, 0)
            createCardDialog.show(parentFragmentManager, "CreateCardDialog")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        cardRecycler = view.findViewById(R.id.card_recycler)

        val carddb = CardDatabaseManager(view.context)
        updateView(carddb)


        cardAdapter = CardRecylcerAdapter(conditions, symptoms, notes)

        cardRecycler?.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = cardAdapter
            Log.d("here2", "here2")
        }

        entries = cardRecycler?.adapter?.itemCount ?: 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateView(db: CardDatabaseManager){
        conditions = db.readAllConditions()
        symptoms = db.readAllSymptoms()
        notes = db.readAllNotes()
    }

    override fun handleDialogClose(sialogInterface: DialogInterface) {
        db = CardDatabaseManager(v.context)
        updateView(db)
        entries++
        cardRecycler.adapter = CardRecylcerAdapter(conditions, symptoms, notes)

    }
}