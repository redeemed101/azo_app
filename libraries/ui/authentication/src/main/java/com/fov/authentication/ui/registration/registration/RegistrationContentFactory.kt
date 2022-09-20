package com.fov.authentication.ui.registration.registration

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.fov.common_ui.ui.composers.general.LoadingBox
import com.fov.common_ui.ui.composers.textfields.CustomTextField
import com.fov.common_ui.ui.composers.buttons.FacebookButton
import com.fov.common_ui.ui.composers.buttons.GoogleButton
import com.fov.common_ui.theme.buttonHeight
import com.fov.common_ui.theme.commonPadding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.fov.authentication.R
import com.fov.authentication.events.RegistrationEvent
import com.fov.authentication.states.RegistrationState
import com.fov.authentication.viewModels.RegistrationViewModel
import kotlinx.coroutines.launch


@Composable
fun Registration(
    viewModel: RegistrationViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    Registration(
        viewState = state,
        events = viewModel::handleRegistrationEvent,
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Registration(
    viewState: RegistrationState,
    events: (event: RegistrationEvent) -> Unit
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //val focusRequester = remember {FocusRequester() }
    val (focusRequester) = FocusRequester.createRefs()


    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current
    
    if (viewState.isLoading) {
        LoadingBox()
    }
    else{
        Surface(color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

            var fbButton : LoginButton? = null





            BoxWithConstraints() {
                val screenWidth = maxWidth
                val scrollState = rememberScrollState(0)


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
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
                        "Create New Account",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h5.copy(
                            MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(start = commonPadding)
                    )
                    Spacer(modifier = Modifier.padding(12.dp))

                    var emailField by remember { mutableStateOf(TextFieldValue(viewState.email)) }
                    CustomTextField(
                        value = emailField,
                        placeholder = "Email address",
                        hasError = viewState.isEmailValid,
                        modifier  = Modifier.focusRequester(focusRequester),
                        onChange = {
                            events(RegistrationEvent.EmailChanged(it.text))
                            emailField = it

                        },
                        //modifier = Modifier.focusRequester(focusRequester),
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                //focusRequester.requestFocus()
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    var fullnameField by remember { mutableStateOf(TextFieldValue(viewState.fullname)) }
                    CustomTextField(
                        value = fullnameField,
                        placeholder = "Full-name",
                        modifier = Modifier.focusRequester(focusRequester),
                        onChange = {
                            events(RegistrationEvent.FullNameChanged(it.text))
                            fullnameField = it

                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                //focusRequester.requestFocus()
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10)
                    )
                    /*Spacer(modifier = Modifier.padding(8.dp))
                    var usernameField by remember { mutableStateOf(TextFieldValue(viewState.username)) }
                    CustomTextField(
                        value = usernameField,
                        placeholder = "Choose Username",
                        onChange = {
                            events(RegistrationEvent.UsernameChanged(it.text))
                            usernameField = it
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        //modifier = Modifier.focusRequester(focusRequester),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                //focusRequester.requestFocus()
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10)
                    )*/
                    Spacer(modifier = Modifier.padding(8.dp))
                    var isPasswordFieldFocused by remember { mutableStateOf(false)}
                    AnimatedVisibility(visible = isPasswordFieldFocused) {
                        Text(
                            "At least one special character, one digit and 6 characters long",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2.copy(
                                Color.Gray,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .padding(start = commonPadding)

                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))

                    var passwordField by remember { mutableStateOf(TextFieldValue(viewState.password)) }
                    var passwordVisibility by remember { mutableStateOf(false) }
                    CustomTextField(
                        value = passwordField,
                        placeholder = "Password",
                        onFocused = {
                            isPasswordFieldFocused =  true
                        },
                        onUnFocused = {
                            isPasswordFieldFocused= false
                        },
                        onChange = {
                            events(RegistrationEvent.PasswordChanged(it.text))
                            passwordField = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        //modifier = Modifier.focusRequester(focusRequester),
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                //focusRequester.requestFocus()
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_eye),
                                "",
                                modifier = Modifier.clickable {
                                    passwordVisibility = !passwordVisibility
                                },
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }

                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    var confirmVisibility by remember { mutableStateOf(false) }
                    var confirmField by remember { mutableStateOf(TextFieldValue(viewState.confirmPassword)) }
                    CustomTextField(
                        value = confirmField,
                        placeholder = "Confirm Password",
                        onChange = {
                            events(RegistrationEvent.ConfirmPasswordChanged(it.text))
                            confirmField = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        //modifier = Modifier.focusRequester(focusRequester),
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        visualTransformation = if (confirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_eye), "",
                                modifier = Modifier.clickable {
                                    confirmVisibility = !confirmVisibility
                                },
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                         modifier = Modifier.padding(horizontal = commonPadding)
                    ) {
                        Button(
                            shape = MaterialTheme.shapes.medium,
                            enabled = viewState.isRegistrationContentValid,
                            modifier = Modifier
                                .width(screenWidth)
                                .height(buttonHeight)
                                .padding(horizontal = 12.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
                            ),
                            onClick = {
                                events(RegistrationEvent.RegistrationClicked)
                            }) {

                            Text(
                                "Sign Up",
                                color = Color.White
                            )


                        }

                        Spacer(modifier = Modifier.padding(12.dp))


                        Spacer(modifier = Modifier.padding(12.dp))



                        Spacer(modifier = Modifier.padding(8.dp))


                        Spacer(modifier = Modifier.padding(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            //modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)

                            ) {
                            Text(
                                "Already have an account?",

                                color = MaterialTheme.colors.onSurface,

                                )
                            Text(
                                "Login",
                                Modifier
                                    .clickable {
                                        events(RegistrationEvent.LoginClicked)
                                    }
                                    .padding(start = 4.dp),
                                style = TextStyle(),
                                color = MaterialTheme.colors.primary,

                                )
                        }
                    }
                    Spacer(modifier = Modifier.padding(commonPadding))
                    if (!viewState.errorMessage.isNullOrEmpty()) {
                        scope.launch {
                            var result = snackBarHostState.showSnackbar(
                                message = viewState.errorMessage
                            )

                        }
                    }




                }




            }
        }

    }

}