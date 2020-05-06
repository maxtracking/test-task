package me.alexeygusev.testtask.api.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * This file contains models for APIs. In real life this would be grouped and split per APIs areas.
 * For the test, I just put all models close to each other.
 */

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val items: List<Item> = listOf()
)

@JsonClass(generateAdapter = true)
data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
) : Serializable
