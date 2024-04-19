package com.mrdev.androidtestassesment.base

interface OnPostItemClickListener<T> {
    fun onItemClick(item: T)
}

interface HiltPostItemClickListener<T>{
    fun onPostItemClick(item: T)
}
