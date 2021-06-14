package com.cliff.myscore.ui.custom

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.cliff.myscore.databinding.AvatarAndNameViewBinding
import com.cliff.myscore.model.FixtureLiveScore

class AvatarAndNameView(
    context: Context,
    player: FixtureLiveScore.Lineup.StartXI.Player,
    private val listener: (Int) -> Unit
) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        val binding = AvatarAndNameViewBinding.inflate(LayoutInflater.from(context), this)
        binding.playersName.text = player.name
        Glide.with(context)
            .load("https://media.api-sports.io/football/players/${player.id}.png")
            .fitCenter()
            .circleCrop()
            .into(binding.playersImage)
        this.setOnClickListener {
            listener(player.id)
        }
    }
}