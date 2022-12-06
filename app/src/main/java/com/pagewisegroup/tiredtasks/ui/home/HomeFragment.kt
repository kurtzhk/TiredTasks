package com.pagewisegroup.tiredtasks.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.databinding.FragmentHomeBinding
import com.pagewisegroup.tiredtasks.ui.medication.MedicationActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var todoAdapter: RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>? = null
    private var planAdapter: RecyclerView.Adapter<PlanRecyclerAdapter.ViewHolder>? = null

    //manages the long press on the plan recycler item
    private val planItemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

                //for when the user moves the recycler item
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = recyclerView.adapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter?.notifyItemMoved(from, to)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    TODO("Not yet implemented")
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    //manages the long press on the todo recycler item
    private val todoItemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

                //for when the user moves the recycler item
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = recyclerView.adapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter?.notifyItemMoved(from, to)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    TODO("Not yet implemented")
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val todoRecycler = view.findViewById<RecyclerView>(R.id.todo_recycler)
        val planRecycler = view.findViewById<RecyclerView>(R.id.plan_recycler)
        //val medicationButton = view.findViewById<MaterialButton>(R.id.medication_button)

        /*medicationButton.setOnClickListener {
            val intent = Intent(activity, MedicationActivity::class.java)
            startActivity(intent)
            //Log.v("HomeFragment","onClick works")
            //MedicationFragment nextFrag = new MedicationFragment();
        }*/

        //applying managers and adapters to the recyclerview
        todoRecycler.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = TodoRecyclerAdapter()
            planItemTouchHelper.attachToRecyclerView(this)
        }

        //applying managers and adapters to the recyclerview
        planRecycler.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = PlanRecyclerAdapter()
            todoItemTouchHelper.attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}