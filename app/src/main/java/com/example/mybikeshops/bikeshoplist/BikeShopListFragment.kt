package com.example.mybikeshops.bikeshoplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mybikeshops.R
import com.example.mybikeshops.databinding.FragmentBikeShopListBinding

class BikeShopListFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentBikeShopListBinding
    private val viewModel: BikeShopListViewModel by lazy {
        ViewModelProvider(this).get(BikeShopListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBikeShopListBinding.inflate(inflater)

        // Allows Data Binding to observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the view model
        binding.viewModel = viewModel

        // set recyclerview adapter with item click listener
        binding.resultsList.adapter = BikeShopListAdapter(
            BikeShopListAdapter.OnClickListener { viewModel.displayBikeShopDetails(it) }
        )

        val spinner = binding.spinner

        // Create an ArrayAdapter using the string array and a default spinner layout
        activity?.let {
            ArrayAdapter.createFromResource(it, R.array.locations_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinner.adapter = adapter
            }
            spinner.onItemSelectedListener = this
        }

        // observe when a bike shop is selected to navigate to the detail fragment
        viewModel.selectedBikeShop.observe(viewLifecycleOwner, {
            if (null != it) {
                // set current bike shop in shared view model to bike shop that was clicked on
                viewModel.setCurrentBikeShop(it)

                val bikeShopItem = it
                val action = BikeShopListFragmentDirections.actionToBikeShopDetailFragment(bikeShopItem)

                this.findNavController().navigate(
                    action
                )
                viewModel.displayBikeShopDetailsComplete()
            }
        })

        viewModel.bikeShops.value?.let {
            (binding.resultsList.adapter as BikeShopListAdapter).setData(it)
        }
        return binding.root
    }

    // Spinner implementation
    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        val latLonValues = resources.getStringArray(R.array.locations_values)
        val locationLatLon: String = latLonValues[position]

        // don't fetch results if new spinner value is same as previous value
        if (viewModel.locationChanged(locationLatLon)) {
                viewModel.setLocation(locationLatLon)
                viewModel.getBikeShops()
                viewModel.bikeShops.value?.let {
                    (binding.resultsList.adapter as BikeShopListAdapter).setData(it)
                }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.clearResults()
    }
}