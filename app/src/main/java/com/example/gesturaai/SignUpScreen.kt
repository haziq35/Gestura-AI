package com.example.gesturaai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gesturaai.ui.theme.GesturaAITheme

private val SignUpScreenBackground = Color(0xFFF3F4EF)
private val SignUpPanelBackground = Color(0xFFEFEFEF)
private val SignUpAccentTeal = Color(0xFF74B8B1)
private val SignUpButtonGreen = Color(0xFF97B99E)
private val SignUpButtonWidth = 170.dp
private val SignUpButtonHeight = 38.dp

@Composable
fun SignUpScreen(
    onRegisterClick: (fullName: String, email: String, password: String, confirmPassword: String) -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SignUpScreenBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpIconBadge()
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF111111),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.register_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = SignUpAccentTeal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SignUpPanelBackground,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                RegisterTextField(
                    label = stringResource(R.string.full_name),
                    value = fullName,
                    onValueChange = { fullName = it },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                RegisterTextField(
                    label = stringResource(R.string.email),
                    value = email,
                    onValueChange = { email = it },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                RegisterTextField(
                    label = stringResource(R.string.password),
                    value = password,
                    onValueChange = { password = it },
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                RegisterTextField(
                    label = stringResource(R.string.confirm_password),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    onImeAction = {
                        focusManager.clearFocus()
                        onRegisterClick(fullName.trim(), email.trim(), password, confirmPassword)
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = { onRegisterClick(fullName.trim(), email.trim(), password, confirmPassword) },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .width(SignUpButtonWidth)
                        .height(SignUpButtonHeight),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SignUpButtonGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onBackToLoginClick,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .width(SignUpButtonWidth)
                        .height(SignUpButtonHeight),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.back_to_login),
                        fontSize = 11.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Composable
private fun RegisterTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    onImeAction: KeyboardActionScope.() -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF262626)
    )
    Spacer(modifier = Modifier.height(6.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(50),
        textStyle = TextStyle(color = Color(0xFF2C2C2C)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF2C2C2C),
            unfocusedTextColor = Color(0xFF2C2C2C),
            focusedContainerColor = Color(0xFFD8DBDE),
            unfocusedContainerColor = Color(0xFFD8DBDE),
            focusedIndicatorColor = Color(0xFF646464),
            unfocusedIndicatorColor = Color(0xFF646464),
            cursorColor = AccentTeal
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = when (imeAction) {
            ImeAction.Next -> KeyboardActions(onNext = onImeAction)
            ImeAction.Done -> KeyboardActions(onDone = onImeAction)
            else -> KeyboardActions()
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun SignUpIconBadge() {
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

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    GesturaAITheme {
        SignUpScreen(
            onRegisterClick = { _, _, _, _ -> },
            onBackToLoginClick = {}
        )
    }
}
