package com.example.clubmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clubmanager.R
import com.example.clubmanager.ui.ping.ComposePingActivity
import com.example.clubmanager.ui.ping.InboxActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up navigation buttons
        val btnCompose = findViewById<Button>(R.id.btnCompose)
        val btnInbox = findViewById<Button>(R.id.btnInbox)

        btnCompose.setOnClickListener {
            val intent = Intent(this, ComposePingActivity::class.java)
            startActivity(intent)
        }

        btnInbox.setOnClickListener {
            val intent = Intent(this, InboxActivity::class.java)
            startActivity(intent)
        }
    }
}
