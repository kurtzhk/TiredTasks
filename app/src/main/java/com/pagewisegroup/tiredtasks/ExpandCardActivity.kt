package com.pagewisegroup.tiredtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ExpandCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_card)

        val minimizeImage = findViewById<ImageView>(R.id.minimize_icon)
        minimizeImage.setOnClickListener{
            finish()
        }

        val conditionText = findViewById<TextView>(R.id.condition_text)
        conditionText.text = intent.getStringExtra("condition")

        val symptomsText = findViewById<TextView>(R.id.symptom_text)
        symptomsText.text = intent.getStringExtra("symptoms")

        val notesText = findViewById<TextView>(R.id.note_text)
        notesText.text = intent.getStringExtra("notes")
    }
}