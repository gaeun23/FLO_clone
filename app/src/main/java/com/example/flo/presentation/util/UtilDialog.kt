package com.example.flo.presentation.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flo.R
import com.example.flo.databinding.DialogOrderSetBinding

class UtilDialog : DialogFragment() {
    private var _binding: DialogOrderSetBinding? = null
    val binding get() = _binding ?: error("error")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogOrderSetBinding.inflate(requireActivity().layoutInflater)
        setCloseBtnClickListener()
        setStyle(STYLE_NORMAL, R.style.CustomFullDialog)
        return activity?.let {
            val dialog = AlertDialog.Builder(it).create()
            dialog.setView(binding.root)
            dialog
        } ?: throw IllegalStateException()
    }

    override fun onStart() {
        super.onStart()
        setLayout()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun setCloseBtnClickListener() {
        binding.dialogBtnCloseIb.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
