package com.greenwoo.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(
    private val array: Array<Pair<String, Fragment>>, fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = array.size

    override fun getItem(position: Int) = array[position].second

    override fun getPageTitle(position: Int) = array[position].first
}