package com.greenwoo.presentation.host.profile.subscribe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.presentation.databinding.ItemFriendBinding

class SubscribeAdapter(private val onClickedDetail: () -> Unit) :
    RecyclerView.Adapter<SubscribeAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetail.invoke()
        }
    }

    override fun getItemCount() = 4

    class ItemHolder(val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root)
}