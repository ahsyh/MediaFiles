package com.bignerdranch.android.mediafiles.ui.recycleView;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

public class MediaFileAdapter extends PagedListAdapter<MediaFile, MediaFileViewHolder> {

    private static final String TAG = "LocalItemAdapter";

    public MediaFileAdapter() {
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
    public MediaFileViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return MediaFileViewHolder.createFromParent(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull final MediaFileViewHolder holder, final int position) {
        final @Nullable MediaFile item = getItem(position);

        if (item == null) {
            Log.w(TAG, "Null LocalItem at position: " + position);
            return;
        }

        holder.idView.setText(String.valueOf(item.getId()));
        holder.pathView.setText(item.getPath() != null ? item.getPath() : "null");
        holder.unifiedIdView.setText(String.valueOf(item.getMediaStoreId()));
        holder.addedView.setText(String.valueOf(item.getDateAdded()));
        holder.takenView.setText(String.valueOf(item.getDateAdded()));
        holder.modifiedView.setText(String.valueOf(item.getSize()));
    }
}