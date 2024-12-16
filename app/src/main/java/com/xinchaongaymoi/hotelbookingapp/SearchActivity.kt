package com.xinchaongaymoi.hotelbookingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xinchaongaymoi.hotelbookingapp.data.adapter.RoomAdapter
import com.xinchaongaymoi.hotelbookingapp.data.service.RoomService
import android.util.Log
class SearchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter:RoomAdapter
    private val roomService = RoomService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSearch)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val keyWord = intent.getStringExtra("keyWord")?:""
        recyclerView = findViewById(R.id.roomRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchRoomByUltilities(keyWord)

    }
    private fun searchRoomByUltilities(keyWord:String){

        Log.i("keyyyyyyy",keyWord)

        roomService.searchRoomByUltilities(keyWord,
            callback = {
                roomList->roomAdapter=RoomAdapter(roomList)
                recyclerView.adapter=roomAdapter
            })
    }
}