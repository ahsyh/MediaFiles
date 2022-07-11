package com.bignerdranch.android.mediafiles.ui.images

import android.util.DisplayMetrics;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.databinding.FragmentDashboardBinding
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileImageAdapter
import com.bignerdranch.android.mediafiles.util.log.Logger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ImagesFragment : Fragment() {
    lateinit var imagesViewModel: ImagesViewModel
    lateinit var adapter: MediaFileImageAdapter
    lateinit var logger: Logger

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger = MediaFilesApplication.appComponent.getLogger()
        imagesViewModel = ViewModelProvider(this).get(ImagesViewModel::class.java)
        adapter = MediaFileImageAdapter(logger)

        val binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.bindAdapter(adapter)

        imagesViewModel.text.observe(viewLifecycleOwner, { s -> binding.textDashboard.text = s })

        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                imagesViewModel.liveMediaFiles.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        return binding.root
    }

    /**
     * Sets up the [RecyclerView] and binds [MediaFileAdapter] to it
     */
    private fun FragmentDashboardBinding.bindAdapter(adapter: MediaFileImageAdapter) {
        mediaFilesImageRecyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(context, spanCountForLayoutMan)
        layoutManager.orientation = RecyclerView.VERTICAL
        mediaFilesImageRecyclerView.layoutManager = layoutManager
        val decoration = DividerItemDecoration(mediaFilesImageRecyclerView.context, DividerItemDecoration.VERTICAL)
        mediaFilesImageRecyclerView.addItemDecoration(decoration)
    }

    private val spanCountForLayoutMan: Int
        get() {
            val metric = DisplayMetrics()
            var result = 3

            activity?.let {
                it.windowManager.defaultDisplay.getMetrics(metric)
                val width = metric.widthPixels
                val height = metric.heightPixels
                val density = metric.density
                val densityDpi = metric.densityDpi
                val imageWidth = resources.getDimension(R.dimen.item_image_width)
                result = (width / imageWidth).toInt()
                logger.v(DTAG, "value: $width $height $density $densityDpi $result")
            }

            return result
        }
}