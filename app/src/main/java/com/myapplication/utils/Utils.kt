package com.myapplication.utils

import com.myapplication.models.Category
import com.myapplication.models.YogaList
import com.myapplication.models.YogaListX
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
        var beginnersList = mutableListOf<YogaListX>()
        var intermediateList = mutableListOf<YogaListX>()
        var allYogaList = mutableListOf<YogaListX>()
        var yogaSearchList = mutableListOf<YogaListX>()
        lateinit var yogaDetailGallery: YogaDetailGallery
        lateinit var yogaDetailVideo: YogaDetailVideo
    }
}