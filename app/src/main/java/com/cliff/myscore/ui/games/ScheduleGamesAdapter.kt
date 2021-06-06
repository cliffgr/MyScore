package com.cliff.myscore.ui.games

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cliff.myscore.databinding.ItemFixtureBinding
import com.cliff.myscore.databinding.ItemHeaderBinding
import com.cliff.myscore.databinding.ItemMatchResultsBinding
import com.cliff.myscore.model.FixtureLiveScore
import com.cliff.myscore.model.LiveScore
import java.text.SimpleDateFormat


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class ScheduleGamesAdapter(private val listener: (Int,String) -> Unit) :
    ListAdapter<LiveScore, RecyclerView.ViewHolder>(LiveScoreDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolder -> {
                holder.bind(item.fixtureLiveScore!!)
                holder.itemView.setOnClickListener {
                    listener(item.fixtureLiveScore!!.fixture.id, item.fixtureLiveScore!!.fixture.status.short)
                }

            }
            is HeaderHolder -> {
                holder.bind(item.country!!, item?.countryLogo ?: "")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).isHeader) {
            true -> ITEM_VIEW_TYPE_HEADER
            false -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder(private val itemBinding: ItemMatchResultsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {

            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMatchResultsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }


        fun bind(fixtureLiveScore: FixtureLiveScore) {
            with(itemBinding) {
                textViewHome.text = fixtureLiveScore.teams.home.name
                textViewAway.text = fixtureLiveScore.teams.away.name
                textViewLeague.text =
                    "${fixtureLiveScore.league.name}-${fixtureLiveScore.league.round}"


                Log.e("Adapter", "Status : ${fixtureLiveScore.fixture.status.short}")
                val dateFormat = SimpleDateFormat("HH:mm")
                when (fixtureLiveScore.fixture.status.short) {
                    "FT" -> {
                        textViewScore.text =
                            "${fixtureLiveScore.goals.home} - ${fixtureLiveScore.goals.away}"
                        textViewStatus.text = "FT"
                    }
                    "NS" -> {
                        textViewScore.text =
                            dateFormat.format(fixtureLiveScore.fixture.timestamp)
                        textViewStatus.text = "NS"
                    }
                    else -> {
                        textViewScore.text = "${fixtureLiveScore.goals.home} - ${fixtureLiveScore.goals.away}"
                        textViewStatus.text = fixtureLiveScore.fixture.status.long
                    }
                }

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

    class HeaderHolder(private val itemHeader: ItemHeaderBinding) :
        RecyclerView.ViewHolder(itemHeader.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderHolder(binding)
            }
        }

        fun bind(title: String, logo: String) {
            itemHeader.countryName.text = title
            Glide.with(itemView)
                .load(logo)
                .centerCrop()
                .into(itemHeader.countryLogo)
        }
    }

}


class LiveScoreDiffCallback : DiffUtil.ItemCallback<LiveScore>() {

    override fun areItemsTheSame(oldItem: LiveScore, newItem: LiveScore): Boolean {
        return if (oldItem.isHeader) {
            oldItem.country == newItem.country
        } else {
            oldItem.fixtureLiveScore?.fixture?.id == newItem.fixtureLiveScore?.fixture?.id
        }
    }

    override fun areContentsTheSame(oldItem: LiveScore, newItem: LiveScore): Boolean {
        return if (oldItem.isHeader) {
            oldItem.country == newItem.country
        } else {
            oldItem.fixtureLiveScore == newItem.fixtureLiveScore
        }

    }

}