package com.greenwoo.presentation.host.search.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.presentation.databinding.ItemUserBinding

class UsersAdapter(private val onClickedDetailUsers: () -> Unit) :
    RecyclerView.Adapter<UsersAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailUsers.invoke()
        }
    }

    override fun getItemCount() = 10

    class ItemHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}