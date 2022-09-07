package com.myapplication.models.yogadetailgallery

import com.myapplication.models.yogabenefit.Benefit

data class Details(
    val benefit: List<Benefit>,
    val description: String,
    val media: Media,
    val media_type: String,
    val title: String
)