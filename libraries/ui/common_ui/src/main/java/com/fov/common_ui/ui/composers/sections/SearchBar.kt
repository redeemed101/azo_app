package com.fov.common_ui.ui.composers.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.R

@Composable
fun SearchBar(
    value : TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
    onSearch : () -> Unit,
    onClose : () -> Unit,
    onCloseSearch : () -> Unit,
    width : Dp,
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchField(
            value = value,
            placeholder = "",
            onChange = {
                onChange(it)
            },
            width = width * 0.9f,
            height = 50.dp,
            padding = width * 0.1f,
            keyboardOptions = KeyboardOptions(

                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            onClose = {
                onClose()

            }
        )
        Icon(
            painterResource(R.drawable.ic_x),
            "",
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                //.padding(top = 5.dp, bottom = 5.dp)
                //.height(50.dp)
                //.width(screenWidth * 0.2f)
                .clickable {
                    onCloseSearch()
                }

        )
    }
}