package com.cliff.myscore.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.cliff.myscore.R
import com.cliff.myscore.databinding.FragmentFixtureBinding
import com.cliff.myscore.ui.match.overview.EventViewFragment
import com.cliff.myscore.ui.match.overview.LineupViewFragment
import com.cliff.myscore.ui.match.overview.OverViewFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureFragment : Fragment() {

    companion object {
        fun newInstance() = FixtureFragment()
    }

    private val viewModel by navGraphViewModels<FixtureViewModel>(R.id.fixtureFragment) {
        defaultViewModelProviderFactory
    }

    private var _binding: FragmentFixtureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // return inflater.inflate(R.layout.fixture_fragment, container, false)
        _binding = FragmentFixtureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args by navArgs<FixtureFragmentArgs>()
        viewModel.getDetailsOfMatch(args.fixtureId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pages = listOf(
            OverViewFragment.newInstance(),
            EventViewFragment.newInstance(),
            LineupViewFragment.newInstance("Home"),
            LineupViewFragment.newInstance("Away"),
        )

        val pageTitles = listOf(
            "Overview",
            "Events",
            "Lineup H",
            "Lineup A"
        )



        binding.viewPager.apply {
            adapter =
                GeneralPagerAdapter(
                    this@FixtureFragment,
                    pages = pages,
                )

            offscreenPageLimit = 3
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = pageTitles[position]
        }.attach()
    }

    private fun initObservers() {
        viewModel.teams.observe(viewLifecycleOwner, { teams ->
            with(teams) {
                binding.textViewHome.text = home.name
                binding.textViewAway.text = away.name

                Glide.with(requireActivity())
                    .load(home.logo)
                    .centerCrop()
                    .into(binding.imageViewHome)

                Glide.with(requireActivity())
                    .load(away.logo)
                    .centerCrop()
                    .into(binding.imageViewAway)
            }
        })

        viewModel.fixture.observe(viewLifecycleOwner, { fixture ->
            with(fixture) {
                binding.textViewElapse.text = "${status.elapsed}'"
                binding.referee.text = fixture.referee
            }
        })

        viewModel.league.observe(viewLifecycleOwner, { league ->
            with(league) {
                binding.textViewLeague.text = "$name - $country"
                binding.textViewRound.text = round

                Glide.with(requireActivity())
                    .load(flag)
                    .centerCrop()
                    .into(binding.countryLogo)

            }
        })

        viewModel.score.observe(viewLifecycleOwner, {
            binding.textViewScore.text = it
        })
    }

}