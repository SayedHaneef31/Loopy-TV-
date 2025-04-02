package com.example.loopytvlauncher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.leanback.widget.VerticalGridView
//import com.example.loopytvlauncher.data.AppInfo

class HomeFragment : Fragment() {
    private lateinit var appsGrid: VerticalGridView
    //private val appsList = mutableListOf<AppInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appsGrid = view.findViewById(R.id.apps_grid)
        // We'll set up the grid adapter in the next step
    }
}