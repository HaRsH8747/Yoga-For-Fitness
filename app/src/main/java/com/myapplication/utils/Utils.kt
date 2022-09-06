package com.myapplication.utils

import com.myapplication.models.Category
import com.myapplication.models.YogaList
import com.myapplication.models.YogaListX

class Utils {
    companion object{
        const val BASE_URL = "https://invisionicl.com/mobileapp/wp-json/yogaforfitness/"
        const val BEGINNERS = "Beginners"
        const val INTERMEDIATE = "Intermediate"
        lateinit var categoryResponse: Category
        lateinit var yogaResponse: YogaList
        var beginnersList = mutableListOf<YogaListX>()
        var intermediateList = mutableListOf<YogaListX>()
        var allYogaList = mutableListOf<YogaListX>()
        var yogaSearchList = mutableListOf<YogaListX>()
    }
}