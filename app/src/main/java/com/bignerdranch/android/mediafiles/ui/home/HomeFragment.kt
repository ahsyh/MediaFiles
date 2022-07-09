package com.bignerdranch.android.mediafiles.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        adapter = MediaFileAdapter(MediaFilesApplication.appComponent.getLogger())
        localItemRecyclerView.adapter = adapter

        //homeViewModel.liveMediaFiles.observe(viewLifecycleOwner, Observer { pagedList: PagedList<MediaFile> -> adapter.submitList(pagedList) })
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.liveMediaFiles.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        homeViewModel.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}