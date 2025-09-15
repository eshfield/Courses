package com.example.courses.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val imageUrl: String,
    val publishDate: String,
)
