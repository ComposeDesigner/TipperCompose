package com.example.tipper.components

import android.graphics.Outline
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//val IconButtonSizeModifier = Modifier.size(140.dp)

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String> = remember {
        mutableStateOf("50.00")
    },
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    onInputChanged: (String) -> Unit = {}
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    OutlinedTextField(
        value = valueState.value,
        onValueChange = onInputChanged,
        modifier = modifier,
        interactionSource = interactionSource,
        leadingIcon = {
            val isFocused = interactionSource.collectIsFocusedAsState()
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                modifier = Modifier.size(24.dp).offset(y = 2.dp),
                contentDescription = "Money Icon",
                tint = if (isFocused.value) Color(0xFF2196F3)
                else Color(0xFFE3F2FD)
            )
        },
        singleLine = isSingleLine,
        maxLines = 1,
        textStyle = MaterialTheme.typography.h1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        placeholder = { Text(text = "0.00") },
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                unfocusedBorderColor = Color(0x6D64B5F6),
                focusedBorderColor = Color(0xFF1E88E5),
                cursorColor = Color(0xFF64B5F6)
            )
        )
}

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color(0xFF64B5F6),
    backgroundColor: Color = Color(0xD0BBDEFB),
    elevation: Dp = 0.dp,
) {

    Card(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onClick.invoke() }
            .padding(4.dp),

        shape = CircleShape,
        backgroundColor = backgroundColor,
        elevation = elevation,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Add or subtract icon",
            tint = tint
        )

    }

}

@Preview(showBackground = true)
@Composable
fun viewTextPreview() {
    InputField()
}