package com.example.gesturaai

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.gesturaai.ui.theme.GesturaAITheme

private val ScreenBackground = Color(0xFFF3F4EF)
private val PanelBackground = Color(0xFFEFEFEF)
val AccentTeal = Color(0xFF74B8B1)
private val LoginGreen = Color(0xFF97B99E)
private val AuthButtonWidth = 138.dp
private val AuthButtonHeight = 38.dp

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = ScreenBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppIconBadge()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF111111),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.login_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(22.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PanelBackground,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 22.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF262626)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.email_placeholder),
                            color = AccentTeal
                        )
                    },
                    textStyle = TextStyle(color = AccentTeal),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = textFieldColors(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.password),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF262626)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "••••••",
                            color = AccentTeal
                        )
                    },
                    textStyle = TextStyle(color = AccentTeal),
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = textFieldColors(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onLoginClick(email.trim(), password)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onLoginClick(email.trim(), password) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(AuthButtonWidth)
                        .height(AuthButtonHeight),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LoginGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(AuthButtonWidth)
                        .height(AuthButtonHeight),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontSize = 11.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.admin_login),
                color = AccentTeal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
private fun AppIconBadge() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(Color(0xFFEDEDE8), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.wave),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(46.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = AccentTeal,
    unfocusedTextColor = AccentTeal,
    focusedContainerColor = Color(0xFFF6F6F6),
    unfocusedContainerColor = Color(0xFFF6F6F6),
    focusedIndicatorColor = Color(0xFF5A5A5A),
    unfocusedIndicatorColor = Color(0xFF5A5A5A),
    cursorColor = AccentTeal,
    focusedPlaceholderColor = AccentTeal,
    unfocusedPlaceholderColor = AccentTeal
)

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    GesturaAITheme {
        LoginScreen(
            onLoginClick = { _, _ -> },
            onSignUpClick = { }
        )
    }
}
