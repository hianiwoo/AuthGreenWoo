package com.greenwoo.presentation.host.search.modules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.presentation.databinding.ItemModuleBinding

class ModulesAdapter(private val onClickedDetailModule: () -> Unit) :
    RecyclerView.Adapter<ModulesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailModule.invoke()
        }
    }

    override fun getItemCount() = 5

    class ItemHolder(val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root)
}