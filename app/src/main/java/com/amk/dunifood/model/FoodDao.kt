package com.amk.dunifood.model

import androidx.room.*

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food)

    @Insert
    fun insertAllFoods(list: List<Food>)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllFoods()

    @Query("SELECT * FROM table_food")
    fun getAllFoods(): List<Food>

    @Query("SELECT * FROM table_food WHERE txtTitle LIKE '%' || :searching || '%'")
    fun searchFoods(searching: String): List<Food>
}