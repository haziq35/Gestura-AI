package com.example.gesturaai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gesturaai.ui.theme.GesturaAITheme

private val MainBackground = Color(0xFFF3F4EF)
private val MainAccent = Color(0xFF74B8B1)
private val TextPrimary = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF6B6B6B)
private val NavUnselected = Color(0xFF8A8A8A)

private val ContentMaxWidth = 420.dp
private val HomeHorizontalPadding = 24.dp
private val CtaHeight = 64.dp
private val NavIconSize = 26.sp
private val NavLabelSize = 13.sp

enum class MainTab {
    Home,
    Translate,
    Learning,
    Profile
}

@Composable
fun MainPageScreen(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    onStartTranslatingClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var translateMode by remember { mutableStateOf(TranslateMode.SentenceToSign) }
    var translateInput by remember { mutableStateOf("") }
    var signToTextOutput by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MainBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 3.dp,
                modifier = Modifier
                    .heightIn(min = 80.dp)
                    .padding(top = 4.dp, bottom = 8.dp)
            ) {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { onTabSelected(tab) },
                        icon = {
                             Text(
                                text = iconForTab(tab),
                                fontSize = NavIconSize,
                                lineHeight = NavIconSize
                            )
                        },
                        label = {
                            Text(
                                text = labelForTab(tab),
                                fontSize = NavLabelSize,
                                fontWeight = if (selectedTab == tab) FontWeight.SemiBold else FontWeight.Medium
                            )
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MainAccent,
                            selectedTextColor = MainAccent,
                            indicatorColor = MainAccent.copy(alpha = 0.2f),
                            unselectedIconColor = NavUnselected,
                            unselectedTextColor = NavUnselected
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            MainTab.Home -> HomePageContent(
                onStartTranslatingClick = onStartTranslatingClick,
                modifier = Modifier.padding(innerPadding)
            )

            MainTab.Translate -> TranslateTabContent(
                mode = translateMode,
                onModeChange = { translateMode = it },
                textInput = translateInput,
                onTextChange = { translateInput = it },
                signToTextOutput = signToTextOutput,
                onSignToTextOutputChange = { signToTextOutput = it },
                modifier = Modifier.padding(innerPadding)
            )

            MainTab.Learning -> SimpleTabPage(
                title = stringResource(R.string.tab_learning),
                subtitle = stringResource(R.string.learning_page_text),
                modifier = Modifier.padding(innerPadding)
            )

            MainTab.Profile -> ProfileScreen(
                onLogoutClick = onLogoutClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun HomePageContent(
    onStartTranslatingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = ContentMaxWidth)
                .padding(
                    horizontal = HomeHorizontalPadding,
                    vertical = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wave),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            letterSpacing = (-0.2).sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.login_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFE8F4F2)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wave),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 32.dp, vertical = 20.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.main_title),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    lineHeight = 32.sp,
                    letterSpacing = (-0.3).sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.main_title_line_2),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF757575)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.main_subtitle),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 26.sp,
                    color = MainAccent
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onStartTranslatingClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CtaHeight),
                shape = RoundedCornerShape(32.dp),
                contentPadding = PaddingValues(horizontal = 36.dp, vertical = 12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 3.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainAccent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.start_translating_ms),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 0.3.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}

@Composable
private fun SimpleTabPage(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = ContentMaxWidth)
                .padding(horizontal = HomeHorizontalPadding, vertical = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    lineHeight = 34.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp,
                    color = TextSecondary
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun iconForTab(tab: MainTab): String = when (tab) {
    MainTab.Home -> "\u2302"
    MainTab.Translate -> "\u21c6"
    MainTab.Learning -> "\u25a3"
    MainTab.Profile -> "\u25cf"
}

@Composable
private fun labelForTab(tab: MainTab): String = when (tab) {
    MainTab.Home -> stringResource(R.string.tab_home)
    MainTab.Translate -> stringResource(R.string.tab_translate)
    MainTab.Learning -> stringResource(R.string.tab_learning)
    MainTab.Profile -> stringResource(R.string.tab_profile)
}

@Preview(showBackground = true)
@Composable
private fun MainPagePreview() {
    GesturaAITheme {
        MainPageScreen(
            selectedTab = MainTab.Home,
            onTabSelected = {},
            onStartTranslatingClick = {},
            onLogoutClick = {}
        )
    }
}
