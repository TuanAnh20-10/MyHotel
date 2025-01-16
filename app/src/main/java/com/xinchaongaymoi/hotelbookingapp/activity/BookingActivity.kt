package com.xinchaongaymoi.hotelbookingapp.activity
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xinchaongaymoi.hotelbookingapp.databinding.ActivityBookingBinding
import com.xinchaongaymoi.hotelbookingapp.model.Room
import com.xinchaongaymoi.hotelbookingapp.service.BookingService
import com.xinchaongaymoi.hotelbookingapp.service.RoomService
import java.text.SimpleDateFormat
import java.util.Locale

class BookingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var bookingService: BookingService
    private lateinit var roomService: RoomService
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookingService  = BookingService()
        roomService = RoomService()
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val roomId = intent.getStringExtra("ROOM_ID") ?: return
        val checkIn = intent.getStringExtra("CHECK_IN") ?: return
        val checkOut = intent.getStringExtra("CHECK_OUT") ?: return
        roomService.getRoomById(roomId){
            room->
            if(room!=null){
                setRoomInfo(room)
                setBookingDetails(room,checkIn,checkOut)
            }
        }
        binding.btnConfirmBooking.setOnClickListener {
            handleBooking(roomId, checkIn, checkOut)
        }
    }
    private fun setRoomInfo(room: Room){
        binding.apply {
            tvRoomName.text = room.roomName
            tvRoomType.text =room.roomType
            tvLocation.text = room.location
            ratingBar.rating = room.rating.toFloat()
            tvRating.text = String.format("%.1f", room.rating)

        }
    }
    private fun handleBooking(roomId: String, checkIn: String, checkOut: String) {
        val userId = sharedPreferences.getString("id", null)
        if (userId == null) {
            Toast.makeText(this, "Please login before booking", Toast.LENGTH_SHORT).show()
            return
        }

        // Hiển thị dialog xác nhận
        MaterialAlertDialogBuilder(this)
            .setTitle("Booking confirmation")
            .setMessage("Are you sure you want to book this room?")
            .setPositiveButton("Cofirm") { _, _ ->
                val totalPrice = binding.tvTotalPrice.text.toString().replace("$", "").toDouble()

                bookingService.createBooking(
                    roomId = roomId,
                    userId = userId,
                    checkInDate = checkIn,
                    checkOutDate = checkOut,
                    totalPrice = totalPrice
                ) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Booking succesfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun setBookingDetails(room: Room,checkIn:String,checkOut:String){
        binding.apply {
            tvCheckIn.text = "Check In: ${checkIn}"
            tvCheckOut.text = "Check Out: ${checkOut}"
            val nights = calculateNights(checkIn, checkOut)
            tvNights.text = "Nights: $nights "
            val pricePerNight = if (room.sale > 0) {
              room.sale
            } else {
                room.pricePerNight
            }

            tvPricePerNight.text = "${String.format("%.2f", pricePerNight)}$"
            tvNumberOfNights.text = "$nights night"
            val totalPrice = pricePerNight * nights
            tvTotalPrice.text = "${String.format("%.2f", totalPrice)}$"
        }
    }
    private fun calculateNights(checkIn: String, checkOut: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val checkInDate = dateFormat.parse(checkIn)
        val checkOutDate = dateFormat.parse(checkOut)
        return ((checkOutDate?.time ?: 0) - (checkInDate?.time ?: 0)) / (1000 * 60 * 60 * 24).toInt()
    }
}