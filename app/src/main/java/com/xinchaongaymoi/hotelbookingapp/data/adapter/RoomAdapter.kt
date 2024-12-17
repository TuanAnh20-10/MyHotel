package com.xinchaongaymoi.hotelbookingapp.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xinchaongaymoi.hotelbookingapp.data.model.Room
import com.xinchaongaymoi.hotelbookingapp.R
import com.xinchaongaymoi.hotelbookingapp.RoomDetailActivity

class RoomAdapter(
    private val roomList: MutableList<Room>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomImage: ImageView = itemView.findViewById(R.id.roomImageItem)
        val roomName: TextView = itemView.findViewById(R.id.roomNameItem)
        val roomPrice: TextView = itemView.findViewById(R.id.roomPriceItem)
        val roomType: TextView = itemView.findViewById(R.id.roomTypeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_item_search, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.roomName.text = room.room_name
        holder.roomPrice.text = "Price per night: ${room.price_per_night}$"

        holder.roomType.text ="Type: ${room.room_type}"


        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(room.image)
            // Optional: placeholder image
            .into(holder.roomImage)

        // Sự kiện click để chuyển sang RoomDetailActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RoomDetailActivity::class.java)
            intent.putExtra("ROOM_ID", room.id) // Gửi roomId qua Intent
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = roomList.size
}