package com.bignerdranch.android.mediafiles.discovery.worker

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri.getUri
import com.bignerdranch.android.mediafiles.util.log.Logger

class MediaStoreUtil (val contentResolver: ContentResolver, val logger: Logger) {

    fun isPathExist(
            mediaType: MediaType,
            path: String): Boolean {
        val uri: Uri = getUri(mediaType) ?: return false

        var count: Long = 0
        val proj = MediaStore.MediaColumns._ID
        try {
            contentResolver.query(
                    uri, arrayOf(proj),
                    MediaStore.MediaColumns.DATA + " = ?", arrayOf(path), null)?.use { cursor ->
                count = cursor.count.toLong()
            }
        } catch (e: Exception) {
            logger.v(DTAG, "problem in isPathExist:" + e.message)
        }
        return count > 0L
    }

    fun isPathsExist(
        mediaType: MediaType,
        paths: Collection<String>): List<Long> {

        val uri: Uri = getUri(mediaType) ?: return emptyList()

        if (paths.size <= 0) {
            return emptyList()
        }

        val proj = MediaStore.MediaColumns._ID
        val queryStr = MediaStore.MediaColumns.DATA +
                paths.map {"?"}.joinToString(prefix = " IN (", postfix = ")", separator = ",")
        val result: MutableList<Long> = ArrayList()

        contentResolver.query(
            uri, arrayOf(proj),
            queryStr, paths.toTypedArray(), null)?.use { cursor ->

            cursor.moveToFirst()

            while (!cursor.isAfterLast) {
                cursor.getLong(0).let {
                    result.add(it)
                }
                cursor.moveToNext()
            }
        }

        return result
    }

    fun fetchMediaFiles(
            mediaType: MediaType,
            offset: Int,
            limit: Int
    ): List<MediaFile> {
        val result: MutableList<MediaFile> = ArrayList(limit)
        val uri: Uri = getUri(mediaType) ?: return result

        val projections = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE)
        logger.v(DTAG, "in fetchMediaFiles try to fetch $limit items begin from $offset")
        try {
            val bundle = Bundle()
            bundle.putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
            bundle.putInt(ContentResolver.QUERY_ARG_LIMIT, limit)

            contentResolver.query(
                    uri, projections, bundle, null)?.use { cursor ->
                cursor.moveToFirst()

                logger.v(DTAG, "query get ${cursor.count} photos. moveToFirst")
                while (!cursor.isAfterLast) {
                    val item = itemFromCursor(cursor)
                    item.let {
                        logger.v(DTAG, "get item: ${it.path}, ${it.dateAdded}, ${it.dateTaken}, ${it.dateModify}")
                    }
                    result.add(item)
                    cursor.moveToNext()
                }
            }
        } catch (e: Exception) {
            logger.v(DTAG, "problem in fetchMediaFiles:" + e.message)
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