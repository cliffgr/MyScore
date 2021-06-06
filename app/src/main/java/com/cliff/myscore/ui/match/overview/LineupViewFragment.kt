package com.cliff.myscore.ui.match.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.cliff.myscore.R
import com.cliff.myscore.databinding.FragmentEventsBinding
import com.cliff.myscore.databinding.FragmentLineupBinding
import com.cliff.myscore.ui.match.FixtureViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LineupViewFragment : Fragment() {

    lateinit var lineupType: String

    companion object {

        const val ARG_LINEUP_TYPE = "lineup-type"

        @JvmStatic
        fun newInstance(lineupType: String) =
            LineupViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LINEUP_TYPE, lineupType)
                }
            }
    }

    private var _binding: FragmentLineupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FixtureViewModel by navGraphViewModels(R.id.fixtureFragment) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            lineupType = it.getString(ARG_LINEUP_TYPE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLineupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lineup.observe(requireParentFragment().viewLifecycleOwner, {
            if (lineupType == "Home") {
                binding.teamCustonView.bind(it.first.startXI, it.first.coach, it.first.formation?:"")
            } else if (lineupType == "Away") {
                binding.teamCustonView.bind(it.second.startXI, it.second.coach, it.second.formation?:"")
            }
        })


    }


}