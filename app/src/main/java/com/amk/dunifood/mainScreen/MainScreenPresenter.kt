package com.amk.dunifood.mainScreen

import com.amk.dunifood.model.Food
import com.amk.dunifood.model.FoodDao

class MainScreenPresenter(private val foodDao: FoodDao) : MainScreenContract.Presenter {
    private var mainView: MainScreenContract.View? = null

    override fun onAttach(view: MainScreenContract.View) {
        mainView = view
        val data = foodDao.getAllFoods()
        mainView?.showFoods(data)
    }

    override fun onDetach() {
        mainView = null
    }

    override fun onSearchFood(filter: String) {
        if (filter.isNotEmpty()) {
            //show filtered data
            val data = foodDao.searchFoods(filter)
            mainView?.refreshFoods(data)
        } else {
            //show all data
            val dataToShow = foodDao.getAllFoods()
            mainView?.showFoods(dataToShow)
        }
    }

    override fun onAddNewFoodClicked(food: Food) {
        //database
        foodDao.insertFood(food)
        //adapter
        mainView?.addNewFood(food)
    }

    override fun onDeleteAllClicked() {
        foodDao.deleteAllFoods()
        mainView?.refreshFoods(foodDao.getAllFoods())
    }

    override fun onUpdateFood(food: Food, position: Int) {
        foodDao.updateFood(food)
        mainView?.updateFood(food, position)
    }

    override fun onDeleteFood(food: Food, position: Int) {
        foodDao.deleteFood(food)
        mainView?.deleteFood(food, position)
    }

    override fun firstRun() {
        val firstRunFoodList = listOf(
            Food(
                txtTitle = "Hamburger",
                txtPrice = "15",
                txtDistance = "3",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = 20,
                averageRating = 4.5f
            ),
            Food(
                txtTitle = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtCity = "Tehran, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = 10,
                averageRating = 4f
            ),
            Food(
                txtTitle = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtCity = "Isfahan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = 30,
                averageRating = 2f
            ),
            Food(
                txtTitle = "pizza",
                txtPrice = "10",
                txtDistance = "2.5",
                txtCity = "Zahedan, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numOfRating = 80,
                averageRating = 1.5f
            ),
            Food(
                txtTitle = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtCity = "Mashhad, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = 200,
                averageRating = 3f
            ),
            Food(
                txtTitle = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtCity = "Jolfa, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = 50,
                averageRating = 3.5f
            ),
            Food(
                txtTitle = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtCity = "NewYork, USA",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = 70,
                averageRating = 2.5f
            ),
            Food(
                txtTitle = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtCity = "Berlin, Germany",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = 40,
                averageRating = 4.5f
            ),
            Food(
                txtTitle = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtCity = "Beijing, China",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = 15,
                averageRating = 5f
            ),
            Food(
                txtTitle = "Baryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtCity = "Ilam, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = 28,
                averageRating = 4.5f
            ),
            Food(
                txtTitle = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtCity = "Karaj, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = 27,
                averageRating = 5f
            ),
            Food(
                txtTitle = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtCity = "Shiraz, Iran",
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = 35,
                averageRating = 2.5f
            ),
        )
        foodDao.insertAllFoods(firstRunFoodList)
    }

}