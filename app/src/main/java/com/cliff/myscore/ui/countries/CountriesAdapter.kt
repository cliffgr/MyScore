package com.cliff.myscore.ui.countries

import android.net.Uri
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

    // var checkBoxesPositions: SparseBooleanArray = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCountiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(getItem(position).code) }
    }


    inner class ViewHolder(private val itemBinding: ItemCountiesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
           /* itemBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                // checkBoxesPositions.put(layoutPosition, isChecked)
            }*/
        }

        fun bind(country: Country) {
            with(itemBinding) {
                countryName.text = country.name
                country.flag?.let {
                    GlideToVectorYou
                        .init()
                        .with(countryImage.context)
                        .load(Uri.parse(country.flag), countryImage)
                }
                //  checkBox.isChecked = checkBoxesPositions[layoutPosition, false]
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