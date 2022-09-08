package com.fov.authentication.ui.registration.registration

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalMaterialApi
@Composable
fun Onboarding(
    viewModel: RegistrationViewModel
){

    OnBoardingUI(events = viewModel::handleRegistrationEvent)
}
@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
private fun OnBoardingUI(
    events: (event: RegistrationEvent) -> Unit,
){

    Surface(color = MaterialTheme.colors.surface,
         modifier = Modifier
             .fillMaxHeight()
             .fillMaxWidth()

    ) {
        val pagerState = rememberPagerState( )

        Column() {
            if(pagerState.currentPage + 1 < pagerState.pageCount)
                Text(text = "Skip",
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { events(RegistrationEvent.RegistrationNavigationClicked) }
                )

            HorizontalPager(
                state = pagerState,
                count = pages.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) { page ->

                PageUI(page = pages[page])
            }

            HorizontalPagerIndicator(pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                activeColor = MaterialTheme.colors.onSurface
            )

            AnimatedVisibility(visible = pagerState.currentPage == 2 ) {
                OutlinedButton(shape = RoundedCornerShape(20.dp) ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),onClick = {
                            events(RegistrationEvent.RegistrationNavigationClicked)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.surface)) {
                    Text(text = "Get Started")
                }
            }

        }
    }

}



@Composable
fun PageUI(page: Page) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = page.title,
            fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = page.description,
            textAlign = TextAlign.Center,fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

    }
}

val pages = listOf(
    Page(
        "Listen to sermons",
        "Listen to Apostle's sermons at your comfort",
        R.drawable.ic_listen
    ),
    Page(
        "Watch sermons",
        "Watch Apostle's sermons at your comfort",
        R.drawable.ic_watch
    ),
    Page(
        "Get Latest News",
        "Follow Apostle through news",
        R.drawable.ic_apostle_news
    )
)

data class Page(val title: String,
                val description: String,
                @DrawableRes val image:Int)

