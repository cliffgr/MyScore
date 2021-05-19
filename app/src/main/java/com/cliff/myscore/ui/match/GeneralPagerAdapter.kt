package com.cliff.myscore.ui.match

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GeneralPagerAdapter(
    fm: Fragment,
    val pages: List<Fragment>,
    val tabsCount: Int
) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return tabsCount
    }

    override fun createFragment(position: Int): Fragment {
        return try {
            pages[position]
        } catch (e: Exception) {
            pages[0]
        }
    }
}