package com.greenwoo.presentation.host.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.domain.models.Module
import com.greenwoo.presentation.databinding.ItemModuleBinding

class ModuleAdapter(private val onClickedDetailModule: (Module) -> Unit) :
    RecyclerView.Adapter<ModuleAdapter.ItemHolder>() {

    var list: List<Module> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailModule.invoke(list[position])
        }
        holder.binding.item = list[position]
    }

    override fun getItemCount() = list.size

    fun setModule(modules: List<Module>) {
        list = modules
        notifyDataSetChanged()
    }

    class ItemHolder(val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root)
}