package com.cliff.myscore.ui.setup.leagues

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cliff.myscore.databinding.ItemLeaguesBinding
import com.cliff.myscore.model.Leagues

class LeaguesAdapter(private val listener: (Int, Boolean) -> Unit) :
    ListAdapter<Leagues, LeaguesAdapter.ViewHolder>(LeaguesDiffCallback()) {

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

        init {

        }

        fun bind(leagues: Leagues) {
            with(itemBinding) {
                leagueName.text = leagues.league.name
                leagues.league.logo?.let {
                    Glide.with(itemView)
                        .load(leagues.league.logo)
                        .fitCenter()
                        .into(leagueImage)

                    checkBox.setOnCheckedChangeListener { _, isChecked ->
                        // checkBoxesPositions.put()
                        listener(leagues.league.id, isChecked)
                    }
                }
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