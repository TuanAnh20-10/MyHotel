package com.xinchaongaymoi.hotelbookingapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xinchaongaymoi.hotelbookingapp.SearchActivity
import com.xinchaongaymoi.hotelbookingapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

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

    val root: View = binding.root

    homeViewModel.text.observe(viewLifecycleOwner) {

    }
    return root

  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}