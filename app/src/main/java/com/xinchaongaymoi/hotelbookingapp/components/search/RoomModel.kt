package com.xinchaongaymoi.hotelbookingapp.components.search


data class Room(
    val id: String = "",
    val name: String = "",
    val price: Int = 0,
    val sale :Float,
    val rating :Float,
    val imageUrl: String = ""
)