package com.cliff.myscore.ui.standing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cliff.myscore.databinding.ItemLeaguesBinding
import com.cliff.myscore.model.Leagues


class StandingAdapter(private val listener: (Int) -> Unit) :
    ListAdapter<Leagues, StandingAdapter.ViewHolder>(LeaguesDiffCallback()) {

    //var checkBoxesPositions: SparseBooleanArray = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemLeaguesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ViewHolder(private val itemBinding: ItemLeaguesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var leagues: Leagues

        init {
            itemView.setOnClickListener {
                listener(leagues.league.id)
            }
        }

        fun bind(leagues: Leagues) {
            this.leagues = leagues
            with(itemBinding) {
                leagueName.text = leagues.league.name
                leagues.league.logo.let {
                    Glide.with(itemView)
                        .load(leagues.league.logo)
                        .fitCenter()
                        .into(leagueImage)
                }

                checkBox.visibility = View.INVISIBLE


            }
        }
    }
}

class LeaguesDiffCallback : DiffUtil.ItemCallback<Leagues>() {

    override fun areItemsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
        return oldItem.league.id == newItem.league.id
    }

    override fun areContentsTheSame(oldItem: Leagues, newItem: Leagues): Boolean {
        return oldItem == newItem
    }

}