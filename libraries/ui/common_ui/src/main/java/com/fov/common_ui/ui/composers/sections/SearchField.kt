package com.fov.common_ui.ui.composers.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.DarkGrey
import com.fov.common_ui.R
import com.fov.common_ui.theme.ThemeHelper

@Composable
fun SearchField(
    value : TextFieldValue,
    placeholder : String,
    onChange : (TextFieldValue) -> Unit,
    width : Dp,
    height : Dp,
    padding : Dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    onClose : () -> Unit = {}
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ){

        val textBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.8f)
        TextField(
            value = value,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = MaterialTheme.colors.surface,
                textAlign = TextAlign.Justify),
            onValueChange = onChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.surface,
                backgroundColor = textBackgroundColor,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.surface,
                placeholderColor = MaterialTheme.colors.surface.copy(alpha = 0.4f)

            ),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_search),
                    "",
                    tint = MaterialTheme.colors.surface,
                    modifier = Modifier
                        //.padding(top = 5.dp, bottom = 5.dp)
                        .height((height * 0.9f))

                )
            },

            trailingIcon = {
                if(!value.text.equals(""))
                    Icon(
                        painterResource(R.drawable.ic_x),
                        "",
                        tint = MaterialTheme.colors.surface,
                        modifier = Modifier
                            //.padding(top = 5.dp, bottom = 5.dp)
                            .height((height * 0.9f))
                            .clickable {
                                onClose()
                            }

                    )
            },
            placeholder = { Text(placeholder,textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary) },
            modifier = Modifier
                .width(width)
                //.height(height)
                .padding(horizontal = padding)
                .clip(shape = RoundedCornerShape(50))
        )
    }
}