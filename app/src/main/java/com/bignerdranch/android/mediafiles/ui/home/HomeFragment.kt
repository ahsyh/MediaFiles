package com.bignerdranch.android.mediafiles.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter

class HomeFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapter: MediaFileAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView = root.findViewById<TextView>(R.id.text_summary)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        val localItemRecyclerView: RecyclerView = root.findViewById(R.id.mediaFilesRecyclerView)
        localItemRecyclerView.layoutManager = layoutManager
        adapter = MediaFileAdapter()
        localItemRecyclerView.adapter = adapter
        homeViewModel.liveMediaFiles.observe(viewLifecycleOwner, Observer { pagedList: PagedList<MediaFile> -> adapter.submitList(pagedList) })
        homeViewModel.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}