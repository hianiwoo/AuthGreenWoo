package com.greenwoo.presentation.host.search.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.presentation.databinding.ItemCourseBinding

class CoursesAdapter(private val onClickedDetailCourse: () -> Unit) :
    RecyclerView.Adapter<CoursesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailCourse.invoke()
        }
    }

    override fun getItemCount() = 10

    class ItemHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root)
}