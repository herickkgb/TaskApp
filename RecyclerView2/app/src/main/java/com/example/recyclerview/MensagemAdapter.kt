package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerview.newRecyclerView.Mensagem


class MensagemAdapter(
    private val lista: List<Mensagem>
) : Adapter<MensagemAdapter.MensagemViewHolder>() {

    inner class MensagemViewHolder(
        val itemView: View
    ) : ViewHolder( itemView )  {

        val textNome: TextView = itemView.findViewById(R.id.text_Nome)
        val textUltima: TextView = itemView.findViewById(R.id.text_Ultima)
        val textHorario: TextView = itemView.findViewById(R.id.textHorario)

    }

    //Ao Criar o View Holder -> Criar a visualização
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {

        val layoutInflater = LayoutInflater.from(
            parent.context
        )

        val itemView = layoutInflater.inflate(
            R.layout.item_lista, parent, false
        )

        return MensagemViewHolder( itemView )

    }


    // ao vincular os dados para a visualização
    override fun onBindViewHolder(mensagemViewHolder: MensagemViewHolder, position: Int) {

        val mensagem = lista[position]
        mensagemViewHolder.textNome.text = mensagem.nome
        mensagemViewHolder.textUltima.text = mensagem.ultima
        mensagemViewHolder.textHorario.text = mensagem.horario

    }

    //getItemCount -> Recuperar a quantidade de itens
    override fun getItemCount(): Int {
        return lista.size
    }


}