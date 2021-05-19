package com.cliff.myscore.ui.games

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cliff.myscore.R

class ScheduledGamesFragment : Fragment() {
    private var dateOffset: Int = 0

    companion object {
        const val ARG_DATE_OFFSET = "date_offset"

        @JvmStatic
        fun newInstance(dateOffset: Int) = ScheduledGamesFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_DATE_OFFSET, dateOffset)
            }
        }
    }

    private lateinit var viewModel: ScheduledGamesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            dateOffset = it.getInt(ARG_DATE_OFFSET, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scheduled_games, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduledGamesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}