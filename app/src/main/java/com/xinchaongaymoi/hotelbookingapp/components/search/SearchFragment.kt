package com.xinchaongaymoi.hotelbookingapp.components.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xinchaongaymoi.hotelbookingapp.databinding.FragmentSearchBinding
import java.util.Calendar
import com.xinchaongaymoi.hotelbookingapp.R
import android.util.Log
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatePickers()
        setUpSearchBtn()
    }
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val date = String.format("%02d/%02d/%d", day, month + 1, year)
                editText.setText(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    private fun setupDatePickers() {
        binding.checkInDate.setOnClickListener { showDatePicker(binding.checkInDate) }
        binding.checkOutDate.setOnClickListener { showDatePicker(binding.checkOutDate) }
    }
    private fun setUpSearchBtn(){
        binding.btnSearch.setOnClickListener{
            val location =binding.locationInput.text.toString()
            val checkIn = binding.checkInDate.text.toString()
            val checkOut = binding.checkOutDate.text.toString()
            val maxPrice =binding.priceSeekBar.progress.toDouble()
            viewModel.searchRooms("ha noi","02/01/2025","03/01/2025",10.0)
//            findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}