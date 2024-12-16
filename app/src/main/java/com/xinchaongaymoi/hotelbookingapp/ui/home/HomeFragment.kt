package com.xinchaongaymoi.hotelbookingapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.xinchaongaymoi.hotelbookingapp.SearchActivity
import com.xinchaongaymoi.hotelbookingapp.data.adapter.HomeRoomAdapter
import com.xinchaongaymoi.hotelbookingapp.data.service.RoomService

import com.xinchaongaymoi.hotelbookingapp.databinding.FragmentHomeBinding
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    private lateinit var  luxuryRoomRecyclerView:RecyclerView
    private lateinit var  royalRoomRecyclerView:RecyclerView
    private lateinit var luxuryAdapter: HomeRoomAdapter
    private lateinit var royalAdapter:HomeRoomAdapter
    private val roomService =RoomService()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
      val searchBtn :Button = binding.searchBtn
      val searchET :EditText = binding.searchET
      searchBtn.setOnClickListener{
          val intent =Intent(requireContext(),SearchActivity::class.java)
          intent.putExtra("keyWord",searchET.text.toString())
          startActivity(intent)
      }

      royalRoomRecyclerView= binding.recommendRV
      luxuryRoomRecyclerView =binding.bestRV
      royalRoomRecyclerView.layoutManager = LinearLayoutManager(requireContext())
      luxuryRoomRecyclerView.layoutManager=LinearLayoutManager(requireContext())
      getRooms()
    val root: View = binding.root

    homeViewModel.text.observe(viewLifecycleOwner) {

    }
    return root

  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getRooms(){
        Log.i("Hello","Hello")
        roomService.getRoomByType("Luxury",
            callback = {
                roomList->luxuryAdapter=HomeRoomAdapter(roomList)
                luxuryRoomRecyclerView.adapter=luxuryAdapter
            }
            )
        roomService.getRoomByType("Royal",
            callback = {
                    roomList->royalAdapter=HomeRoomAdapter(roomList)
                royalRoomRecyclerView.adapter=royalAdapter
            }
        )
    }
}
