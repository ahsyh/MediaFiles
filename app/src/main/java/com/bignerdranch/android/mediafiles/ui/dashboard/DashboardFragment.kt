package com.bignerdranch.android.mediafiles.ui.dashboard

import android.util.DisplayMetrics;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileImageAdapter
import com.bignerdranch.android.mediafiles.util.log.Logger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    lateinit var dashboardViewModel: DashboardViewModel
    lateinit var adapter: MediaFileImageAdapter
    lateinit var logger: Logger

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger = MediaFilesApplication.appComponent.getLogger()
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val layoutManager = GridLayoutManager(context, 3)
        val layoutManager = GridLayoutManager(context, spanCountForLayoutMan)
        layoutManager.orientation = RecyclerView.VERTICAL
        val localItemRecyclerView: RecyclerView = root.findViewById(R.id.mediaFilesImageRecyclerView)
        localItemRecyclerView.layoutManager = layoutManager
        adapter = MediaFileImageAdapter(logger)
        localItemRecyclerView.adapter = adapter
        val textView = root.findViewById<TextView>(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, { s -> textView.text = s })

        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.liveMediaFiles.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        return root
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