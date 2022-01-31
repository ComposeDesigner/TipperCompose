package com.example.tipper.util


fun calculateTip(totalBill: Double, tipPercent: Int): Double {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) {
        (totalBill * tipPercent) / 100
    } else {
        return 0.00
    }
}

fun calcualateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercent: Int
): Double {
    val bill = calculateTip(totalBill = totalBill, tipPercent = tipPercent) + totalBill
    return bill / splitBy
}

