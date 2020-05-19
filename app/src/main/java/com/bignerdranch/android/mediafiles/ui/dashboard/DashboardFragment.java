package com.bignerdranch.android.mediafiles.ui.dashboard;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.mediafiles.R;
import com.bignerdranch.android.mediafiles.ui.recycleView.MediaFileImageAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private MediaFileImageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(), getSpanCountForLayoutMan());
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

    private int getSpanCountForLayoutMan() {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;

        float imageWidth = getResources().getDimension(R.dimen.item_image_width);

        int result = (int)(width/imageWidth);
        Log.v("ShiyihuiHLNSKQ","value: " + width + " " + height + " " + density + " " + densityDpi + " " + result);
        return result;
    }
}
