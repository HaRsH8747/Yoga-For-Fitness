package com.myapplication.models

data class YogaList(
    val timestamp: Int,
    val totalrecord: Int,
    val yogalist: List<YogaListX>
)