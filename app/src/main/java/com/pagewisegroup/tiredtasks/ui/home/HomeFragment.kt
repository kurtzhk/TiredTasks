package com.pagewisegroup.tiredtasks.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.databinding.FragmentHomeBinding
import com.pagewisegroup.tiredtasks.ui.dashboard.CardDatabaseManager
import com.pagewisegroup.tiredtasks.ui.medication.DialogCloseListener
import com.pagewisegroup.tiredtasks.ui.medication.MedicationActivity

class HomeFragment : Fragment(), DialogCloseListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var todoAdapter: TodoRecyclerAdapter
    private lateinit var todoRecycler: RecyclerView
    private var entries = 0
    private lateinit var v: View
    lateinit var db: TodoDatabaseManager
    var tasks = mutableListOf<String>()
    var priorities = mutableListOf<String>()
    var energies = mutableListOf<String>()

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
                    TODO("implement")
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    //manages the long press on the todo recycler item
    private val todoItemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, LEFT) {

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
                    var position = viewHolder.adapterPosition
                    var item = todoAdapter.getData().get(position)

                    val helperDB = TodoDatabaseManager(v.context)
                    todoAdapter.removeItem(position)
                    helperDB.removeTask(item)
                    todoAdapter.notifyItemRemoved(position)
                    todoAdapter.notifyDataSetChanged()
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //add task button that opens dialog
        root.findViewById<Button>(R.id.add_task_button).setOnClickListener{
            val addTaskDialog = AddTaskDialog()
            addTaskDialog.setTargetFragment(this, 0)
            addTaskDialog.show(parentFragmentManager, "AddTaskDialog")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val planRecycler = view.findViewById<RecyclerView>(R.id.plan_recycler)
        todoRecycler = view.findViewById(R.id.todo_recycler)
        v = view
        val tododb = TodoDatabaseManager(view.context)

        //reading all the info from the db to display in recyclerview
        updateView(tododb)
        todoAdapter = TodoRecyclerAdapter(tasks, priorities, energies)
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
            adapter = todoAdapter
            todoItemTouchHelper.attachToRecyclerView(this)
        }

        //applying managers and adapters to the recyclerview
        planRecycler.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = PlanRecyclerAdapter()
            planItemTouchHelper.attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //reads data from database
    private fun updateView(db: TodoDatabaseManager){
        tasks = db.readAllTasks()
        priorities = db.readAllPriorities()
        energies = db.readAllEnergy()
    }

    //reads new user entry from database and updates recyclerview
    override fun handleDialogClose(dialogInterface: DialogInterface) {
        db = TodoDatabaseManager(v.context)
        updateView(db)
        entries++
        todoRecycler.adapter = TodoRecyclerAdapter(tasks, priorities, energies)
    }
}