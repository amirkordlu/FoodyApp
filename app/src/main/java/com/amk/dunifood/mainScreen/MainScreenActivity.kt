package com.amk.dunifood.mainScreen

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amk.dunifood.databinding.ActivityMainBinding
import com.amk.dunifood.databinding.DialogAddItemBinding
import com.amk.dunifood.databinding.DialogDeleteItemBinding
import com.amk.dunifood.databinding.DialogUpdateItemBinding
import com.amk.dunifood.model.Food
import com.amk.dunifood.model.MyDatabase
import com.amk.dunifood.utils.BASE_URL_IMAGE
import com.amk.dunifood.utils.showToast

class MainScreenActivity : AppCompatActivity(), FoodAdapter.FoodEvents, MainScreenContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: FoodAdapter
    private lateinit var presenter: MainScreenContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainScreenPresenter(MyDatabase.getDatabase(this).foodDao)


        val sharedPreferences = getSharedPreferences("duniFood", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("firstRun", true)) {
            presenter.firstRun()
            sharedPreferences.edit().putBoolean("firstRun", false).apply()
        }

        presenter.onAttach(this)

        binding.btnRemoveAllFoods.setOnClickListener {
            presenter.onDeleteAllClicked()
        }

        binding.btnAddFood.setOnClickListener {
            addNewFood()
        }

        binding.edtSearch.addTextChangedListener { editTextInput ->
            presenter.onSearchFood(editTextInput.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    private fun addNewFood() {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogAddItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {

            if (dialogBinding.dialogEdtFoodName.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodCity.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodPrice.text!!.isNotEmpty() &&
                dialogBinding.dialogEdtFoodDistance.text!!.isNotEmpty()
            ) {

                val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                val txtaverageRatingNumber = (1..150).random()
                val averageRatingBarStar = (1..5).random().toFloat()

                val randomForUrl = (1 until 12).random()
                val urlPic = "$BASE_URL_IMAGE$randomForUrl.jpg"

                val newFood = Food(
                    txtTitle = txtName,
                    txtCity = txtCity,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    urlImage = urlPic,
                    numOfRating = txtaverageRatingNumber,
                    averageRating = averageRatingBarStar
                )

                presenter.onAddNewFoodClicked(newFood)

                dialog.dismiss()

            } else {
                showToast("لطفا همه مقادیر را وارد کنید :)")
            }

        }
    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogUpdateBinding = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(dialogUpdateBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateBinding.dialogEdtFoodName.setText(food.txtTitle)
        dialogUpdateBinding.dialogEdtFoodCity.setText(food.txtCity)
        dialogUpdateBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        dialogUpdateBinding.dialogEdtFoodDistance.setText(food.txtDistance)

        dialogUpdateBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogUpdateBinding.dialogUpdateBtnDone.setOnClickListener {

            if (dialogUpdateBinding.dialogEdtFoodName.text!!.isNotEmpty() &&
                dialogUpdateBinding.dialogEdtFoodCity.text!!.isNotEmpty() &&
                dialogUpdateBinding.dialogEdtFoodPrice.text!!.isNotEmpty() &&
                dialogUpdateBinding.dialogEdtFoodDistance.text!!.isNotEmpty()
            ) {
                val txtName = dialogUpdateBinding.dialogEdtFoodName.text.toString()
                val txtCity = dialogUpdateBinding.dialogEdtFoodCity.text.toString()
                val txtPrice = dialogUpdateBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogUpdateBinding.dialogEdtFoodDistance.text.toString()

                val newFood = Food(
                    id = food.id,
                    txtTitle = txtName,
                    txtCity = txtCity,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    urlImage = food.urlImage,
                    numOfRating = food.numOfRating,
                    averageRating = food.averageRating
                )

                presenter.onUpdateFood(newFood, position)
                dialog.dismiss()

            } else {
                showToast("لطفا همه مقادیر را وارد کنید :)")
            }

        }
    }

    override fun onFoodLongClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            presenter.onDeleteFood(food, position)
            dialog.dismiss()
        }
    }

    override fun showFoods(data: List<Food>) {
        mainAdapter = FoodAdapter(ArrayList(data), this)
        binding.recyclerMain.adapter = mainAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun refreshFoods(data: List<Food>) {
        mainAdapter.setData(ArrayList(data))
    }

    override fun addNewFood(newFood: Food) {
        mainAdapter.addFood(newFood)
    }

    override fun deleteFood(oldFood: Food, position: Int) {
        mainAdapter.removeFood(oldFood, position)
    }

    override fun updateFood(editingFood: Food, position: Int) {
        mainAdapter.updateFood(editingFood, position)
    }
}
