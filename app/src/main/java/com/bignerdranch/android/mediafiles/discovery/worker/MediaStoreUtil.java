package com.bignerdranch.android.mediafiles.discovery.worker;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bignerdranch.android.mediafiles.discovery.model.MediaType;
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaStoreUtil {
    @NonNull private final ContentResolver contentResolver;


    public boolean isPathExist(
            @NonNull final MediaType mediaType,
            @NonNull final String path) {
        long count = 0;
//        String colName = "count";
//        String proj = "count(" + MediaStore.MediaColumns.DATA + ") AS " + colName;
        String proj = MediaStore.MediaColumns._ID;

        try(final Cursor cursor = contentResolver.query(
                    MediaUri.getUri(mediaType),
                    new String[] {proj},
                    MediaStore.MediaColumns.DATA + " = ?",
                    new String[] {path}, null)) {
            cursor.moveToNext();
//            int colIndex = cursor.getColumnIndexOrThrow(colName);
//            count = cursor.getLong(colIndex);
            count = cursor.getCount();
        } catch (Exception e) {
            Log.v("ShiyihuiHLNSKQ", "problem in isPathExist:" + e.getMessage());
        }
        return count > 0L;
    }

    @NonNull
    public List<MediaFile> fetchMediaFiles(
            @NonNull final MediaType mediaType,
            @NonNull final long offset,
            @NonNull final int limit
    ) {
        final List<MediaFile> result =  new ArrayList<>(limit);
        if (MediaUri.getUri(mediaType) == null) {
            return result;
        }

        String[] selectors = {
                MediaStore.MediaColumns._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE};

        String[] parameters = {
                "" + offset };

        Log.v("ShiyihuiHLNSKQ", "in fetchMediaFiles try to fetch " + limit + " items begin from " + offset);

        try (final Cursor cursor = contentResolver.query(
                MediaUri.getUri(mediaType), selectors, MediaStore.MediaColumns._ID + " > ?",
                parameters, MediaStore.MediaColumns._ID + " ASC" + ((limit > 0) ? " LIMIT " + limit : ""))) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                result.add(itemFromCursor(cursor));
            }
        } catch (Exception e) {
            Log.v("ShiyihuiHLNSKQ", "problem in fetchMediaFiles:" + e.getMessage());
        }

        return  result;
    }

    private MediaFile itemFromCursor(@NonNull final Cursor cursor) {
        int idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID);
        int dataCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int dateAddedCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
        int sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

        MediaFile item = new MediaFile();
        item.mediaStoreId = cursor.getLong(idCol);
        item.path = cursor.getString(dataCol);
        item.dateAdded = cursor.getLong(dateAddedCol);
        item.size = cursor.getLong(sizeCol);

        return item;
    }
}
