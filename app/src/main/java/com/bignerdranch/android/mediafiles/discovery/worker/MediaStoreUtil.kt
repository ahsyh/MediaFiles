package com.bignerdranch.android.mediafiles.discovery.worker

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri.getUri
import com.bignerdranch.android.mediafiles.util.log.Logger
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class MediaStoreUtil (val contentResolver: ContentResolver, val logger: Logger) {

    fun isPathExist(
            mediaType: MediaType,
            path: String): Boolean {
        val uri: Uri = getUri(mediaType) ?: return false

        var count: Long = 0
//        String colName = "count";
//        String proj = "count(" + MediaStore.MediaColumns.DATA + ") AS " + colName;
        val proj = MediaStore.MediaColumns._ID
        try {
            contentResolver.query(
                    uri, arrayOf(proj),
                    MediaStore.MediaColumns.DATA + " = ?", arrayOf(path), null)?.use { cursor ->
                //            cursor.moveToNext()
                //            int colIndex = cursor.getColumnIndexOrThrow(colName);
                //            count = cursor.getLong(colIndex);
                count = cursor.count.toLong()
            }
        } catch (e: Exception) {
            logger.v("ShiyihuiHLNSKQ", "problem in isPathExist:" + e.message)
        }
        return count > 0L
    }

    fun fetchMediaFiles(
            mediaType: MediaType,
            offset: Long,
            limit: Int
    ): List<MediaFile> {
        val result: MutableList<MediaFile> = ArrayList(limit)
        val uri: Uri = getUri(mediaType) ?: return result

        val selectors = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE)
        val parameters = arrayOf("" + offset)
        logger.v("ShiyihuiHLNSKQ", "in fetchMediaFiles try to fetch $limit items begin from $offset")
        try {
            val sortOrderStr = MediaStore.MediaColumns._ID  + " ASC" + if (limit > 0) " LIMIT $limit" else ""
            val selection = MediaStore.MediaColumns._ID + " > ?"
            logger.v("ShiyihuiHLNSKQ", "query string, uri: ${uri.toString()}, selection: $selection, sortOrder: $sortOrderStr")
            contentResolver.query(
                    uri, selectors, selection, parameters, sortOrderStr)?.use { cursor ->
                cursor.moveToFirst()

                logger.v("ShiyihuiHLNSKQ", "query get ${cursor.count} photos. moveToFirst")
                while (!cursor.isAfterLast) {
                    val item = itemFromCursor(cursor)
                    item.let {
                        logger.v("ShiyihuiHLNSKQ", "get item: ${it.path}, ${it.dateAdded}, ${it.dateTaken}, ${it.dateModify}")
                    }
                    result.add(item)
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            logger.v("ShiyihuiHLNSKQ", "problem in fetchMediaFiles:" + e.message)
        }
        return result
    }

    private fun itemFromCursor(cursor: Cursor): MediaFile {
        val idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
        val dataCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val dateAddedCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
        val dateTakenCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
        val dateModifyCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
        val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
        val item = MediaFile()
        item.mediaStoreId = cursor.getLong(idCol)
        item.path = cursor.getString(dataCol)
        item.dateAdded = cursor.getLong(dateAddedCol)
        item.dateTaken = cursor.getLong(dateTakenCol)
        item.dateModify = cursor.getLong(dateModifyCol)
        item.size = cursor.getLong(sizeCol)
        return item
    }
}