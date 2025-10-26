package com.example.clubmanager.ui.ping

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clubmanager.R
import com.example.clubmanager.data.models.Ping
import com.example.clubmanager.data.models.TargetType
import com.example.clubmanager.data.models.Urgency
import com.example.clubmanager.data.repo.ServiceLocator
import java.util.UUID
import android.content.DialogInterface


class ComposePingActivity : AppCompatActivity() {

    private lateinit var rgTarget: RadioGroup
    private lateinit var rgUrgency: RadioGroup
    private lateinit var rbAll: RadioButton
    private lateinit var rbRole: RadioButton
    private lateinit var rbStaff: RadioButton
    private lateinit var rbLow: RadioButton
    private lateinit var rbNormal: RadioButton
    private lateinit var rbHigh: RadioButton
    private lateinit var etRole: EditText
    private lateinit var etMessage: EditText
    private lateinit var tvStaffLabel: TextView
    private lateinit var btnPickStaff: Button
    private lateinit var btnSend: Button
    private lateinit var btnOpenInbox: Button

    private val selectedStaffIds = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_ping)

        rgTarget = findViewById(R.id.rgTarget)
        rgUrgency = findViewById(R.id.rgUrgency)
        rbAll = findViewById(R.id.rbAll)
        rbRole = findViewById(R.id.rbRole)
        rbStaff = findViewById(R.id.rbStaff)
        rbLow = findViewById(R.id.rbLow)
        rbNormal = findViewById(R.id.rbNormal)
        rbHigh = findViewById(R.id.rbHigh)
        etRole = findViewById(R.id.etRole)
        etMessage = findViewById(R.id.etMessage)
        tvStaffLabel = findViewById(R.id.tvStaffLabel)
        btnPickStaff = findViewById(R.id.btnPickStaff)
        btnSend = findViewById(R.id.btnSend)
        btnOpenInbox = findViewById(R.id.btnOpenInbox)

        rgTarget.setOnCheckedChangeListener { _, _ -> updateVisibility() }
        updateVisibility()

        btnPickStaff.setOnClickListener { showStaffPicker() }
        btnSend.setOnClickListener { onSend() }
        btnOpenInbox.setOnClickListener {
            startActivity(Intent(this, InboxActivity::class.java))
        }
    }

    private fun updateVisibility() {
        val byRole = rbRole.isChecked
        val byStaff = rbStaff.isChecked
        etRole.visibility = if (byRole) View.VISIBLE else View.GONE
        tvStaffLabel.visibility = if (byStaff) View.VISIBLE else View.GONE
        btnPickStaff.visibility = if (byStaff) View.VISIBLE else View.GONE
    }

    private fun showStaffPicker() {
        val staff = ServiceLocator.repo.staff
        val names: Array<CharSequence> =
            staff.map { "${it.name} (${it.role})" }.toTypedArray()
        val checked: BooleanArray =
            staff.map { selectedStaffIds.contains(it.id) }.toBooleanArray()

        AlertDialog.Builder(this)
            .setTitle("Select staff")
            .setMultiChoiceItems(names, checked) { _: DialogInterface, which: Int, isChecked: Boolean ->
                val id = ServiceLocator.repo.staff[which].id
                if (isChecked) {
                    if (!selectedStaffIds.contains(id)) selectedStaffIds.add(id)
                } else {
                    selectedStaffIds.remove(id)
                }
            }
            .setPositiveButton("Done") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .show()


    }

    private fun onSend() {
        val target = when {
            rbRole.isChecked -> TargetType.ROLE
            rbStaff.isChecked -> TargetType.STAFF
            else -> TargetType.ALL
        }

        val urgency = when {
            rbHigh.isChecked -> Urgency.HIGH
            rbLow.isChecked -> Urgency.LOW
            else -> Urgency.NORMAL
        }

        val msg = etMessage.text?.toString()?.trim().orEmpty()
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "Message required", Toast.LENGTH_SHORT).show()
            return
        }

        val toIds = when (target) {
            TargetType.ALL -> emptyList()
            TargetType.ROLE -> {
                val role = etRole.text?.toString()?.trim().orEmpty()
                if (role.isEmpty()) {
                    Toast.makeText(this, "Enter a role", Toast.LENGTH_SHORT).show()
                    return
                }
                listOf(role)
            }
            TargetType.STAFF -> {
                if (selectedStaffIds.isEmpty()) {
                    Toast.makeText(this, "Pick at least one staff", Toast.LENGTH_SHORT).show()
                    return
                }
                selectedStaffIds.toList()
            }
        }

        val ping = Ping(
            id = UUID.randomUUID().toString(),
            toType = target,
            toIds = toIds,
            message = msg,
            urgency = urgency
        )
        ServiceLocator.repo.addPing(ping)
        Toast.makeText(this, "Ping sent!", Toast.LENGTH_SHORT).show()

        etMessage.setText("")
        selectedStaffIds.clear()
    }
}
