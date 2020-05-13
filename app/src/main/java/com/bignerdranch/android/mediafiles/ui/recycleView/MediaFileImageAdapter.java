package com.bignerdranch.android.mediafiles.ui.recycleView;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bumptech.glide.Glide;

import java.io.File;

public class MediaFileImageAdapter extends PagedListAdapter<MediaFile, MediaFileImageViewHolder> {
    private static final String TAG = "LocalItemAdapter";

    public MediaFileImageAdapter() {
        super(new DiffUtil.ItemCallback<MediaFile>() {
            @Override
            public boolean areItemsTheSame(@NonNull final MediaFile oldItem, @NonNull final MediaFile newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull final MediaFile oldItem, @NonNull final MediaFile newItem) {
                return oldItem.equals(newItem);

            }
        });
    }

    @NonNull
    @Override
    public MediaFileImageViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return MediaFileImageViewHolder.createFromParent(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final MediaFileImageViewHolder holder, final int position) {
        final @Nullable MediaFile item = getItem(position);

        if (item == null) {
            Log.w(TAG, "Null LocalItem at position: " + position);
            return;
        }

        final String path;

        if (item.getPath() != null) {
            path = item.getPath();
            holder.textView.setText("image");
            Glide.with(holder.context).load(path).into(holder.imageView);
        } else {
            Log.v("ShiyihuiHLNSKQ", "Load file path null.");
        }
    }
}
