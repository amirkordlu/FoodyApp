package com.amk.dunifood.mainScreen

import com.amk.dunifood.model.Food
import com.amk.dunifood.utils.BasePresenter
import com.amk.dunifood.utils.BaseView

interface MainScreenContract {

    interface Presenter : BasePresenter<View> {

        fun onSearchFood(filter: String)
        fun onAddNewFoodClicked(food: Food)
        fun onDeleteAllClicked()

        fun onUpdateFood(food: Food, position: Int)
        fun onDeleteFood(food: Food, position: Int)

        fun firstRun()
    }

    interface View : BaseView {

        fun showFoods(data: List<Food>)
        fun refreshFoods(data: List<Food>)
        fun addNewFood(newFood: Food)
        fun deleteFood(oldFood: Food, position: Int)
        fun updateFood(editingFood: Food, position: Int)

    }
}