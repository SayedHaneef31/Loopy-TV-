package com.example.loopytvlauncher

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import android.view.inputmethod.EditorInfo

class PinDialogFragment(private val onCorrectPin: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_pin, container, false)

        val pinEditText = view.findViewById<EditText>(R.id.pin_input)
        val submitButton = view.findViewById<Button>(R.id.btn_submit)

        // Request focus for PIN input
        pinEditText.post {
            pinEditText.requestFocus()
        }

        // Handle back button press in the dialog
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                // Prevent dialog from being dismissed by back button
                true
            } else {
                false
            }
        }

        submitButton.setOnClickListener {
            val enteredPin = pinEditText.text.toString()
            if (enteredPin == "1234") { // Replace with your desired PIN
                onCorrectPin()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Incorrect PIN! Please try again.", Toast.LENGTH_SHORT).show()
                pinEditText.text.clear()
                pinEditText.requestFocus()
            }
        }

        // Also handle Enter key press
        pinEditText.setOnEditorActionListener { _, actionId, event ->
            if (event?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                submitButton.performClick()
                true
            } else {
                false
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // Reset the flag when the dialog is dismissed
        if (activity is KidsLauncherActivity) {
            (activity as KidsLauncherActivity).resetPinDialogShowing()
        }
    }
}
