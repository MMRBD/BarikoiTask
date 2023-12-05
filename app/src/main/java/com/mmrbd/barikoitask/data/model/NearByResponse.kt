package com.mmrbd.barikoitask.data.model

data class NearByResponse(
    val places: List<Place>,
    val status: Int,
    val message: String
)