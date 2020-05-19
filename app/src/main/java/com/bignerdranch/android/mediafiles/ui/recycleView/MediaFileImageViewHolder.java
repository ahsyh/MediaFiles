package com.bignerdranch.android.mediafiles.ui.recycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.mediafiles.R;

public class MediaFileImageViewHolder extends RecyclerView.ViewHolder {
    @NonNull public final ImageView imageView;
    @NonNull public final TextView textView;

    private MediaFileImageViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.grid_item_image_view);
        this.textView = itemView.findViewById(R.id.item_name);
    }

    public static MediaFileImageViewHolder createFromParent(@NonNull final ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_file_image, parent, false);
        return new MediaFileImageViewHolder(view);
    }
}
