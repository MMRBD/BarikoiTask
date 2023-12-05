package com.mmrbd.barikoitask.data.model

data class Place(
    val id: Int,
    val name: String,
    val address: String,
    val area: String,
    val city: String,
    val distance_in_meters: String,
    val latitude: String,
    val longitude: String,
    val pType: String,
    val postCode: String,
    val subType: String,
    val uCode: String
)