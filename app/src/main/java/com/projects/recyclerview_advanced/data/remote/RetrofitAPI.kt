package com.projects.recyclerview_advanced.data.remote

import com.projects.recyclerview_advanced.data.entities.CharacterList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitAPI {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofitKinopoisk = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
        )
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val search: RickAndMortyAPI = retrofitKinopoisk.create(RickAndMortyAPI::class.java)

}

interface RickAndMortyAPI {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): CharacterList


}