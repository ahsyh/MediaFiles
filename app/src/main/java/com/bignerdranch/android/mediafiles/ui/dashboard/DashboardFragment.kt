package com.bignerdranch.android.mediafiles.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileImageAdapter

class DashboardFragment : Fragment() {
    lateinit var dashboardViewModel: DashboardViewModel
    lateinit var adapter: MediaFileImageAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.orientation = RecyclerView.VERTICAL
        val localItemRecyclerView: RecyclerView = root.findViewById(R.id.mediaFilesImageRecyclerView)
        localItemRecyclerView.layoutManager = layoutManager
        adapter = MediaFileImageAdapter()
        localItemRecyclerView.adapter = adapter
        val textView = root.findViewById<TextView>(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        dashboardViewModel.liveMediaFiles.observe(viewLifecycleOwner, Observer<PagedList<MediaFile>> { pagedList: PagedList<MediaFile> -> adapter.submitList(pagedList) })
        return root
    }
}