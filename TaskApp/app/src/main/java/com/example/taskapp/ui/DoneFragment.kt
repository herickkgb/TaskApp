package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentDoneBinding
import com.example.taskapp.databinding.FragmentTodokBinding
import com.example.taskapp.ui.adapter.TaskAdapter

class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!


    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(getTask())
    }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)

        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.setHasFixedSize(true)
        binding.rvTask.adapter = taskAdapter
    }

    private fun getTask() = listOf<Task>(
        Task("1", "Revisar código fonte", Status.DONE),
        Task("2", "Realizar testes de unidade", Status.DONE),
        Task("3", "Corrigir erros de segurança", Status.DONE),
        Task("4", "Atualizar bibliotecas de terceiros", Status.DONE),
        Task("5", "Realizar revisão de código", Status.DONE),
    )


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}