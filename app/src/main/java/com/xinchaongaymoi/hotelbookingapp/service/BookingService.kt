package com.xinchaongaymoi.hotelbookingapp.service

import com.google.firebase.database.FirebaseDatabase
import com.xinchaongaymoi.hotelbookingapp.model.Booking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class BookingService {
    private val database = FirebaseDatabase.getInstance().reference
    private val bookingsRef =database.child("Booking")

    fun createBooking(
        roomId:String
        ,userId:String,
        checkInDate:String,
        checkOutDate:String,
        totalPrice:Double,
        callback:(Boolean,String?)->Unit
    ){
        val bookingId = bookingsRef.push().key?:return
        val booking = Booking(
            id =bookingId,
            roomId = roomId,
            userId = userId,
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            status = "pending",
            createdAt =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
            totalPrice =totalPrice
        )
        bookingsRef.child(bookingId).setValue(booking)
            .addOnSuccessListener {
                callback(true,bookingId)
            }
            .addOnFailureListener{
                e->callback(false,e.message)
            }
    }
}