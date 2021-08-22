package com.cliff.myscore.ui.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.bl.setVisible
import com.cliff.myscore.databinding.FragmentCountriesBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val countriesViewModel: CountriesViewModel by viewModels()
    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.visibility = View.GONE

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = CountriesAdapter {
                val directions: NavDirections =
                    CountriesFragmentDirections.actionNavigationCountriesToLeagueFragment2(it)
                findNavController().navigate(directions)
            }
        }

        countriesViewModel.filteredCountries.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as CountriesAdapter).submitList(it)
        })

        countriesViewModel.loader.observe(viewLifecycleOwner, { flag ->
            binding.progressBar.setVisible(flag)
        })
    }

    override fun onResume() {
        super.onResume()
        countriesViewModel.getSupportedCountries()
    }
}