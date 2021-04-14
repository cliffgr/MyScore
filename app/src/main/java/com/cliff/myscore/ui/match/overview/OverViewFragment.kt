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

    private val viewModel: FixtureViewModel by navGraphViewModels(R.id.fixtureFragment){
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_over_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.statistics.observe(requireParentFragment().viewLifecycleOwner, {
            Log.i("OverViewFragment", "Statistics : $it");
        })
    }


}