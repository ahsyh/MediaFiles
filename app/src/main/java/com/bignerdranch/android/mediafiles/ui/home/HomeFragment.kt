package com.bignerdranch.android.mediafiles.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.databinding.FragmentHomeBinding
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapter: MediaFileAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = MediaFileAdapter(MediaFilesApplication.appComponent.getLogger())

        binding.bindAdapter(adapter)

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

        homeViewModel.text.observe(viewLifecycleOwner, Observer { s -> binding.textSummary.text = s })
        return binding.root
    }

    /**
     * Sets up the [RecyclerView] and binds [MediaFileAdapter] to it
     */
    private fun FragmentHomeBinding.bindAdapter(mediaFileAdapter: MediaFileAdapter) {
        mediaFilesRecyclerView.adapter = mediaFileAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        mediaFilesRecyclerView.layoutManager = layoutManager
        val decoration = DividerItemDecoration(mediaFilesRecyclerView.context, DividerItemDecoration.VERTICAL)
        mediaFilesRecyclerView.addItemDecoration(decoration)
    }

}