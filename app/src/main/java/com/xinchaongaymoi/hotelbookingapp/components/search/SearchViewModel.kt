package com.xinchaongaymoi.hotelbookingapp.components.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.Query
import com.xinchaongaymoi.hotelbookingapp.model.Room
import com.xinchaongaymoi.hotelbookingapp.service.RoomService
import android.util.Log
class SearchViewModel : ViewModel() {
    private val roomService = RoomService()
    
    private val _searchResults = MutableLiveData<List<Room>>()
    val searchResults: LiveData<List<Room>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchRooms(location: String, checkIn: String, checkOut: String, maxPrice: Double) {

        _isLoading.value = true
        roomService.searchRooms(location, checkIn, checkOut, maxPrice) { rooms ->
            Log.i("helloooo",rooms.toString())
            _searchResults.postValue(rooms)
            _isLoading.value = false
        }

    }
}