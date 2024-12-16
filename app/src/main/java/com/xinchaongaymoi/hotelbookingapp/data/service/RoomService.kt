package com.xinchaongaymoi.hotelbookingapp.data.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xinchaongaymoi.hotelbookingapp.data.model.Room

class RoomService{
    fun searchRoomByUltilities(keyWord:String,callback:(MutableList<Room>)->Unit){
        val database =FirebaseDatabase.getInstance().reference
        database.child("room")
//            .orderByChild("room_name")
//            .startAt(keyWord)
//            .endAt(keyWord+"\uf8ff")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   val roomList = mutableListOf<Room>()
                    for(data in snapshot.children){
                        val room = data.getValue(Room::class.java)
                        room?.let { roomList.add(it) }
                    }
                    callback(roomList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }
}