package com.cliff.myscore.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.cliff.myscore.databinding.TeamViewBinding

import com.cliff.myscore.model.FixtureLiveScore

class TeamView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {


    private val binding = TeamViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val memberViews: MutableList<View> = mutableListOf()

    init {
        binding.teamWrapper.background = HalfFieldDrawable(context)
        radius = 8.toFloat()
    }

    fun bind(members: List<FixtureLiveScore.Lineup.StartXI>) {
        memberViews.forEach {
            binding.teamWrapper.removeView(it)
        }
        memberViews.clear()

        binding.apply {

            val ids = members.map{ member ->
                val view = AvatarAndNameView(context, member.player)
                val generateViewId = View.generateViewId()
                view.id = generateViewId
                teamWrapper.addView(view)
                memberViews.add(view)
                generateViewId
            }

            val data: MutableList<Int> = mutableListOf()
            data.add(ids[0])

            teamMembersG.referencedIds = data.toIntArray()

            teamMembersD.referencedIds = ids.subList(1, ids.size).toIntArray()

        }
    }
}