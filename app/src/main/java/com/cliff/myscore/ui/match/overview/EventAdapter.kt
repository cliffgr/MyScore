package com.cliff.myscore.ui.match.overview

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cliff.myscore.R
import com.cliff.myscore.databinding.ItemEventBinding
import com.cliff.myscore.model.FixtureLiveScore.Event


class EventAdapter(private val listener: (Int) -> Unit) :
    ListAdapter<Event, EventAdapter.ViewHolder>(LeaguesDiffCallback()) {

    var homeId: Int = 0
    var awayId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class ViewHolder(private val itemBinding: ItemEventBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {

        }

        fun bind(event: Event) {
            with(itemBinding) {

                itemBinding.time.text = "${event.time.elapsed}'"

                if (homeId == event.team.id) {
                    away.visibility = View.GONE
                    home.visibility = View.VISIBLE
                    eventHomePlayerName.text = event.player.name
                    placeImage(eventHomeImage, event.type, event.detail)

                } else if (awayId == event.team.id) {
                    home.visibility = View.GONE
                    away.visibility = View.VISIBLE
                    eventAwayPlayerName.text = event.player.name
                    placeImage(eventAwayImage, event.type, event.detail)
                }
            }
        }


        private fun placeImage(imageView: ImageView, type: String, details: String) {

            when (details) {
                "Normal Goal" -> imageView.setImageResource(R.drawable.ic_goal)
                "Penalty" -> imageView.setImageResource(R.drawable.ic_goal)
                "Own Goal" -> imageView.setImageResource(R.drawable.ic_goal)
                "Yellow Card" -> imageView.setImageResource(R.drawable.ic_yellow_card)
                "Red Card" -> imageView.setImageResource(R.drawable.ic_red_card)
                "Second Yellow card" -> imageView.setImageResource(R.drawable.ic_red_card)
                "Substitution 1", "Substitution 2", "Substitution 3", "Substitution 4" -> imageView.setImageResource(
                    R.drawable.ic_substitution
                )

            }

        }
    }
}


class LeaguesDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return false;
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }

}