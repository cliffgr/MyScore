package com.cliff.myscore.ui.dashboard

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cliff.myscore.databinding.ItemCountiesBinding
import com.cliff.myscore.model.Country
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class CountriesAdapter(private val listener: (String) -> Unit) :
    ListAdapter<Country, CountriesAdapter.ViewHolder>(CountryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCountiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener("x") }
    }


    inner class ViewHolder(private val itemBinding: ItemCountiesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                // listener(getItem(layoutPosition), binding.mcvPoster, binding.tvTitle)
            }
        }

        fun bind(country: Country) {
            with(itemBinding) {
                countryName.text = country.name
              /*  Glide.with(countryImage)
                    .`as`(PictureDrawable::class.java)
                    .load()
                    .centerCrop()
                    .into()*/

                country.flag?.let {
                    GlideToVectorYou
                        .init()
                        .with(countryImage.context)
                        .load(Uri.parse(country.flag), countryImage);
                }
            }
        }
    }

}

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }

}