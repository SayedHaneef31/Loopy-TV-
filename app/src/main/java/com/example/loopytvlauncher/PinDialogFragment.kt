package com.example.loopytvlauncher

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class PinDialogFragment(private val onCorrectPin: () -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_pin, container, false)

        val pinEditText = view.findViewById<EditText>(R.id.pin_input)
        val submitButton = view.findViewById<Button>(R.id.btn_submit)

        submitButton.setOnClickListener {
            if (pinEditText.text.toString() == "1234") { // Replace with your desired PIN
                onCorrectPin()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Incorrect PIN!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
