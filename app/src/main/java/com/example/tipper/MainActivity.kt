package com.example.tipper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipper.components.InputField
import com.example.tipper.ui.theme.TipperTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tipper {
                BillDetails()
            }
        }
    }
}

@Composable
fun Tipper(content: @Composable () -> Unit) {
    TipperTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column() {
                content()
            }

        }
    }
}

@Preview
@Composable
fun TipperHeader(tipPerPerson: Double = 20.00) {
    val total = "%.2f".format(tipPerPerson)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(8.dp))),
        color = Color(0xFFEEE5FF)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Per Person", fontWeight = FontWeight(500))
            Text(
                text = "$${total}",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight(700)
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun BillDetails() {
    BillForm() { billAmt ->
        Log.d("AMT", "Entered value: $billAmt")
    }

}


@ExperimentalComposeUiApi
@Preview
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {}
) {

    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Column() {
            InputField(
                valueState = totalBillState,
                labelId = "Enter bill amount",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState)
                        return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                }
            )
            Text(text = "Input goes here")
            Text(text = "Slider goes here")
            Text(text = "More goes here")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TipperTheme {
//        Tipper {
//            Text(text = "What happens now?")
//        }
//    }
//}