package com.example.taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentRegisterBinding
import com.example.taskapp.util.initToolbar
import com.example.taskapp.util.showBottomSheet


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()

    }

    fun initListener() {
        binding.btnRegister.setOnClickListener {
            validadeData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun validadeData() {
        var email = binding.editEmail.text.toString().trim()
        var password = binding.editPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                showBottomSheet(message = getString(R.string.tudo_certo))

            } else {
                showBottomSheet(message = getString(R.string.Register_fragment_preencha_uma_senha))
            }
        } else {
            showBottomSheet(message = getString(R.string.register_fragment_preencha_um_e_mail_v_lido))
        }
    }

}