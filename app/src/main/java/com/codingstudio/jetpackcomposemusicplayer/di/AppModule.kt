package com.codingstudio.jetpackcomposemusicplayer.di

import android.content.Context
import com.codingstudio.jetpackcomposemusicplayer.data.remote.SongAPI
import com.codingstudio.jetpackcomposemusicplayer.data.repository.MusicRepositoryImpl
import com.codingstudio.jetpackcomposemusicplayer.data.service.MusicControllerImpl
import com.codingstudio.jetpackcomposemusicplayer.domain.repository.MusicRepository
import com.codingstudio.jetpackcomposemusicplayer.domain.service.MusicController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSongAPI() : SongAPI {
        return Retrofit.Builder()
            .baseUrl("https://cms.samespace.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideMusicRepository(api: SongAPI) : MusicRepository {
        return MusicRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideMusicController(@ApplicationContext context: Context): MusicController =
        MusicControllerImpl(context)


}