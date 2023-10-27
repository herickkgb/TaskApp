package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentDoneBinding
import com.example.taskapp.databinding.FragmentFormTaskBinding
import com.example.taskapp.util.initToolbar
import com.example.taskapp.util.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newtask: Boolean = true
    private var status: Status = Status.TODO

    private val args: FormTaskFragmentArgs by navArgs()

    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        reference = Firebase.database.reference
        auth = Firebase.auth

        getArgs()
        initListener()
    }

    private fun getArgs() {
        args.task.let { it ->
            if (it != null) {
                this.task = it

                configTask()
            }

        }
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener { validadeData() }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            status = when (id) {
                R.id.rd_Todo -> Status.TODO
                R.id.rd_Doing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun configTask() {
        newtask = false
        status = task.status
        binding.textToolbar.text = getString(R.string.text_toolbar_update_form_task)


        binding.editDescription.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        binding.radioGroup.check(
            when (task.status) {
                Status.TODO -> R.id.rd_Todo
                Status.DONE -> R.id.rd_Done
                else -> R.id.rd_Doing
            }
        )
    }

    fun validadeData() {
        var description = binding.editDescription.text.toString().trim()

        if (description.isNotEmpty()) {
            binding.progressBar.isVisible = true

            if (newtask) {
                task = Task()
                task.id = reference.database.reference.push().key ?: ""
            }
            task.description = description
            task.status = status

            saveTask()
        } else {
            showBottomSheet(message = getString(R.string.text_description_form_task_fragment))
        }

    }

    private fun saveTask() {
        reference.child("tasks").child(auth.currentUser?.uid ?: "").child(task.id).setValue(task)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_save_sucess_form_task_fragment,
                        Toast.LENGTH_SHORT
                    ).show()

                    if (newtask) {
                        findNavController().popBackStack()
                    } else {
                        viewModel.setUpdateTask(task)

                        binding.progressBar.isVisible = false
                    }

                } else {
                    showBottomSheet(message = getString(R.string.erro_generic))
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}