package com.xinchaongaymoi.hotelbookingapp.data.model

data class Room(
    val room_name:String="",
    val image:String="",
    val room_type:String="",
    val location:String="",
    val area:Int=0      ,
    val bed_type:String="",
    val total_bed:Int=0,
    val status:String="",
    val max_guests:Int=0,
    val price_per_night:Int=0,
    val price_per_hour:Int=0,
    val ultilities: String="",
    val sale:Int=0,
    val rating:Double=0.0

)
