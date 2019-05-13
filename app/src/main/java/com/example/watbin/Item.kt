package com.example.watbin

class Item {
    var name: String = ""
    var description: String = ""
    var category: Category = Category.GARBAGE
    var expanded: Boolean = false

    fun Item(name: String, description: String, category: Category ) {
        this.name = name
        this.description = description
        this.category = category
    }

    fun getItemName(): String {
        return name
    }

}