package com.cliff.myscore.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.R
import com.cliff.myscore.databinding.FragmentDashboardBinding
import com.cliff.myscore.databinding.FragmentHomeBinding
import com.cliff.myscore.ui.home.HomeViewModel
import com.cliff.myscore.ui.home.LiveScoreAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import java.util.zip.CheckedOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class CountriesFragment : Fragment() {

     val dashboardViewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardViewModel.getSupportedCountries();

        with(binding.recyclerView) {
            layoutManager= LinearLayoutManager(context)
            adapter= CountriesAdapter(){

            }
        }

        dashboardViewModel.countries.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as CountriesAdapter).submitList(it)
        })
    }
}