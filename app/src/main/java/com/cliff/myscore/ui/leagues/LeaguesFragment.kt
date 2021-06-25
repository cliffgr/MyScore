package com.cliff.myscore.ui.leagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.databinding.FragmentLeagueBinding
import com.cliff.myscore.ui.setup.leagues.LeagueViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaguesFragment : Fragment() {

    private val leagueViewModel: LeagueViewModel by viewModels()

    private var _binding: FragmentLeagueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = LeaguesAdapter() { id ->

            }
        }

        leagueViewModel.leaguesByCountryCode("gr")

        leagueViewModel.leagues.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as LeaguesAdapter).submitList(it)
        })
    }
}