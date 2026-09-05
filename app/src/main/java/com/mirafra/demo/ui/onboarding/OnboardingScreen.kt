package com.mirafra.demo.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mirafra.demo.common.ui.PageIndicator
import com.mirafra.demo.common.ui.PrimaryButton
import com.mirafra.demo.common.ui.SecondaryButton
import com.mirafra.demo.navigation.Screen
import com.mirafra.demo.ui.theme.DemoTheme
import com.mirafra.demo.ui.theme.appColors
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavController) {
    val colors     = appColors()
    val pages      = onboardingPages
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope      = rememberCoroutineScope()

    val isFirstPage = pagerState.currentPage == 0
    val isLastPage  = pagerState.currentPage == pages.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.screenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.cardBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    if (isFirstPage) navController.popBackStack()
                    else scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = colors.primaryText
                    )
                }
            }

            TextButton(onClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = false }
                }
            }) {
                Text(
                    text = "Skip",
                    color = colors.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = page.illustrationRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = page.featureTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = page.featureSubtitle,
                    fontSize = 14.sp,
                    color = colors.secondaryText,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                SecondaryButton(
                    text = "Back",
                    enabled = !isFirstPage,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )

                PrimaryButton(
                    text = if (isLastPage) "Sign In" else "Next",
                    onClick = {
                        if (isLastPage) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = false }
                            }
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    DemoTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}