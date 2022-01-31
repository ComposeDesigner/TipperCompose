package com.example.tipper

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipper.components.InputField
import com.example.tipper.components.RoundIconButton
import com.example.tipper.ui.theme.TipperTheme
import com.example.tipper.util.calcualateTotalPerPerson
import com.example.tipper.util.calculateTip
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tipper {
                MainContent()
            }
        }
    }
}

@Composable
fun Tipper(content: @Composable () -> Unit) {
    TipperTheme {
        Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
            Column() {
                content()
            }

        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MainContent() {
    val splitNumber = remember {
        mutableStateOf(2)
    }

    val tipAmount = remember {
        mutableStateOf(0.0)
    }

    var totalPerPerson = remember {
        mutableStateOf(0.0)
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Tipper(
            splitNumber = splitNumber,
            tipAmount = tipAmount,
            totalPerPerson = totalPerPerson
        ) { }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Tipper(
    modifier: Modifier = Modifier,
    splitNumber: MutableState<Int>,
    tipAmount: MutableState<Double>,
    totalPerPerson: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    var totalBillState = remember {
        mutableStateOf<String>("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var sliderState = remember {
        mutableStateOf(0.0f)
    }

    val sliderColors: SliderColors = SliderDefaults.colors(
        activeTickColor = Color(0xFF2196F3),
        inactiveTickColor = Color(0xFF90CAF9),
        thumbColor = Color(0xFF1E88E5),
        inactiveTrackColor = Color(0xFFBBDEFB),
        activeTrackColor = Color(0xFF64B5F6)
    )

    val tipPercent = (sliderState.value * 100).toInt()
    val total = "%.2f".format(totalPerPerson.value)
    val rippleColor = rememberRipple(color = Color(0xFFBBDEFB))


    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(vertical = 60.dp, horizontal = 24.dp)
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "Bill total",
                color = Color(0xFF858585),
                modifier = Modifier.align(Alignment.Start)
            )
            InputField(
                modifier = modifier.fillMaxWidth(),
                valueState = totalBillState,
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState)
                        return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }) {
                totalBillState.value = it

                if (it != "") {
                    tipAmount.value =
                        calculateTip(
                            totalBillState.value.toDouble(),
                            tipPercent
                        )
                    totalPerPerson.value = calcualateTotalPerPerson(
                        totalBill = totalBillState.value.toDouble(),
                        splitBy = splitNumber.value,
                        tipPercent = tipPercent
                    )
                } else {
                    tipAmount.value = 0.0
                    totalPerPerson.value = 0.0
                }

            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Text(
                text = "Tip",
                color = Color(0xFF858585),
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "$tipPercent %",
                modifier = Modifier.align(Alignment.Start),
                style = MaterialTheme.typography.h1
            )

            CompositionLocalProvider(LocalIndication provides rippleColor) {
                Slider(
                    value = sliderState.value,
                    onValueChange = { value ->
                        if (validState) {
                            sliderState.value = value
                            tipAmount.value =
                                calculateTip(
                                    totalBillState.value.toDouble(),
                                    tipPercent
                                )
                            totalPerPerson.value = calcualateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitNumber.value,
                                tipPercent = tipPercent
                            )
                        } else 0

                    },
                    valueRange = 0f..1f,
                    steps = 5,
                    colors = sliderColors,
                    onValueChangeFinished = {
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE3F2FD))
                .padding(vertical = 0.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Split",
                        style = MaterialTheme.typography.body2,
                        color = Color(0xFF64B5F6)
                    )
                    Row(modifier = Modifier.offset(y = 2.dp)) {
                        RoundIconButton(imageVector = Icons.Default.Remove,
                            onClick = {
                                if (splitNumber.value > 1)
                                    splitNumber.value = splitNumber.value - 1
                                else 1

                                tipAmount.value =
                                    calculateTip(
                                        totalBillState.value.toDouble(),
                                        tipPercent
                                    )
                                totalPerPerson.value = calcualateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitNumber.value,
                                    tipPercent = tipPercent
                                )

                            }
                        )

                        Text(
                            text = "${splitNumber.value}",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .offset(y = -2.dp),
                            style = MaterialTheme.typography.subtitle1,
                            color = Color(0xFF42A5F5)
                        )

                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClick = {
                                splitNumber.value = splitNumber.value + 1

                                tipAmount.value =
                                    calculateTip(
                                        totalBillState.value.toDouble(),
                                        tipPercent
                                    )
                                totalPerPerson.value = calcualateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitNumber.value,
                                    tipPercent = tipPercent
                                )
                            })
                    }
                }


            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Total Per Person",
                    style = MaterialTheme.typography.body2,
                    color = Color(0xFF64B5F6)
                )
                Text(
                    text = "$${total}",
                    style = MaterialTheme.typography.h1,
                    color = Color(0xFF42A5F5)
                )
            }
        }
    }
}
