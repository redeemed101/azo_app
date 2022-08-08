package com.fov.common_ui.ui.composers.sections

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
        var textBackgroundColor = DarkGrey
        if(ThemeHelper.isDarkTheme())
            textBackgroundColor = MaterialTheme.colors.onBackground
        TextField(
            value = value,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Justify),
            onValueChange = onChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = textBackgroundColor,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                placeholderColor = Color.LightGray

            ),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_search),
                    "",
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        //.padding(top = 5.dp, bottom = 5.dp)
                        .height((height * 0.9f))

                )
            },

            trailingIcon = {
                if(!value.text.equals(""))
                    Icon(
                        painterResource(R.drawable.ic_x_circle),
                        "",
                        tint = MaterialTheme.colors.onPrimary,
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