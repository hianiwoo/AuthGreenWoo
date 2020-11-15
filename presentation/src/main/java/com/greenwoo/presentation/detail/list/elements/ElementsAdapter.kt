package com.greenwoo.presentation.detail.list.elements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module
import com.greenwoo.domain.models.User
import com.greenwoo.presentation.databinding.ItemCourseBinding
import com.greenwoo.presentation.databinding.ItemFolderBinding
import com.greenwoo.presentation.databinding.ItemModuleBinding
import com.greenwoo.presentation.databinding.ItemUserBinding

class ElementsAdapter(private val onClickedDetail: (Any) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_MODULE = 0
        private const val ITEM_FOLDER = 1
        private const val ITEM_COURSE = 2
        private const val ITEM_USER = 3
    }

    var type: Int = 0
    var listModules: List<Module> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listFolders: List<Folder> = listOf()
    var listCourses: List<Course> = listOf()
    var listUsers: List<User> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            ITEM_MODULE -> ItemModuleHolder(
                ItemModuleBinding.inflate(inflater, parent, false)
            )
            ITEM_FOLDER -> ItemFolderHolder(
                ItemFolderBinding.inflate(inflater, parent, false)
            )
            ITEM_COURSE -> ItemCourseHolder(
                ItemCourseBinding.inflate(inflater, parent, false)
            )
            ITEM_USER -> ItemUserHolder(
                ItemUserBinding.inflate(inflater, parent, false)
            )
            else -> throw NoSuchElementException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { onClickedDetail.invoke(position) }

        when (holder) {
            is ItemModuleHolder -> holder.binding.item = listModules[position]
            is ItemFolderHolder -> holder.binding.item = listFolders[position]
            is ItemCourseHolder -> holder.binding.item = listCourses[position]
        }
    }

    override fun getItemCount(): Int {
        return when (type) {
            ITEM_MODULE -> listModules.size
            ITEM_FOLDER -> listFolders.size
            ITEM_COURSE -> listCourses.size
            ITEM_USER -> listUsers.size
            else -> 0
        }
    }

    class ItemModuleHolder(val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemFolderHolder(val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemCourseHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemUserHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}