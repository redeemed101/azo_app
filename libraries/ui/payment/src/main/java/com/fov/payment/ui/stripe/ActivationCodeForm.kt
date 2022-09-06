package com.fov.payment.ui.stripe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.buttonHeight
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.textfields.CustomTextField
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActivationCodeForm(
    modifier: Modifier,
    payState: PayState,
    payEvents: (PayEvent) -> Unit
){

    val (focusRequester) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BoxWithConstraints() {
        val screenWidth = maxWidth
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        )
        {
            if (payState.errorText != null){
                Text(
                    text = payState.errorText,
                    style = MaterialTheme.typography.caption.copy(
                        Color.Red,
                        fontWeight = FontWeight.Bold,


                        ),
                )
            }
            Text(
                "Enter Activation Code",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h5.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,

                    ),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = commonPadding)
            )
            Spacer(modifier = Modifier.padding(12.dp))



            var codeField by remember { mutableStateOf(TextFieldValue(payState.activationCode.toString())) }
            CustomTextField(
                value = codeField,
                placeholder = "Activation Code",
                modifier = Modifier.focusRequester(focusRequester),
                onChange = {
                    //events(RegistrationEvent.FullNameChanged(it.text))
                    payEvents(PayEvent.SetActivationCode(it.text))
                    codeField = it

                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onNext = {

                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                width = screenWidth,
                padding = commonPadding,
                shape = RoundedCornerShape(10)
            )

            Spacer(modifier = Modifier.padding(12.dp))


            Spacer(modifier = Modifier.padding(12.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = commonPadding)
            ) {
                Button(
                    shape = MaterialTheme.shapes.medium,
                    enabled = !payState.isPaymentValid,
                    modifier = Modifier
                        .width(screenWidth)
                        .height(buttonHeight),

                    onClick = {
                        if (payState.activationCode != null && payState.activationCode.isNotEmpty()) {
                            payEvents(PayEvent.SetErrorText(null))
                            payEvents(PayEvent.SubmitCode)
                        }
                        else{
                            payEvents(PayEvent.SetErrorText("Enter activation code"))
                        }

                    }) {

                    Text(
                        "Submit Code",
                        color = MaterialTheme.colors.surface
                    )


                }
            }
        }
    }
}