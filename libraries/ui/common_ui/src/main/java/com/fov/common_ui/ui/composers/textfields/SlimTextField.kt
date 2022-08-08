package com.fov.common_ui.ui.composers.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.fov.common_ui.theme.DarkGrey

@Composable
fun  SlimTextField(
    value: TextFieldValue,
    label : String,
    placeholder: String,
    hasError: Boolean = false,
    onChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor : Color = MaterialTheme.colors.surface,
    unfocusedIndicatorColor : Color = MaterialTheme.colors.onPrimary,
    width: Dp,
    padding: Dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onFocused: () -> Unit = { print("focused") },
    onUnFocused: () -> Unit = { print("focused") }
){
    Column(
        modifier = modifier.width(width)
    ) {
        Text(
            label,
            modifier  = Modifier.clickable {  },
            textAlign = TextAlign.Start,
            color  = DarkGrey ,
            style = MaterialTheme.typography.h6.copy(
                MaterialTheme.colors.primary,
                fontSize = 10.sp
            ),
        )
        TextField(
            value = value,

            textStyle = TextStyle(
                color = MaterialTheme.colors.onSurface,
                textDirection = TextDirection.Ltr,
                fontSize = 12.sp
            ),
            onValueChange = onChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                backgroundColor = backgroundColor,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor =  MaterialTheme.colors.onSurface,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                errorLabelColor = Color.Red,
                errorTrailingIconColor = Color.Red,
                errorLeadingIconColor = Color.Red,

                ),
            isError = hasError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .width(width)
                .onFocusChanged {
                    if (it.isFocused) {
                        onFocused()
                    } else {
                        onUnFocused()
                    }


                }


        )
    }
}