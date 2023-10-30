package com.example.taskapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentDoingBinding
import com.example.taskapp.databinding.FragmentHomeBinding
import com.example.taskapp.ui.adapter.TaskAdapter
import com.example.taskapp.util.FirebaseHelper
import com.example.taskapp.util.showBottomSheet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        initRecyclerView()

        getTask()
    }

    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        with(binding.rvTask) {
            LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }


    }

    private fun initListener() {

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if (updateTask.status == Status.DOING) {
                //armazena a lista atual doadapter
                val oldList = taskAdapter.currentList

                //gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                val newList = oldList.toMutableList().apply {
                    find { it.id == updateTask.id }?.description == updateTask.description
                }

                //armazena a posição da tarefa a ser atualizada na lista
                val position = newList.indexOfFirst { it.id == updateTask.id }

                //envia a lista atualizada para o adapter
                taskAdapter.submitList(newList)

                //atualiza a tarefa pela posição do adapter
                taskAdapter.notifyItemChanged(position)
            }
        }
    }


    private fun optionSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(titleDialog = R.string.text_title_dialog_delete,
                    message = getString(R.string.text_message_dialog_delete),
                    titleButton = R.string.text_button_dialog_confirm_logout,
                    onClick = {
                        deleteTask(task)
                    })
            }

            TaskAdapter.SELECT_NEXT -> {
                task.status = Status.DONE
                updateTask(task)

                Toast.makeText(requireContext(), "Next ${task.description}", Toast.LENGTH_LONG)
                    .show()
            }

            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), task.description, Toast.LENGTH_LONG)
                    .show()
            }

            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECT_BACK -> {
                task.status = Status.TODO
                updateTask(task)

                Toast.makeText(requireContext(), "Voltando ${task.description}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun deleteTask(task: Task) {
        FirebaseHelper.getDatabase().child("tasks").child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id).removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(requireContext(), R.string.text_delete_task, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), R.string.erro_generic, Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

    private fun updateTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.text_update_form_task,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), R.string.erro_generic, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun getTask() {
        FirebaseHelper.getDatabase().child("tasks").child(FirebaseHelper.getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    for (ds in snapshot.children) {
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.DOING) {
                            taskList.add(task)
                        }
                    }
                    binding.progressBar.isVisible = false
                    listEmpty(taskList)

                    taskList.reverse()

                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("INFOTESTE","onCanceled:")
                }

            })
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.textInfo.text = if (taskList.isEmpty()) {
            getString(R.string.text_list_task_empty)
        } else {
            ""
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}