package com.cliff.myscore.ui.setup.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.R
import com.cliff.myscore.bl.setVisible
import com.cliff.myscore.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel.getSupportedCountries();

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
        })
    }
}