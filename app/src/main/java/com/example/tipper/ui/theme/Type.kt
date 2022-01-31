package com.example.tipper.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import com.example.tipper.R


private val font_bold = FontFamily(
    Font(R.font.quicksand_bold)
)

private val font_medium = FontFamily(Font(R.font.quicksand_medium))

private val font_semibold = FontFamily(Font(R.font.quicksand_semi_bold))

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = font_semibold,
        fontSize = 16.sp
    ),

    body2 = TextStyle(
        fontFamily = font_bold,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = font_bold,
        fontSize = 32.sp

    ),


    subtitle1 = TextStyle(
        fontFamily = font_semibold,
        fontSize = 28.sp
    )


)