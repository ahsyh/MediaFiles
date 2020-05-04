package com.bignerdranch.android.mediafiles.ui.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.mediafiles.R;

public class MediaFileViewHolder extends RecyclerView.ViewHolder {

    @NonNull public final TextView idView;
    @NonNull public final TextView pathView;
    @NonNull public final TextView unifiedIdView;
    @NonNull public final TextView addedView;
    @NonNull public final TextView takenView;
    @NonNull public final TextView modifiedView;

    private MediaFileViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.idView = itemView.findViewById(R.id.itemId);
        this.pathView = itemView.findViewById(R.id.itemPath);
        this.unifiedIdView = itemView.findViewById(R.id.unifiedId);
        this.addedView = itemView.findViewById(R.id.itemAdded);
        this.takenView = itemView.findViewById(R.id.itemTaken);
        this.modifiedView = itemView.findViewById(R.id.itemModified);
    }

    public static MediaFileViewHolder createFromParent(@NonNull final ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_file, parent, false);
        return new MediaFileViewHolder(view);
    }
}
