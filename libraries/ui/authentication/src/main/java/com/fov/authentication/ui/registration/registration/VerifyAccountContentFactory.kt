package com.fov.authentication.ui.registration.registration

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.ui.composers.textfields.CustomTextField
import com.fov.common_ui.theme.commonPadding
import com.fov.authentication.events.RegistrationEvent
import com.fov.authentication.states.RegistrationState
import com.fov.authentication.viewModels.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun VerifyAccount(
    viewModel: RegistrationViewModel
){
    val state by viewModel.uiState.collectAsState()
    VerifyAccount(
        viewState = state,
        viewModel::handleRegistrationEvent
    )

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun VerifyAccount(
    viewState: RegistrationState,
    events: (event: RegistrationEvent) -> Unit,
){
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    if (viewState.isLoading) {
        LoadingBox()
    }
    else{
        Surface(color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            BoxWithConstraints() {
                val screenWidth = maxWidth
                val screenHeight = maxHeight
                val scrollState = rememberScrollState(0)
            Box(Modifier.fillMaxSize()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .verticalScroll(scrollState)
                )
                {

                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.secondary)


                    )
                    Text(
                        "Verify Account",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h5.copy(
                            MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(start = commonPadding)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        "input the six digit verification code that was sent to your email address",
                        textAlign = TextAlign.Start,
                        style = TextStyle(color = MaterialTheme.colors.onSurface),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(horizontal = commonPadding)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.width(screenWidth)
                            .padding(horizontal = commonPadding)
                    ) {

                        Text(
                            "Resend Code",
                            Modifier.clickable {
                                events(RegistrationEvent.ResendVerificationCode)
                            }.padding(bottom = 4.dp),
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colors.primary,

                            )
                    }

                    var verificationCode by remember { mutableStateOf(TextFieldValue(viewState.verificationCode)) }
                    CustomTextField(
                        value = verificationCode,
                        placeholder = "Enter Verification Code",
                        onChange = {
                            events(RegistrationEvent.VerificationCodeChanged(it.text))
                            verificationCode = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        shape = RoundedCornerShape(30),
                        enabled = viewState.verificationCode.isNotEmpty(),
                        modifier = Modifier
                            .width(screenWidth)
                            .height(70.dp)
                            .padding(horizontal = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
                        ),
                        onClick = {
                            events(RegistrationEvent.VerifyCodeClicked)
                        }) {

                        Text(
                            "Verify Account",
                            color = MaterialTheme.colors.surface
                        )


                    }


                    //Spacer(modifier = Modifier.padding(top = screenHeight/2f))

                    if (!viewState.errorMessage.isNullOrEmpty()) {
                        coroutineScope.launch {
                            var result = snackBarHostState.showSnackbar(
                                message = viewState.errorMessage
                            )


                        }
                    }



                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)
                ) {
                    Text(
                        "Not Working?",

                        color = MaterialTheme.colors.onSurface,

                        )
                    Text(
                        "Contact Us",
                        Modifier.clickable {

                        }.padding(start = 4.dp),
                        style = TextStyle(textAlign = TextAlign.Center),
                        color = MaterialTheme.colors.primary,

                        )
                }

                Spacer(modifier = Modifier.padding(commonPadding))
            }

            }
        }
    }

}