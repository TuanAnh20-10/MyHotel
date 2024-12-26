package com.xinchaongaymoi.hotelbookingapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xinchaongaymoi.hotelbookingapp.adapter.RoomAdapter
import com.xinchaongaymoi.hotelbookingapp.service.RoomService
import android.util.Log
import com.xinchaongaymoi.hotelbookingapp.R

class SearchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter: RoomAdapter
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
        searchRoomByUtilities(keyWord)
    }

    private fun searchRoomByUtilities(keyWord: String) {
        roomService.searchRoomByUtilities(keyWord) { roomList ->
            if (roomList.isNotEmpty()) {
                // Tạo adapter và gán danh sách phòng vào RecyclerView
                roomAdapter = RoomAdapter(roomList) { roomId ->
                    // Xử lý khi click vào một phòng, chuyển đến trang chi tiết
                    navigateToRoomDetail(roomId)
                }
                recyclerView.adapter = roomAdapter
            } else {
                Log.w("SearchActivity", "No rooms found for keyword: $keyWord")
            }
        }
    }

    /**
     * Hàm điều hướng sang RoomDetailActivity
     */
    private fun navigateToRoomDetail(roomId: String) {
        val intent = Intent(this, RoomDetailActivity::class.java)
        intent.putExtra("ROOM_ID", roomId) // Truyền ID phòng sang RoomDetailActivity
        startActivity(intent)
    }
}