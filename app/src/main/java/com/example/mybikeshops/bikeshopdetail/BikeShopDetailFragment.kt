package com.example.mybikeshops.bikeshopdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mybikeshops.databinding.FragmentBikeShopDetailBinding

class BikeShopDetailFragment : Fragment() {

    private val args: BikeShopDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentBikeShopDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.bikeShopItem = args.bikeShopItem

        return binding.root
    }
}