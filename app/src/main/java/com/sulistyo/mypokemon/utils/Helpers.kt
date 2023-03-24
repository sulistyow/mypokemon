package com.sulistyo.mypokemon.utils

fun isCatched(): Boolean {
    val randomNum = (1..100).random()
    val percentage = ((randomNum.toDouble() / 100) * 100)
    return percentage >= 50
}

fun primeNumber(): Boolean {
    val num = (1..10).random()
    var flag = true
    for (i in 2..num / 2) {
        if (num % i == 0) {
            flag = false
            break
        }
    }
    return flag
}

fun fibonacciSeries(myInput: Int): Int {
    var temp1 = 0
    var temp2 = 1
    val arr = ArrayList<Int>()
    for (i in 1..myInput) {
        arr.add(temp1)
        val sum = temp1 + temp2
        temp1 = temp2
        temp2 = sum
    }
    return arr.last()
}