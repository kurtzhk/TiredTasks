package com.pagewisegroup.tiredtasks.ui.notifications

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pagewisegroup.tiredtasks.R
import com.pagewisegroup.tiredtasks.adapters.MessagesAdapter
import com.pagewisegroup.tiredtasks.databinding.FragmentNotificationsBinding
import com.pagewisegroup.tiredtasks.models.MedicationModel
import com.pagewisegroup.tiredtasks.models.MessagesModel
import com.pagewisegroup.tiredtasks.ui.medication.AddNewMedication
import com.pagewisegroup.tiredtasks.utils.DatabaseHandler

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //view list
    private lateinit var messagesRecycler: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var messagesList: ArrayList<MessagesModel>

    //use db handler group used
    private lateinit var db: DatabaseHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //view binding
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get context for db from associated activity
        db = DatabaseHandler(activity)
        db.openDatabase()

        //alert builder's string
        var m_Text = "";

        //bind add button
        val addButton = _binding!!.root.findViewById<FloatingActionButton>(R.id.templateAdd)

        addButton.setOnClickListener {
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle("New Message Template")

            //user input
            val input = EditText(activity)
            input.hint = "Enter Text"
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            //builder buttons
            builder.setPositiveButton("Enter", DialogInterface.OnClickListener { dialog, which ->

                // get user input
                m_Text = input.text.toString()

                if(m_Text != ""){
                    var temp = MessagesModel()

                    temp.message = m_Text
                    db.addTemplate(temp)

                    messagesList = db.getTemplates()
                    messagesAdapter.setList(messagesList)
                }
            })

            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        messagesRecycler = _binding!!.root.findViewById(R.id.messageCardView)
        messagesRecycler.layoutManager = LinearLayoutManager(activity)

        messagesAdapter = MessagesAdapter(db)
        messagesRecycler.adapter = messagesAdapter

        messagesList = db.getTemplates()
        messagesList.reverse()
        messagesAdapter.setList(messagesList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}