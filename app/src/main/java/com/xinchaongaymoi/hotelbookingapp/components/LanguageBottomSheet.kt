package com.xinchaongaymoi.hotelbookingapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xinchaongaymoi.hotelbookingapp.R
import com.xinchaongaymoi.hotelbookingapp.adapter.LanguageAdapter

class LanguageBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View?
    {
        val view = inflater.inflate(R.layout.language_bottom_sheet_content, container, false)
        return view
    }
    //val languageRecyclerView = view.findViewById<RecyclerView>(R.id.language_recycler_view)
    val languages = listOf("English", "French", "Vietnamese")
    val languageAdapter = LanguageAdapter(languages) { language ->
        // Handle language selected
    }
    companion object {
        const val TAG = "ModalBottomSheet"
    }
}