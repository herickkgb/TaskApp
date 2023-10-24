package com.example.taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.R
import com.example.taskapp.data.model.Status
import com.example.taskapp.data.model.Task
import com.example.taskapp.databinding.FragmentHomeBinding
import com.example.taskapp.databinding.FragmentTodokBinding
import com.example.taskapp.ui.adapter.TaskAdapter

class TodoFragment : Fragment() {
    private var _binding: FragmentTodokBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodokBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        initRecyclerView(getTask())
    }

    private fun initListener() {
        binding.fadAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)

        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.setHasFixedSize(true)
        binding.rvTask.adapter = taskAdapter
    }

    private fun getTask() = listOf<Task>(
        Task("1", "Realizar pesquisa de mercado para o novo produto", Status.TODO),
        Task("2", "Escrever relatório de vendas do último trimestre", Status.TODO),
        Task("3", "Agendar reunião de equipe para a próxima semana", Status.TODO),
        Task("4", "Revisar o conteúdo do site e atualizar informações", Status.TODO),
        Task("5", "Preparar apresentação para o cliente sobre os resultados do projeto", Status.TODO),
        Task("6", "Enviar convites para o evento de lançamento do produto", Status.TODO),
        Task("7", "Conduzir treinamento para novos funcionários", Status.TODO),
        Task("8", "Criar campanha de marketing nas redes sociais", Status.TODO),
        Task("9", "Desenvolver um protótipo de design de interface de usuário para o aplicativo móvel", Status.TODO),
        Task("10", "Analisar dados de pesquisa de satisfação do cliente", Status.TODO)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}