package com.myapplication.models.yogadetailvideo

import com.myapplication.models.yogabenefit.Benefit

data class Details(
    val benefit: List<Benefit>,
    val description: String,
    val media: String,
    val media_type: String,
    val title: String
)