package com.cliff.myscore.ui.standing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliff.myscore.databinding.FragmentStandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingFragment : Fragment() {

    private val standingViewModel: StandingViewModel by viewModels()
    private val args: StandingFragmentArgs by navArgs()

    private var _binding: FragmentStandingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = StandingAdapter {
                Log.e("Test", "Test $it")
            }
        }

        args.let {
            standingViewModel.standingByLeague(it.leagueCode,"2020")
        }


        standingViewModel.standing.observe(viewLifecycleOwner, {
            Log.i("Standing" ," $it")
          //  (binding.recyclerView.adapter as StandingAdapter).submitList(it)
        })
    }
}