package com.example.taskapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentLoguinBinding
import com.example.taskapp.ui.BaseFragment
import com.example.taskapp.util.FirebaseHelper
import com.example.taskapp.util.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoguinFragment : BaseFragment() {
    private var _binding: FragmentLoguinBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoguinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.btnCriarconta.setOnClickListener {
            findNavController().navigate(R.id.action_loguinFragment_to_registerFragment)
        }

        binding.recuperarConta.setOnClickListener {
            findNavController().navigate(R.id.action_loguinFragment_to_recoverAccountFragment)
        }

        binding.btnLoguin.setOnClickListener {
            validadeData()
            // findNavController().navigate(R.id.action_global_homeFragment)
        }


    }

    fun validadeData() {
        var email = binding.editEmail.text.toString().trim()
        var password = binding.editPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {

                hideKeyboard()

                binding.progressBar.isVisible = true
                loginUser(email, password)

            } else {
                showBottomSheet(message = getString(R.string.password_empty_register_fragment))
            }
        } else {
            showBottomSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }

    private fun loginUser(email: String, password: String) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)

                } else {
                    binding.progressBar.isVisible = false

                    showBottomSheet(
                        message = getString(FirebaseHelper.validError(task.exception?.message.toString()))
                    )
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}