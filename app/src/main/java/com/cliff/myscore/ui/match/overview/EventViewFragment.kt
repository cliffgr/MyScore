package com.cliff.myscore.ui.match.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.R
import com.cliff.myscore.databinding.FragmentEventsBinding
import com.cliff.myscore.databinding.FragmentOverViewBinding
import com.cliff.myscore.ui.match.FixtureViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventViewFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            EventViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FixtureViewModel by navGraphViewModels(R.id.fixtureFragment) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = EventAdapter() { id ->

            }
        }

        viewModel.events.observe(requireParentFragment().viewLifecycleOwner, {
            Log.i("EventViewFragment", "Event : $it");
            with(binding.recyclerView.adapter as EventAdapter) {
                homeId = it.second
                awayId = it.third
                submitList(it.first)
            }
        })
    }


}