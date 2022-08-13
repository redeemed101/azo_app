package com.fov.authentication.ui.registration.registration

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fov.common_ui.ui.composers.general.ClickableTextLink
import com.fov.common_ui.extensions.toDp
import com.fov.common_ui.extensions.toPx
import com.fov.common_ui.theme.ThemeHelper
import com.fov.common_ui.theme.commonPadding
import com.fov.authentication.events.RegistrationEvent
import com.fov.authentication.ui.models.OnboardingCard
import com.fov.authentication.viewModels.RegistrationViewModel
import kotlinx.coroutines.launch
import com.fov.authentication.R

@ExperimentalMaterialApi
@Composable
fun Onboarding(
    viewModel: RegistrationViewModel
){

    OnBoardingUI(events = viewModel::handleRegistrationEvent)
}
@ExperimentalMaterialApi
@Composable
private fun OnBoardingUI(
    events: (event: RegistrationEvent) -> Unit,
){
    val rowScrollState = rememberScrollState(0)
    val columnScrollState = rememberScrollState(0)


    val scope = rememberCoroutineScope()
    var currentPositionState = remember { mutableStateOf(1) }
    val cards = getItems()


    Surface(color = MaterialTheme.colors.surface, modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()

    ) {

        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight= maxHeight
            val swipeableState = rememberSwipeableState(0)
            val sizePx = with(LocalDensity.current) { screenWidth.toPx() }
            val anchors = mapOf(0f to 0, sizePx to 1)


            val doActionOnClick : () -> Unit = {
                scope.launch {


                    rowScrollState.scrollBy((screenWidth.value.toPx))
                    currentPositionState.value += 1

                }
            }
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.verticalScroll(columnScrollState)) {
                Logo()
                //Spacer(modifier = Modifier.padding(screenHeight * 0.1f.toDp))
                Row(
                    Modifier
                        .horizontalScroll(
                            state = rowScrollState,
                            false
                        )

                ) {

                    cards.forEach{ item ->

                        Row(

                            modifier = Modifier
                                .width(screenWidth)
                                .swipeable(
                                    orientation = Orientation.Horizontal,
                                    state = swipeableState,
                                    anchors = anchors,
                                    thresholds = { from, to -> FractionalThreshold(0.3f) }
                                )

                        ) {

                            MainCard(
                                item.resourceId,
                                item.cardHeader,
                                item.cardDetails
                            )

                        }

                    }


                }

                Spacer(modifier = Modifier.padding(screenHeight * 0.1f.toDp))

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    positionDots(cards.size,currentPositionState.value)
                    Spacer(modifier = Modifier.padding(4.dp))
                    if(currentPositionState.value < cards.size) {
                        actionButton(
                            action = doActionOnClick,
                            text = "Next",
                            scrollState = rowScrollState,
                            scrollBy = screenWidth, width = 200.dp,
                            currentPositionState = currentPositionState
                        )
                    }
                    else{
                        actionButton(
                            action = {
                                events(RegistrationEvent.RegistrationNavigationClicked)
                            },
                            text = "Agree and Continue",
                            scrollState = rowScrollState,
                            scrollBy = screenWidth, width = 200.dp,
                            currentPositionState = currentPositionState
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        val agreement = buildAnnotatedString {
                            val str =
                                "By Tapping \"Agree and Continue\" above you agree to the Terms and Conditions and Privacy Policy of fov"
                            val startTerm = str.indexOf("Terms")
                            val endTerm = startTerm + 20
                            append(str)
                            addStyle(
                                SpanStyle(
                                    color = MaterialTheme.colors.primary
                                ), startTerm,endTerm
                            )
                            addStringAnnotation(
                                tag = "Terms",
                                annotation = "https://fov.com",
                                start = startTerm,
                                end = endTerm
                            )
                            val startPriv = str.indexOf("Privacy")
                            val endPriv = startPriv + 14
                            addStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.primary
                                ), start = startPriv, end = endPriv
                            )
                            addStringAnnotation(
                                tag = "Privacy",
                                annotation = "https://fov.com",
                                start = startPriv,
                                end = endPriv
                            )
                            toAnnotatedString()
                        }

                        ClickableTextLink(
                            agreement,
                            listOf("Terms","Privacy"),
                            Modifier.width(screenWidth/2)
                                .padding(bottom = commonPadding)
                        )

                    }

                }
            }
        }
    }

}

private fun getItems() : Array<OnboardingCard> {

    return arrayOf(
        OnboardingCard(
            R.drawable.ic_listen_songs,
            "Over 50 Million Songs",
            "You bring the passion. We bring the platform"
        ),
        OnboardingCard(
            R.drawable.ic_stay_connected,
            "Stay Connected With Friends",
            "Find New Friends and Chat in Room"
        ),
        OnboardingCard(
            R.drawable.ic_record_video,
            "Record Yourself In Video",
            "CreateOr Join Trending Challenges In Flick"
        ),



        )
}
@Composable
fun Logo(){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxWithConstraints() {
            Image(
                painter = painterResource(ThemeHelper.getLogoResource()),
                "Logo",
                modifier = Modifier.size(maxWidth/ 3)


            )
        }
    }
}

@Composable
private fun actionButton(action : () -> Unit,

                         scrollState: ScrollState,
                         scrollBy: Dp,
                         currentPositionState : MutableState<Int>,
                         text:String,
                         width: Dp
){

    BoxWithConstraints() {
        Button(
            shape = RoundedCornerShape(30),
            modifier = Modifier
                .width(maxWidth / 1.5f)
                .height(70.dp)
                .padding(all = 10.dp),

            onClick = {

                action()


            }) {

            Text(
                text,
                color = Color.White
            )


        }
    }
}
@Composable
private fun positionDots(numberOfDots : Int, currentPosition: Int = 1) {
    Row {

        for(i in 1..numberOfDots){
            var boxColor =  Color.Gray
            if(i == currentPosition) boxColor = MaterialTheme.colors.primary
            Box(modifier = Modifier
                .clip(CircleShape)){

                Box(modifier = Modifier
                    .size(10.dp)
                    .background(boxColor))
            }
            if(i != numberOfDots)
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        }



    }
}

@Composable
fun MainCard(resourceId : Int, cardHeader : String, cardDetails : String) {


    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painterResource(id = resourceId),
            "resource")
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = cardHeader,
            style = MaterialTheme.typography.h5.copy(
                MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = cardDetails,
            style = MaterialTheme.typography.caption, textAlign = TextAlign.Center
        )



    }

}
