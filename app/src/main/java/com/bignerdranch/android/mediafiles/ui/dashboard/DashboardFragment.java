package com.bignerdranch.android.mediafiles.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.mediafiles.R;
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter;
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileImageAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private MediaFileImageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        final RecyclerView localItemRecyclerView = root.findViewById(R.id.mediaFilesImageRecyclerView);
        localItemRecyclerView.setLayoutManager(layoutManager);
        adapter = new MediaFileImageAdapter();
        localItemRecyclerView.setAdapter(adapter);

        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        dashboardViewModel.getLiveMediaFiles().observe(getViewLifecycleOwner(), adapter::submitList);
        return root;
    }
}
