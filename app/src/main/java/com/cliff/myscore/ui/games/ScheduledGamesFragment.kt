package com.cliff.myscore.ui.games


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.databinding.FragmentScheduledGamesBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ScheduledGamesFragment : Fragment() {
    private var dateOffset: Int = 0
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var _binding: FragmentScheduledGamesBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_DATE_OFFSET = "date_offset"

        @JvmStatic
        fun newInstance(dateOffset: Int) = ScheduledGamesFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_DATE_OFFSET, dateOffset)
            }
        }
    }

    private val viewModel: ScheduledGamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dateOffset = it.getInt(ARG_DATE_OFFSET, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduledGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ScheduleGamesAdapter { id, status ->
                Log.e("Tap", "ID : $id - Status : $status")
                when (status) {
                    "FT" -> {
                        val directions: NavDirections =
                            GamesFragmentDirections.actionNavigationDashboardToFixtureFragment(
                                id
                            )
                        findNavController().navigate(directions)
                    }
                    else -> {

                    }

                }

            }
        }

        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DATE, dateOffset)
        val dateTobeRequested = simpleDateFormat.format(cal.time)

        viewModel.getScheduledMatchesForTheDay(dateTobeRequested)
    }

    private fun initObservers() {
        viewModel.scheduledMatches.observe(viewLifecycleOwner, {
            (binding.recyclerView.adapter as ScheduleGamesAdapter).submitList(it)
        })
    }

}