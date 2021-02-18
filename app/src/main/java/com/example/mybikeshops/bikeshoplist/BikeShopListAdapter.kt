package com.example.mybikeshops.bikeshoplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybikeshops.databinding.BikeShopListItemBinding

class BikeShopListAdapter(
    private val onClickListener: OnClickListener
    ) : RecyclerView.Adapter<BikeShopListAdapter.BikeShopItemViewHolder>() {

    class BikeShopItemViewHolder(private var binding: BikeShopListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bikeShopItem: BikeShopItem) {
            binding.bikeShop = bikeShopItem
            binding.executePendingBindings()
        }
    }

    private var bikeShops = emptyList<BikeShopItem>()

    fun setData(newBikeShops: List<BikeShopItem>) {
        bikeShops = newBikeShops
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeShopItemViewHolder {
        return BikeShopItemViewHolder(BikeShopListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BikeShopItemViewHolder, position: Int) {
        val bikeShop = bikeShops[position]
        holder.bind(bikeShop)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(bikeShop)
        }
    }

    override fun getItemCount(): Int {
        return bikeShops.size
    }

    class OnClickListener(val clickListener: (bikeShop: BikeShopItem) -> Unit) {
        fun onClick(bikeShop: BikeShopItem) = clickListener(bikeShop)
    }
}