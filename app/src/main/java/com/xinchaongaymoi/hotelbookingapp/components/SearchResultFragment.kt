package com.xinchaongaymoi.hotelbookingapp.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xinchaongaymoi.hotelbookingapp.adapter.RoomAdapter
import com.xinchaongaymoi.hotelbookingapp.components.search.SearchViewModel
import com.xinchaongaymoi.hotelbookingapp.databinding.FragmentSearchResultBinding

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
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}