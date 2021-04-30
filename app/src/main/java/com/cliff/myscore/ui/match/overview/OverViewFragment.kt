package com.cliff.myscore.ui.match.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.cliff.comparingperformancebar.PercentageProgressBar
import com.cliff.comparingperformancebar.ValueProgressBar
import com.cliff.myscore.R
import com.cliff.myscore.databinding.FragmentOverViewBinding
import com.cliff.myscore.ui.match.FixtureViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OverViewFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            OverViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FixtureViewModel by navGraphViewModels(R.id.fixtureFragment) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statistics.observe(
            requireParentFragment().viewLifecycleOwner,
            { matchStatistics ->

                matchStatistics.forEach {
                    Log.i(
                        "OverViewFragment",
                        "${it.isTypePercentage}  ->  ${it.title} ---- ${it.team1} - ${it.team2}"
                    )

                    when (it.isTypePercentage) {
                        true -> {
                            val layoutPercentage: View = LayoutInflater.from(requireContext())
                                .inflate(
                                    R.layout.item_statistics_percentage,
                                    binding.constraintLayout,
                                    false
                                )

                            val textTitle: TextView = layoutPercentage.findViewById(R.id.title)
                            textTitle.text = it.title
                            val percentageProgressBar: PercentageProgressBar = layoutPercentage.findViewById(R.id.percentage)
                            percentageProgressBar.setProgress(it.team1.toString().replace("%","").toFloat())
                            binding.constraintLayout.addView(layoutPercentage)
                        }
                        false -> {
                            val layoutValue: View = LayoutInflater.from(requireContext()).inflate(
                                R.layout.item_statistics_value,
                                binding.constraintLayout,
                                false
                            )

                            val textTitle: TextView = layoutValue.findViewById(R.id.title)
                            textTitle.text = it.title

                            val valueProgressBar: ValueProgressBar = layoutValue.findViewById(R.id.value)
                            valueProgressBar.setValues(it.team1.toString().toFloat(), it.team2.toString().toFloat())
                            binding.constraintLayout.addView(layoutValue)
                        }
                    }
                }


                /* for (i in it.first.statistics.indices) {
                     val layout: View =
                         LayoutInflater.from(requireContext())
                             .inflate(
                                 R.layout.item_statistics_percentage,
                                 binding.constraintLayout,
                                 false
                             )

                     val textTitle: TextView = layout.findViewById(R.id.title)
                     textTitle.text = statisticsList?.type

                 }


                 it.first.statistics.forEachIndexed { index, statisticsList ->

                     val layout2: View =
                         LayoutInflater.from(requireContext())
                             .inflate(
                                 R.layout.item_statistics_percentage,
                                 binding.constraintLayout,
                                 false
                             )

                     val textTitle: TextView = layout2.findViewById(R.id.title)
                     textTitle.text = statisticsList?.type

                     val percentageProgressBar: PercentageProgressBar =
                         layout2.findViewById(R.id.percentage)

                     statisticsList.value.let { value ->
                         if (value.toString().contains("%")) {
                             value.toString().replace("%", "")
                             it.second.statistics[index].value.toString().replace("%", "")
                         }
                         val total = value?.toString()
                             .toFloat() + it.second.statistics[index].value?.toString().toFloat()
                         val percentage = (value.toString().toFloat() / total) * 100f
                         percentageProgressBar.setProgress(percentage)
                     }
                     binding.constraintLayout.addView(layout2)
                 }*/
            })
    }


}