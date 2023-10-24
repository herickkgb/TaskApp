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
import com.example.taskapp.databinding.FragmentDoingBinding
import com.example.taskapp.databinding.FragmentHomeBinding
import com.example.taskapp.ui.adapter.TaskAdapter

class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
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
        Task("1", "Implementar a funcionalidade de login no app", Status.DOING),
        Task("2", "Criar a interface de usuário para a tela principal", Status.DOING),
        Task("3", "Integrar o aplicativo com um serviço de geolocalização", Status.DOING),
        Task("4", "Testar a funcionalidade de compartilhamento de mídia", Status.DOING),
        Task("5", "Adicionar suporte a notificações push", Status.DOING),
        Task("6", "Otimizar o desempenho do aplicativo", Status.DOING),
        Task("7", "Escrever documentação do aplicativo", Status.DOING),
        Task("8", "Realizar testes de usabilidade", Status.DOING),
        Task("9", "Corrigir bugs relatados pelos usuários", Status.DOING),
        Task("10", "Lançar uma campanha de marketing para o aplicativo", Status.DOING),
        Task("10", "Analisar dados de pesquisa de satisfação do cliente", Status.DOING),
    )


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}