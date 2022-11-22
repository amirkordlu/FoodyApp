package com.amk.dunifood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class Food(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val txtTitle: String,
    val txtCity: String,
    val txtPrice: String,
    val txtDistance: String,
    val urlImage: String,
    val numOfRating: Int,
    val averageRating: Float

)


