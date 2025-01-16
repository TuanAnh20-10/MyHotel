package com.xinchaongaymoi.hotelbookingapp.components.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.xinchaongaymoi.hotelbookingapp.adapter.RoomAdapter
import com.xinchaongaymoi.hotelbookingapp.databinding.FragmentSearchResultBinding
import com.xinchaongaymoi.hotelbookingapp.R
class SearchResultFragment : Fragment() {

    private val viewModel :SearchViewModel by activityViewModels()
    private lateinit var binding:FragmentSearchResultBinding
    private lateinit var roomAdapter:RoomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentSearchResultBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setTabSort()
    }
    private fun setTabSort()
    {
        binding.filterTabs.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
               when(tab?.position){
                   0 -> viewModel.sortRooms(SearchViewModel.SortOrder.PRICE_LOW_TO_HIGH)
                   1 -> viewModel.sortRooms(SearchViewModel.SortOrder.PRICE_LOW_TO_HIGH)
                   2 -> viewModel.sortRooms(SearchViewModel.SortOrder.PRICE_HIGH_TO_LOW)
                   3 -> viewModel.sortRooms(SearchViewModel.SortOrder.RATING_HIGH_TO_LOW)
               }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    private fun setupRecyclerView()
    {
        roomAdapter = RoomAdapter()
        binding.recyclerViewRooms.apply {
            adapter=roomAdapter
            layoutManager =LinearLayoutManager(context)

        }
    }
    private fun observeViewModel(){
        viewModel.searchResults.observe(viewLifecycleOwner) { rooms ->
            roomAdapter.updateRooms(rooms)
        }
        viewModel.checkInDate.observe(viewLifecycleOwner) { checkIn ->
            viewModel.checkOutDate.value?.let { checkOut ->
                roomAdapter.setDates(checkIn, checkOut)
            }
        }

        viewModel.checkOutDate.observe(viewLifecycleOwner) { checkOut ->
            viewModel.checkInDate.value?.let { checkIn ->
                roomAdapter.setDates(checkIn, checkOut)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}