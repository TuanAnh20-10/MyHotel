package com.xinchaongaymoi.hotelbookingapp.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xinchaongaymoi.hotelbookingapp.model.Room
import android.util.Log

class RoomService {
    private val database = FirebaseDatabase.getInstance().reference
    private val roomsRef = database.child("rooms")
    private val bookingsRef = database.child("Booking")
    fun searchRooms(
        location: String,
        checkIn: String,
        checkOut: String,
        maxPrice: Double,
        callback: (List<Room>) -> Unit
    )
    {
        roomsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allRooms = mutableListOf<Room>()
                for (data in snapshot.children) {
                    val room = data.getValue(Room::class.java)
                    room?.let { allRooms.add(it) }
                }
                Log.i("hellpppppp","sossssssss")
                val filteredRooms = allRooms.filter { room ->
                    room.location.equals(location, ignoreCase = true) &&
                            room.pricePerNight <= maxPrice
                }
                // Thêm dòng này để trả về kết quả qua callback
                callback(filteredRooms)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RoomService", "Error fetching rooms", error.toException())
                callback(emptyList())
            }
        })
    }


//    private fun checkAvailableRooms(
//        rooms: List<Room>,
//        checkIn: String,
//        checkOut: String,
//        callback: (List<Room>) -> Unit)
//    {
//        bookingsRef
//            .orderByChild("status")
//            .equalTo("CONFIRMED")
//            .addListenerForSingleValueEvent(object : ValueEventListener
//            {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val checkInDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(checkIn)
//                    val checkOutDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(checkOut)
//                    val bookedRoomIds = mutableSetOf<String>()
//                    snapshot.children.forEach()
//                    {
//                        bookingSnapShot ->
//                         val booking= bookingSnapShot.getValue(Booking::class.java)
//                         booking?.let {
//                             val bookingCheckIn = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//                                 .parse(it.checkInDate)
//                             val bookingCheckOut = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//                                 .parse(it.checkOutDate)
//                             if (!(checkOutDate?.before(bookingCheckIn) == true ||
//                                         checkInDate?.after(bookingCheckOut) == true)) {
//                                 bookedRoomIds.add(it.roomId)
//                             }
//                         }
//
//                        }
//                    val availableRooms = rooms.filter { room ->
//                        !bookedRoomIds.contains(room.id)
//                    }
//                    callback(availableRooms)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.e("RoomService", "Error checking bookings", error.toException())
//                    callback(emptyList())
//                }
//
//            })
//
//    }
}