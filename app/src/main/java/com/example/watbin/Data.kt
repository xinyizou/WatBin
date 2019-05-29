package com.example.watbin

class Data (height: Double, width: Double, left: Double, top: Double, gender: String, minAge: Long, maxAge: Long){
    val height: Double
    val width: Double
    val left: Double
    val top: Double
    val gender: String
    val minAge: Long
    val maxAge: Long

    init {
        this.height = height
        this.width = width
        this.left = left
        this.top = top
        this.gender = gender
        this.minAge = minAge
        this.maxAge = maxAge
    }

}