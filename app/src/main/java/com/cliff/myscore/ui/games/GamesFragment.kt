package com.cliff.myscore.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cliff.myscore.databinding.FragmentGamesBinding
import com.cliff.myscore.ui.match.GeneralPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private val gamesViewModel: GamesViewModel by viewModels()

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    val simpleDateFormat = SimpleDateFormat("EEE, MMM d")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val pages = arrayListOf<ScheduledGamesFragment>()

        val pageTitles = arrayListOf<String>()

        val cal = Calendar.getInstance()
        cal.time = Date()

        for (i in -6..5) {
            when (i) {
                -1 -> pageTitles.add("Yesterday")
                0 -> pageTitles.add("Today")
                1 -> pageTitles.add("Tomorrow")
                else -> {
                    cal.add(Calendar.DATE, i)
                    pageTitles.add(simpleDateFormat.format(cal.time))
                    cal.time = Date()
                }
            }

            pages.add(ScheduledGamesFragment.newInstance(i))
        }

        binding.viewPager.apply {
            adapter =
                GeneralPagerAdapter(
                    this@GamesFragment,
                    pages = pages,
                    pageTitles.size
                )
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = pageTitles[position]
        }.attach()

        binding.viewPager.setCurrentItem(6,false)
    }
}