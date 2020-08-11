package com.example.mygw.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore


class Utils {

    companion object{
        @SuppressLint("NewApi")
        fun getRealPathApi19Above(
            context: Context,
            uri: Uri
        ): String? {
            var filePath = ""
            try {
                val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

                // DocumentProvider
                if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(
                            uri
                        )
                    ) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split =
                            docId.split(":".toRegex()).toTypedArray()
                        val type = split[0]
                        if ("primary".equals(type, ignoreCase = true)) {
                            return Environment.getExternalStorageDirectory()
                                .toString() + "/" + split[1]
                        }

                        // TODO handle non-primary volumes
                    } else if (isDownloadsDocument(
                            uri
                        )
                    ) {
                        val id = DocumentsContract.getDocumentId(uri)
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id)
                        )
                        return getDataColumn(
                            context,
                            contentUri,
                            null,
                            null
                        )
                    } else if (isMediaDocument(
                            uri
                        )
                    ) {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split =
                            docId.split(":".toRegex()).toTypedArray()
                        val type = split[0]
                        var contentUri: Uri? = null
                        if ("image" == type) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        } else if ("video" == type) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        } else if ("audio" == type) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                        val selection = "_id=?"
                        val selectionArgs = arrayOf(
                            split[1]
                        )
                        return getDataColumn(
                            context,
                            contentUri,
                            selection,
                            selectionArgs
                        )
                    }
                } else if ("content".equals(uri.scheme, ignoreCase = true)) {

                    // Return the remote address
                    return if (isGooglePhotosUri(
                            uri
                        )
                    ) uri.lastPathSegment else getDataColumn(
                        context,
                        uri,
                        null,
                        null
                    )
                } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                    return uri.path
                }



            } catch (e: Exception) {
                filePath = ""
            }
            return filePath
        }


        fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(
                column
            )
            try {
                cursor = context.contentResolver.query(
                    uri!!, projection, selection, selectionArgs,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index: Int = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null) cursor.close()
            }
            return null
        }

        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

    }



}