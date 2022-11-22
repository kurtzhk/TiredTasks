package com.pagewisegroup.tiredtasks.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.databinding.FragmentHomeBinding
import com.pagewisegroup.tiredtasks.ui.dashboard.MedicationFragment
import com.pagewisegroup.tiredtasks.ui.medication.MedicationActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val todoRecycler = view.findViewById<RecyclerView>(R.id.todo_recycler)
        val planRecycler = view.findViewById<RecyclerView>(R.id.plan_recycler)
        val medicationButton = view.findViewById<MaterialButton>(R.id.medication_button)

        medicationButton.setOnClickListener {
            val intent = Intent(activity, MedicationActivity::class.java)
            startActivity(intent)
            //Log.v("HomeFragment","onClick works")
            //MedicationFragment nextFrag = new MedicationFragment();
        }

        todoRecycler.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = TodoRecyclerAdapter()
        }

        planRecycler.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = PlanRecyclerAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}