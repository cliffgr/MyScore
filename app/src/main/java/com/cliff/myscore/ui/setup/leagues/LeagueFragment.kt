package com.cliff.myscore.ui.setup.leagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.databinding.FragmentLeagueBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeagueFragment : Fragment() {


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
        val args by navArgs<LeagueFragmentArgs>()

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = LeaguesAdapter { id, flag ->
                leagueViewModel.addFav(id, flag)
            }
        }

        leagueViewModel.leaguesByCountryCode(args.countryCode)

        leagueViewModel.leagues.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as LeaguesAdapter).submitList(it)
        })
    }

}