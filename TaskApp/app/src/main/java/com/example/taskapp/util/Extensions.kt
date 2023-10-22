package com.example.taskapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

    fun Fragment.showBottomSheet(
        titleDialog: Int? = null,
        titleButton: Int? = null,
        message: Int,
        onClick: () -> Unit = {}
    ) {
        val bottonShetDialog = BottomSheetDialog(requireContext(), )

        val bottomSheetBinding: BottomSheetBinding =
            BottomSheetBinding.inflate(layoutInflater,null,false)
    }
}