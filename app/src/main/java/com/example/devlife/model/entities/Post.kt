package com.example.devlife.model.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Post(

    @SerialName("id")
    val id: Int,

    @SerialName("description")
    val description: String,

    @SerialName("gifURL")
    val gifURL: String
)