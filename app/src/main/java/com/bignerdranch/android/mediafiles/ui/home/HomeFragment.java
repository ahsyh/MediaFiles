package com.bignerdranch.android.mediafiles.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.mediafiles.R;
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MediaFileAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_summary);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        final RecyclerView localItemRecyclerView = root.findViewById(R.id.mediaFilesRecyclerView);
        localItemRecyclerView.setLayoutManager(layoutManager);
        adapter = new MediaFileAdapter();
        localItemRecyclerView.setAdapter(adapter);

        homeViewModel.getLiveMediaFiles().observe(getViewLifecycleOwner(), adapter::submitList);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}
