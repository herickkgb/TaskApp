package com.example.recyclerview.newRecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.MensagemAdapter
import com.example.recyclerview.R

class RecyclerViewActivity : AppCompatActivity() {
      private lateinit var rvLista: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val lista = listOf(
            Mensagem("jamilton", "Olá, tudo bem?", "10:45"),
            Mensagem("ana", "Te vi ontem..", "00:45"),
            Mensagem("maria", "Não acredito...", "06:03"),
            Mensagem("pedro", "Futebol hoje?", "15:32"),
        )

        rvLista = findViewById(R.id.rv_lista)
        rvLista.adapter = MensagemAdapter( lista )//tipo: MensagemAdapter, Adapter
        rvLista.layoutManager = LinearLayoutManager(this)

    }


}