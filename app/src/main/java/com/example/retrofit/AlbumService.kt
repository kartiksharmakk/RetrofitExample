package com.example.retrofit

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums():Response<Albums>

    @GET("/albums")
    suspend fun getAlbums(@Query("userId")userid:Int):Response<Albums>

    @GET("/albums/{id}")
    suspend fun getIdAlbums(@Path(value = "id")id:Int):Response<AlbumsItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumsItem): Response<AlbumsItem>
}