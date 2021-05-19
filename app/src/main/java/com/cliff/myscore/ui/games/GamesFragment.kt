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

        /*  dashboardViewModel.getSupportedCountries();

          with(binding.recyclerView) {
              layoutManager = LinearLayoutManager(context)
              adapter = CountriesAdapter() {
                  val directions: NavDirections =
                      CountriesFragmentDirections.actionCountriesFragmentToLeagueFragment(it)

                  findNavController().navigate(directions)
              }
          }

          binding.button.setOnClickListener {
              val directions: NavDirections =
                  CountriesFragmentDirections.actionCountriesFragmentToMobileNavigation()
              findNavController().navigate(directions)
          }

          dashboardViewModel.countries.observe(viewLifecycleOwner, {
              (binding.recyclerView.adapter as CountriesAdapter).submitList(it)
          })

          dashboardViewModel.loader.observe(viewLifecycleOwner, { flag ->
              binding.progressBar.setVisible(flag)
          })*/
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

            ScheduledGamesFragment.newInstance(i)
        }

        binding.viewPager.apply {
            adapter =
                GeneralPagerAdapter(
                    this@GamesFragment,
                    pages = pages,
                    pageTitles.size
                )

            //offscreenPageLimit = 3
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = pageTitles[position]
        }.attach()

        // binding.tabs.setScrollPosition(5,0f,false)
        //binding.viewPager.setCurrentItem(5,true)
    }
}