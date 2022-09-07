package com.myapplication.api

import com.myapplication.models.Category
import com.myapplication.models.YogaList
import com.myapplication.models.yogadetailgallery.YogaDetailGallery
import com.myapplication.models.yogadetailvideo.YogaDetailVideo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface YogaAPI {

    @GET("v1/yogatypes/{token}")
    fun getCategories(
        @Path("token")
        token: String
    ): Call<Category>

    @GET("v1/yogalist/{yogaId}/{token}")
    fun getYogaList(
        @Path("yogaId")
        yogaId: String,
        @Path("token")
        token: String
    ): Call<YogaList>

    @GET("v1/yogadetails/{yogaDetailId}/{token}")
    fun getYogaDetailGallery(
        @Path("yogaDetailId")
        yogaId: String,
        @Path("token")
        token: String
    ): Call<YogaDetailGallery>

    @GET("v1/yogadetails/{yogaDetailId}/{token}")
    fun getYogaDetailVideo(
        @Path("yogaDetailId")
        yogaId: String,
        @Path("token")
        token: String
    ): Call<YogaDetailVideo>

}