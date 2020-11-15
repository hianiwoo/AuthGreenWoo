package com.greenwoo.presentation.host.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.domain.models.Folder
import com.greenwoo.presentation.databinding.ItemFolderBinding

class FolderAdapter(private val onClickedDetailFolder: (Folder) -> Unit) :
    RecyclerView.Adapter<FolderAdapter.ItemHolder>() {

    var list : List<Folder> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailFolder.invoke(list[position])
        }
        holder.binding.item = list[position]
    }

    override fun getItemCount() = list.size

    fun setFolder(folders: List<Folder>) {
        list = folders
        notifyDataSetChanged()
    }

    class ItemHolder(val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root)
}