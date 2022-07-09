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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.activity.AddGasRecordActivity
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.ui.recycleView.FillGasAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel
    lateinit var adapter: FillGasAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        val localItemRecyclerView: RecyclerView = root.findViewById(R.id.gasFillRecyclerView)
        localItemRecyclerView.layoutManager = layoutManager
        adapter = FillGasAdapter(context, MediaFilesApplication.appComponent.getLogger())
        localItemRecyclerView.adapter = adapter
        //notificationsViewModel.liveFillGas.observe(viewLifecycleOwner, { pagedList: PagedList<FillGasEvent> -> adapter.submitList(pagedList) })
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationsViewModel.liveFillGas.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        val textView = root.findViewById<TextView>(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, { s -> textView.text = s })

        val button = root.findViewById<Button>(R.id.addGasRecordButton)
        button.setOnClickListener {
            Log.e(DTAG, "setOnClickListener")
            val intent = Intent(context, AddGasRecordActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}
