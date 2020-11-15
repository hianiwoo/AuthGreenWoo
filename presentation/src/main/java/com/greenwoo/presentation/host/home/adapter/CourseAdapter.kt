package com.greenwoo.presentation.host.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.domain.models.Course
import com.greenwoo.presentation.databinding.ItemCourseBinding

class CourseAdapter(private val onClickedDetailCourse: (Course) -> Unit) :
    RecyclerView.Adapter<CourseAdapter.ItemHolder>() {

    var list : List<Course> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickedDetailCourse.invoke(list[position])
        }
        holder.binding.item = list[position]
    }

    override fun getItemCount() = list.size

    fun setCourse(courses: List<Course>) {
        list = courses
        notifyDataSetChanged()
    }

    class ItemHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root)
}