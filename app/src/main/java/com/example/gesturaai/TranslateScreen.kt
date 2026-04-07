package com.example.gesturaai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gesturaai.ui.theme.GesturaAITheme

private val PageBg = Color(0xFFF3F4EF)
private val Accent = Color(0xFF74B8B1)
private val TextDark = Color(0xFF1A1A1A)
private val Muted = Color(0xFF6B6B6B)
private val FieldBg = Color(0xFFF0F0F0)
private val CardStroke = Color(0xFF2C2C2C)

enum class TranslateMode {
    SentenceToSign,
    SignToSentence
}

@Composable
fun TranslateTabContent(
    mode: TranslateMode,
    onModeChange: (TranslateMode) -> Unit,
    textInput: String,
    onTextChange: (String) -> Unit,
    signToTextOutput: String,
    onSignToTextOutputChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PageBg)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .widthIn(max = 440.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.translate_screen_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = TextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ModeToggleRow(
            mode = mode,
            onModeChange = onModeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (mode) {
            TranslateMode.SentenceToSign -> {
                AvatarPlaceholderArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputCardSentenceToSign(
                    text = textInput,
                    onTextChange = onTextChange,
                    onVoiceClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.voice_coming_soon),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onTranslateClick = {
                        Toast.makeText(
                            context,
                            context.getString(
                                R.string.translate_submitted_toast,
                                textInput.ifBlank { "—" }
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            TranslateMode.SignToSentence -> {
                CameraSignPlaceholderArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputCardSignToSentence(
                    outputText = signToTextOutput,
                    onOutputChange = onSignToTextOutputChange,
                    onVoiceOrCameraClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.camera_feature_coming_soon),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onTranslateClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.sign_to_text_submitted_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun ModeToggleRow(
    mode: TranslateMode,
    onModeChange: (TranslateMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x1A74B8B1))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val sentenceActive = mode == TranslateMode.SentenceToSign
        val signActive = mode == TranslateMode.SignToSentence

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (sentenceActive) Accent else Color.Transparent)
                .clickable { onModeChange(TranslateMode.SentenceToSign) }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "T",
                    color = if (sentenceActive) Color.White else Accent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = stringResource(R.string.mode_sentence_to_sign),
                    color = if (sentenceActive) Color.White else Accent,
                    fontSize = 12.sp,
                    fontWeight = if (sentenceActive) FontWeight.SemiBold else FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (signActive) Accent else Color.Transparent)
                .clickable { onModeChange(TranslateMode.SignToSentence) }
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "\u25B6",
                    color = if (signActive) Color.White else Accent,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = stringResource(R.string.mode_sign_to_sentence),
                    color = if (signActive) Color.White else Accent,
                    fontSize = 12.sp,
                    fontWeight = if (signActive) FontWeight.SemiBold else FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun AvatarPlaceholderArea(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE5E8E8)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.wave),
            contentDescription = stringResource(R.string.avatar_placeholder_description),
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun CameraSignPlaceholderArea(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE5E8E8)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "\u25B6", fontSize = 40.sp, color = Accent)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.camera_placeholder_title),
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
private fun InputCardSentenceToSign(
    text: String,
    onTextChange: (String) -> Unit,
    onVoiceClick: () -> Unit,
    onTranslateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardStroke, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.translate_input_hint),
                    color = Muted
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = FieldBg,
                unfocusedContainerColor = FieldBg,
                focusedIndicatorColor = CardStroke,
                unfocusedIndicatorColor = CardStroke,
                cursorColor = Accent
            ),
            minLines = 3,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onTranslateClick() })
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE8E8E8))
                    .clickable(onClick = onVoiceClick),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\uD83C\uDFA4", fontSize = 22.sp)
            }

            Button(
                onClick = onTranslateClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "\u27A4", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(R.string.translate_to_sign_action),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Composable
private fun InputCardSignToSentence(
    outputText: String,
    onOutputChange: (String) -> Unit,
    onVoiceOrCameraClick: () -> Unit,
    onTranslateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardStroke, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = outputText,
            onValueChange = onOutputChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.sign_to_text_output_hint),
                    color = Muted
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = FieldBg,
                unfocusedContainerColor = FieldBg,
                focusedIndicatorColor = CardStroke,
                unfocusedIndicatorColor = CardStroke,
                cursorColor = Accent
            ),
            minLines = 3
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE8E8E8))
                    .clickable(onClick = onVoiceOrCameraClick),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\uD83D\uDCF9", fontSize = 20.sp)
            }

            Button(
                onClick = onTranslateClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "\u27A4", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(R.string.translate_to_text_action),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TranslateTabPreview() {
    GesturaAITheme {
        TranslateTabContent(
            mode = TranslateMode.SentenceToSign,
            onModeChange = {},
            textInput = "",
            onTextChange = {},
            signToTextOutput = "",
            onSignToTextOutputChange = {}
        )
    }
}
