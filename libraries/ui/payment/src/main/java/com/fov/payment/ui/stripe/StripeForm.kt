package com.fov.payment.ui.stripe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.common_ui.theme.commonPadding
import com.fov.common_ui.ui.composers.textfields.CustomTextField
import com.fov.payment.events.PayEvent
import com.fov.payment.states.PayState
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import com.fov.common_ui.theme.buttonHeight
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StripeForm(
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
                "Pay through Stripe",
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

            /*var amountField by remember { mutableStateOf(TextFieldValue(payState.amount.toString())) }
            CustomTextField(
                value = amountField,
                placeholder = "Amount",
                modifier = Modifier.focusRequester(focusRequester),
                onChange = {
                    //events(RegistrationEvent.FullNameChanged(it.text))
                    amountField = it

                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Decimal
                ),
                keyboardActions = KeyboardActions(
                    onNext = {

                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                width = screenWidth,
                padding = commonPadding,
                shape = RoundedCornerShape(10)
            )*/

            Spacer(modifier = Modifier.padding(12.dp))
            
            CardInput(modifier = Modifier
                .fillMaxWidth()
                .padding(commonPadding), payEvents)

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


                        if(
                            payState.cardWidget != null
                            && payState.clientStripeSecret != null
                            && payState.stripePublishableKey != null && payState.stripeAccountId != null) {
                            val widget = payState.cardWidget
                            if(widget.paymentMethodCreateParams != null){
                                payEvents(PayEvent.SetErrorText(null))
                                payState.stripePaymentMethod(payState.cardWidget,
                                    payState.stripePublishableKey,
                                    payState.stripeAccountId,
                                    payState.clientStripeSecret)
                            }
                            else{
                               payEvents(PayEvent.SetErrorText("Fill all Card Details"))
                            }

                        }

                    }) {

                    Text(
                        "Make Payment",
                        color = Color.White
                    )


                }
            }
        }
    }
}