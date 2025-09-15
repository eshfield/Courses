package com.example.courses.feature.signin.presentation.ui

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.courses.R
import com.example.courses.core.presentation.ui.theme.CoursesTheme
import com.example.courses.feature.signin.presentation.FormData
import com.example.courses.feature.signin.presentation.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(onSignIn: () -> Unit) {
    val viewModel = koinViewModel<SignInViewModel>()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    SignInScreen(
        formData = formState,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onSignIn = onSignIn,
    )
}

@Composable
fun SignInScreen(
    formData: FormData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(100.dp))
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Start),
        )
        Spacer(Modifier.height(28.dp))
        SignInForm(
            formData = formData,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onSignIn = onSignIn,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.medium)))
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.small),
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.no_account),
            )
            Text(
                text = stringResource(R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = {}),
            )
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.small)))
        Text(
            text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = {}),
        )
        Spacer(Modifier.height(32.dp))
        HorizontalDivider()
        Spacer(Modifier.height(32.dp))
        SocialButtons()
        Spacer(Modifier.height(dimensionResource(R.dimen.medium)))
    }
}

@Composable
fun SignInForm(
    formData: FormData,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.email_field_label),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.small)))
        InputField(
            value = formData.email,
            onValueChange = { onEmailChange(it) },
            placeholderText = stringResource(R.string.email_field_placeholder),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.medium)))
        Text(
            text = stringResource(R.string.password_field_label),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.small)))
        InputField(
            value = formData.password,
            onValueChange = { onPasswordChange(it) },
            placeholderText = stringResource(R.string.password_field_placeholder),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onSignIn,
            enabled = formData.isFormValid,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.sign_in_button),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground,
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = modifier,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(dimensionResource(R.dimen.sign_in_field_corner)),
            contentPadding = PaddingValues(
                horizontal = dimensionResource(R.dimen.medium),
                vertical = dimensionResource(R.dimen.small),
            ),
            placeholder = {
                Text(
                    text = placeholderText,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5F),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
    }
}

@Composable
fun SocialButtons(
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.medium),
        ),
        modifier = modifier,
    ) {
        SocialButton(
            icon = R.drawable.vk,
            colors = listOf(Color(0xFF2683ED), Color(0xFF2683ED)),
            url = "https://vk.com/",
            modifier = Modifier.weight(1F),
        )
        SocialButton(
            icon = R.drawable.ok,
            colors = listOf(Color(0xFFF98509), Color(0xFFF95D00)),
            url = "https://ok.ru/",
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
fun SocialButton(
    @DrawableRes icon: Int,
    colors: List<Color>,
    url: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(dimensionResource(R.dimen.social_button_height))
            .clip(
                RoundedCornerShape(
                    dimensionResource(R.dimen.social_button_corner),
                )
            )
            .background(brush = Brush.verticalGradient(colors = colors))
            .clickable(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
            ),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    CoursesTheme {
        SignInScreen(
            formData = FormData(),
            onEmailChange = {},
            onPasswordChange = {},
            onSignIn = {},
        )
    }
}
