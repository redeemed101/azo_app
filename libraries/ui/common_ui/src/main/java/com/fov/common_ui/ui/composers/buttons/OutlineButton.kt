package com.fov.common_ui.ui.composers.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun customOutlinedButton(text : String,
                         width: Dp? = null,
                         cornerShape : Int = 10,
                         imageContent: @Composable RowScope.() -> Unit = {},
                         action : () -> Unit){
    OutlinedButton(
        onClick = action,
        modifier = if(width != null) Modifier.width(width) else Modifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RoundedCornerShape(cornerShape),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onSurface)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            imageContent()
            Spacer(modifier = Modifier.width(6.dp))
            Text( text = text )
        }
    }
}