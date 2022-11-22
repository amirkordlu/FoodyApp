package com.amk.dunifood.mainScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amk.dunifood.databinding.ItemFoodBinding
import com.amk.dunifood.model.Food
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvent: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            binding.itemTxtTitle.text = data[position].txtTitle
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtPrice.text = "$" + data[position].txtPrice
            binding.itemTxtDistance.text = data[position].txtDistance + " miles from you"
            binding.itemRatingFood.rating = data[position].averageRating
            binding.itemTxtRating.text = "(" + data[position].numOfRating.toString() + " Ratings)"

            Glide.with(binding.root.context)
                .load(data[position].urlImage)
                .transform(RoundedCorners(16))
                .into(binding.itemImgMain)

            itemView.setOnClickListener {
                foodEvent.onFoodClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {

                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood: Food) {
        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(newFood: Food, position: Int) {

        data[position] = newFood
        notifyItemChanged(position)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<Food>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvents {
        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, position: Int)
    }

}