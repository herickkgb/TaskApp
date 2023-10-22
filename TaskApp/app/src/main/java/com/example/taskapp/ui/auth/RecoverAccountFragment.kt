package com.example.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskapp.databinding.FragmentRecoverAccountBinding
import com.example.taskapp.util.initToolbar


class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initListener(){
        binding.btnEnviar.setOnClickListener {
            validadeData()
        }
    }

    fun validadeData() {
        var email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {

                Toast.makeText(requireContext(), "Tudo certo", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(requireContext(), "Preencha um e-mail válido", Toast.LENGTH_LONG).show()
        }
    }

}