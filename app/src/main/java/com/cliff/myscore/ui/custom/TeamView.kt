package com.cliff.myscore.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
    private lateinit var callbackListener: (Int) -> Unit

    init {
        binding.teamWrapper.background = HalfFieldDrawable(context)
        radius = 8.toFloat()
    }

    fun setCallback(listener: (Int) -> Unit) {
        callbackListener = listener
    }

    fun bind(
        members: List<FixtureLiveScore.Lineup.StartXI>,
        coachModel: FixtureLiveScore.Lineup.Coach,
        formationString: String
    ) {
        memberViews.forEach {
            binding.teamWrapper.removeView(it)
        }
        memberViews.clear()


        binding.apply {
            coach.text = coachModel.name
            formation.text = formationString
            val ids: List<Pair<String, Int>> = members.map { member ->
                val view = AvatarAndNameView(context, member.player) {
                    callbackListener(it)
                }
                val generateViewId = View.generateViewId()
                view.id = generateViewId
                teamWrapper.addView(view)
                memberViews.add(view)
                Pair(member.player.pos, generateViewId)
            }

            val gatekeepers: List<Int> = ids.filter { it.first == "G" }.map { it.second }
            val defenders: List<Int> = ids.filter { it.first == "D" }.map { it.second }
            val centers: List<Int> = ids.filter { it.first == "M" }.map { it.second }
            val forwards: List<Int> = ids.filter { it.first == "F" }.map { it.second }

            teamMembersG.referencedIds = gatekeepers.toIntArray()
            teamMembersD.referencedIds = defenders.toIntArray()
            teamMembersM.referencedIds = centers.toIntArray()
            teamMembersF.referencedIds = forwards.toIntArray()

        }
    }
}