package com.amk.dunifood.utils

interface BasePresenter<T> {
    fun onAttach(view: T)
    fun onDetach()
}

interface BaseView {
    //your view function
}