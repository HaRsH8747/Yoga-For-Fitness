package com.myapplication.utils

import com.myapplication.models.*
import com.myapplication.models.yogadetailgallery.YogaDetailGallery
import com.myapplication.models.yogadetailvideo.YogaDetailVideo

class Utils {
    companion object{
        const val BASE_URL = "https://invisionicl.com/mobileapp/wp-json/yogaforfitness/"
        const val BEGINNERS = "Beginners"
        const val INTERMEDIATE = "Intermediate"
        const val token = "xaZPmP8Fu9hdbhHasB"
        lateinit var categoryResponse: Category
        lateinit var yogaResponse: YogaList
        var allYogaList = mutableListOf<YogaListId>()
        var yogaList = mutableListOf<YogaListX>()
//        var intermediateList = mutableListOf<YogaListId>()
        var favouritesList = mutableListOf<YogaListX>()
//        var allYogaList = mutableListOf<YogaListId>()
        var yogaSearchList = mutableListOf<YogaListX>()
        lateinit var yogaDetailGallery: YogaDetailGallery
        lateinit var yogaDetailVideo: YogaDetailVideo
        var commonFragList = mutableListOf<CommonFragId>()
        var currentYogaId = 0
    }
}