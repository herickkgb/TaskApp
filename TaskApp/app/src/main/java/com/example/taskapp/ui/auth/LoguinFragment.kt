package com.example.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentLoguinBinding
import com.example.taskapp.util.showBottomSheet


class LoguinFragment : Fragment() {
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
                findNavController().navigate(R.id.action_global_homeFragment)
            } else {
                showBottomSheet(message = getString(R.string.preencha_sua_senha))
            }
        } else {
            showBottomSheet(message = getString(R.string.preencha_seu_email))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}