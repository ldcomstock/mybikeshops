package com.example.mybikeshops.bikeshoplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mybikeshops.Injection
import com.example.mybikeshops.R
import com.example.mybikeshops.databinding.FragmentBikeShopListBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BikeShopListFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentBikeShopListBinding
    private lateinit var viewModel: BikeShopListViewModel
    private val adapter = BikeShopListAdapter(
        BikeShopListAdapter.OnClickListener {
            viewModel.displayBikeShopDetails(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBikeShopListBinding.inflate(inflater)

        // Allows Data Binding to observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(BikeShopListViewModel::class.java)

        // Giving the binding access to the view model
        binding.viewModel = viewModel

        initSpinner()
        initAdapter()

        return binding.root
    }

    private fun initAdapter() {
        binding.resultsList.adapter = adapter

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.resultsList.addItemDecoration(decoration)

        // observe when a bike shop item is selected then navigate to the detail fragment
        viewModel.selectedBikeShop.observe(viewLifecycleOwner, {
            if (null != it) {
                val bikeShopItem = it
                val action =
                    BikeShopListFragmentDirections.actionToBikeShopDetailFragment(bikeShopItem)
                this.findNavController().navigate(action)
                viewModel.displayBikeShopDetailsComplete()
            }
        })
    }

    // Note: the system maintains spinner value on configuration change so we don't have to
    private fun initSpinner() {
        val spinner = binding.spinner

        // create an ArrayAdapter using the string array and a default spinner layout
        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.locations_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                    // set which layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // apply the adapter to the spinner
                    spinner.adapter = adapter
                }
            spinner.onItemSelectedListener = this
        }
    }

    // TODO move logic to data binding binding adapter
    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.resultsList.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.resultsList.visibility = View.VISIBLE
        }
    }

    private fun updateBikeShopListFromSpinnerSelection() {
        viewModel.currentLocation?.let { queryLocation ->
            if (queryLocation.isNotEmpty()) {
                binding.resultsList.scrollToPosition(0)
                searchBikeShops()
            }
        }
    }

    /**
     * Invokes a coroutine which returns a flow of paged data of BikeShopItems
     * which will in turn be submitted to the adapter. If a search is already in progress,
     * the search job is canceled before starting a new search
     */
    private var searchJob: Job? = null
    private fun searchBikeShops() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel
                .fetchNearbyBikeShops()
                .distinctUntilChanged()
                .collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
        }
    }

    // Spinner OnItemSelectedListener implementation
    // Note: onItemSelected callback is not triggered when user selects same spinner value again
    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val latLonValues = resources.getStringArray(R.array.locations_values)
        val locationLatLon: String = latLonValues[position]
        viewModel.setLocation(locationLatLon)
        updateBikeShopListFromSpinnerSelection()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // nothing to do here since adapter directly consumes the paged data flow
    }
}