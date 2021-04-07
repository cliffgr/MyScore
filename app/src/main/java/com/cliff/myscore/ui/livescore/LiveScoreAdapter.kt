package com.cliff.myscore.ui.livescore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cliff.myscore.databinding.ItemFixtureBinding
import com.cliff.myscore.model.FixtureLiveScore

class LiveScoreAdapter(private val listener: (Int) -> Unit) :
    ListAdapter<FixtureLiveScore, LiveScoreAdapter.ViewHolder>(LiveScoreDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemFixtureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item.fixture.id) }
    }


    inner class ViewHolder(private val itemBinding: ItemFixtureBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                // listener(getItem(layoutPosition), binding.mcvPoster, binding.tvTitle)
            }
        }

        fun bind(fixtureLiveScore: FixtureLiveScore) {
            with(itemBinding) {
                textViewHome.text = fixtureLiveScore.teams.home.name
                textViewAway.text = fixtureLiveScore.teams.away.name
                textViewScore.text =
                    "${fixtureLiveScore.goals.home} : ${fixtureLiveScore.goals.away}"
                textViewElapse.text = "${fixtureLiveScore.fixture.status.elapsed}'"
                textViewLeague.text = "${fixtureLiveScore.league.country} - ${fixtureLiveScore.league.name}"
                textViewRound.text = fixtureLiveScore.league.round

                Glide.with(itemView)
                    .load(fixtureLiveScore.teams.home.logo)
                    .centerCrop()
                    .into(imageViewHome)

                Glide.with(itemView)
                    .load(fixtureLiveScore.teams.away.logo)
                    .centerCrop()
                    .into(imageViewAway)
            }
        }
    }

}

class LiveScoreDiffCallback : DiffUtil.ItemCallback<FixtureLiveScore>() {

    override fun areItemsTheSame(oldItem: FixtureLiveScore, newItem: FixtureLiveScore): Boolean {
        return oldItem.fixture.id == newItem.fixture.id
    }

    override fun areContentsTheSame(oldItem: FixtureLiveScore, newItem: FixtureLiveScore): Boolean {
        return oldItem == newItem
    }

}