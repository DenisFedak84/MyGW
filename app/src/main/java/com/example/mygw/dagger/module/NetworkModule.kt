package com.example.mygw.dagger.module

import android.content.Context
import androidx.room.Room
import com.example.mygw.db.AppDatabase
import com.example.mygw.db.NotesDao
import com.example.mygw.network.NotesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
class NetworkModule {

    @Provides
    internal fun providePostApi(retrofit: Retrofit): NotesApi {
        return retrofit.create(NotesApi::class.java)
    }

    @Provides
    internal fun provideRetrofitInterface(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttp())
            .build()
    }

    fun createOkHttp(): OkHttpClient {
        val okhttp = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okhttp.addInterceptor(loggingInterceptor)
        return okhttp.build()
    }

    @Provides
    internal fun provideNotesDao(context: Context): NotesDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, "notes").build().notesDao()
    }

}