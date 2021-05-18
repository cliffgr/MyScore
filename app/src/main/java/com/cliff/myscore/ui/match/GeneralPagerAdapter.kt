package com.cliff.myscore.ui.match

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GeneralPagerAdapter(
    fm: Fragment,
    val pages: List<Fragment>
) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}