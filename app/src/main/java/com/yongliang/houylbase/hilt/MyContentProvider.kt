package com.yongliang.houylbase.hilt

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

/**
 *
 * created by houyl
 * on  5:30 PM
 */
class MyContentProvider: ContentProvider() {
//    @EntryPoint
//    @InstallIn(ApplicationComponent::class)
//    interface MyEntryPoint {
//        fun getRetrofit(): Retrofit
//    }

    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

//    override fun query(
//        uri: Uri,
//        projection: Array<out String>?,
//        selection: String?,
//        selectionArgs: Array<out String>?,
//        sortOrder: String?
//    ): Cursor? {
////        context?.let {
////            val appContext = it.applicationContext
////            val entryPoint = EntryPointAccessors.fromApplication(appContext, MyEntryPoint::class.java)
////            val retrofit = entryPoint.getRetrofit()
////        }
//    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}