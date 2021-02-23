package com.example.mybikeshops.bikeshoplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mybikeshops.databinding.BikeShopListItemBinding

class BikeShopListAdapter(private val onClickListener: OnClickListener)
    : PagingDataAdapter<BikeShopItem, BikeShopListAdapter.BikeShopItemViewHolder>(BIKE_SHOP_COMPARATOR) {

    companion object {
        private val BIKE_SHOP_COMPARATOR = object : DiffUtil.ItemCallback<BikeShopItem>() {
            override fun areItemsTheSame(oldItem: BikeShopItem, newItem: BikeShopItem): Boolean {
                // Id is unique.
                return oldItem.address == newItem.address
            }

            override fun areContentsTheSame(oldItem: BikeShopItem, newItem: BikeShopItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class BikeShopItemViewHolder(private var binding: BikeShopListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bikeShopItem: BikeShopItem) {
            binding.bikeShop = bikeShopItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeShopItemViewHolder {
        return BikeShopItemViewHolder(BikeShopListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BikeShopItemViewHolder, position: Int) {
        getItem(position)?.let { bikeShop ->
            (holder as? BikeShopItemViewHolder)?.bind(bikeShopItem = bikeShop)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(bikeShop)
            }
        }
    }

    class OnClickListener(val clickListener: (bikeShop: BikeShopItem) -> Unit) {
        fun onClick(bikeShop: BikeShopItem) = clickListener(bikeShop)
    }
}