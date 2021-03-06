package com.cliff.myscore.ui.livescore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.data.local.sharePref.PrefManager
import com.cliff.myscore.databinding.FragmentHomeBinding

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var pref: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getLiveScore()

        with(binding.recyclerView) {
            layoutManager= LinearLayoutManager(context)
            adapter= LiveScoreAdapter{
                val directions: NavDirections = HomeFragmentDirections.actionNavigationHomeToFixtureFragment(it)
                findNavController().navigate(directions)
            }
        }

        homeViewModel.liveScore.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as LiveScoreAdapter).submitList(it)
        })

        pref.intFirstRunPref=false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}