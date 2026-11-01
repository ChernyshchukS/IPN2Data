package pp.chsv.ipn2data.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pp.chsv.rnokpp.Rnokpp
import pp.chsv.ipn2data.R


@Composable
fun ValidateScreen(
    onShowResult: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // Введённый номер
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }

    // Сообщение об ошибке
    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    // Проверка формата
    val isValid = inputValue.length == 10 &&
            inputValue.all { it in '0'..'9' }

    Column(

        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // Заголовок
        // --------------------------------------------------

        Text(
            text = stringResource(R.string.validate_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // Поле ввода
        // --------------------------------------------------

        OutlinedTextField(

            value = inputValue,

            onValueChange = { newValue ->
                if (
                    newValue.length <= 10 &&
                    newValue.all { it in '0'..'9' }
                ) {
                    inputValue = newValue
                    errorMessage = null
                } else if (newValue.length > 10) {
                    inputValue = newValue
                    errorMessage = "Длина номера не больше 10 цифр"

                } else if (!newValue.all { it in '0'..'9' }) {
                    inputValue = newValue
                    errorMessage = "Номер должен состоять из цифр"
                }
            },

            label = {
                Text(stringResource(R.string.validate_hint))
            },

            supportingText = {
                when {
                    errorMessage != null -> {
                        Text(errorMessage!!)
                    }

                    inputValue.length < 10 -> {
                        Text(
                            stringResource(
                                R.string.decode_textFieldSupportingTextLengthLs10,
                                inputValue.length
                            )
                        )
                    }

                    isValid -> {
                        val test = Rnokpp.parse(inputValue)
                        Text(
                            if (inputValue[9].digitToInt() != test.expectedControlDigit)
                                "Контрольная цифра должна быть ${test.expectedControlDigit}"
                            else
                                "Номер имеет правильный формат"
                        )
                    }
                }
            },

            isError = errorMessage != null,

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --------------------------------------------------
        // Кнопка проверки
        // --------------------------------------------------

        Button(

            onClick = {
                val result = runCatching {
                    Rnokpp.parse(inputValue)
                }

                result.onSuccess { rnokpp ->
                    // Номер имеет правильный формат.
                    onShowResult(inputValue)
                }

                result.onFailure { error ->
                    // Ошибка формата.
                    errorMessage = error.message!!
                }
            },

            enabled = inputValue.isNotBlank(),

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(stringResource(R.string.validate_button))
        }
    }
}