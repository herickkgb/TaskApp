package com.example.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentRecoverAccountBinding
import com.example.taskapp.util.FirebaseHelper
import com.example.taskapp.util.initToolbar
import com.example.taskapp.util.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initToolbar(binding.toolbar)

        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun recoverAccoutUser(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            binding.progressBar.isVisible = false
            if (task.isSuccessful) {
                showBottomSheet(
                    message = getString(R.string.text_message_recover_accout_fragment)
                )
            } else {
                showBottomSheet(
                    message = getString(FirebaseHelper.validError(task.exception?.message.toString()))
                )
            }
        }

    }

    fun initListener() {
        binding.btnEnviar.setOnClickListener {
            validadeData()
        }
    }

    fun validadeData() {
        var email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressBar.isVisible = true

            recoverAccoutUser(email)
            Toast.makeText(requireContext(), "Tudo certo", Toast.LENGTH_LONG).show()

        } else {
            showBottomSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }


}