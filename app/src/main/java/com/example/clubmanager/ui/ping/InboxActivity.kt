package com.example.clubmanager.ui.ping

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.clubmanager.R
import com.example.clubmanager.data.models.TargetType
import com.example.clubmanager.data.repo.ServiceLocator
import java.text.SimpleDateFormat
import java.util.*

class InboxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        val list = findViewById<ListView>(R.id.listPings)
        val df = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val rows = ServiceLocator.repo.pings.map { p ->
            val to = when (p.toType) {
                TargetType.ALL -> "All"
                TargetType.ROLE -> "Role: ${p.toIds.joinToString()}"
                TargetType.STAFF -> "Staff: ${p.toIds.joinToString()}"
            }
            "[${p.urgency}] ${p.message}\n→ $to · ${df.format(Date(p.createdAt))}"
        }
        list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rows)
    }
}
