package com.bignerdranch.android.mediafiles.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.activity.AddGasRecordActivity
import com.bignerdranch.android.mediafiles.databinding.FragmentHomeBinding
import com.bignerdranch.android.mediafiles.databinding.FragmentNotificationsBinding
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.ui.recycleView.FillGasAdapter
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel
    lateinit var adapter: FillGasAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val binding = FragmentNotificationsBinding.inflate(layoutInflater)

        adapter = FillGasAdapter(context, MediaFilesApplication.appComponent.getLogger())
        binding.bindAdapter(adapter)

        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationsViewModel.liveFillGas.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        notificationsViewModel.text.observe(viewLifecycleOwner, { s -> binding.textNotifications.text = s })

        binding.addGasRecordButton.setOnClickListener {
            Log.e(DTAG, "setOnClickListener")
            val intent = Intent(context, AddGasRecordActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    /**
     * Sets up the [RecyclerView] and binds [MediaFileAdapter] to it
     */
    private fun FragmentNotificationsBinding.bindAdapter(fillGasAdapter: FillGasAdapter) {
        gasFillRecyclerView.adapter = fillGasAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        gasFillRecyclerView.layoutManager = layoutManager
        val decoration = DividerItemDecoration(gasFillRecyclerView.context, DividerItemDecoration.VERTICAL)
        gasFillRecyclerView.addItemDecoration(decoration)
    }
}
