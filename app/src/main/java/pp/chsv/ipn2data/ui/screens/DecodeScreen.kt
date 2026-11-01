package pp.chsv.ipn2data.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pp.chsv.ipn2data.R
import pp.chsv.rnokpp.Gender

import pp.chsv.rnokpp.Rnokpp


// --------------------------------------------------
// Экран "Декодирование"
// --------------------------------------------------

@Composable
fun DecodeScreen(
    modifier: Modifier = Modifier
) {

    // Введённый номер
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }

    // Результат расшифровки
    var convertedValue by remember {
        mutableStateOf<Rnokpp?>(null)
    }

    // Сообщение об ошибке
    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    // Проверка формата
    val isValid = inputValue.length == 10 &&
            inputValue.all(Char::isDigit) &&
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
            text = stringResource(R.string.decode_title),
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

                    // Сброс предыдущего результата
                    convertedValue = null
                    errorMessage = null
                }
            },

            label = {
                Text(stringResource(R.string.decode_textFieldLabel))
            },

//            placeholder = {
//                Text(stringResource(R.string.decode_textFieldPlaceholder))
//            },

            supportingText = {

                when {

                    errorMessage != null -> {
                        Text(errorMessage!!)
                    }

                    inputValue.isEmpty() -> {
                        Text(stringResource(R.string.decode_textFieldSupportingTextIsEmpty))
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
                        Text(stringResource(R.string.decode_textFieldSupportingTextIsValid))
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
        // Кнопка декодирования
        // --------------------------------------------------

        Button(
            onClick = {

                runCatching {

                    Rnokpp.parse(inputValue)

                }.onSuccess { rnokpp ->

                    convertedValue = rnokpp
                    errorMessage = null

                }.onFailure { error ->

                    convertedValue = null

                    errorMessage =
//                        error.message ?: "Ошибка декодирования номера"
                        (error.message ?: R.string.decode_buttonErrorMessage) as String?
                }
            },

            enabled = isValid,

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(stringResource(R.string.decode_buttonText))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // Результат
        // --------------------------------------------------

        convertedValue?.let { rnokpp ->

            DecodeResultCard(
                rnokpp = rnokpp
            )
        }
    }
}


// --------------------------------------------------
// Карточка результата
// --------------------------------------------------

@Composable
private fun DecodeResultCard(
    rnokpp: Rnokpp,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(
                    R.string.decode_result_title,
                    rnokpp.number
                ),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            HorizontalDivider()

            Text(
                text = stringResource(
                    pp.chsv.ipn2data.R.string.decode_result_birthDate,
                    rnokpp.birthDate
                )
            )

            Text(
                text = stringResource(
                    pp.chsv.ipn2data.R.string.decode_result_sequence,
                    rnokpp.sequence
                )
            )

            Text(
                text = stringResource(
                    pp.chsv.ipn2data.R.string.decode_result_gender,
                    when (rnokpp.gender) {
                        Gender.MALE -> stringResource(R.string.decode_result_genderMale)
                        Gender.FEMALE -> stringResource(R.string.decode_result_genderFemale)
                    }
                )
            )

            Text(
                text = if (rnokpp.isValid)
                    stringResource(
                        pp.chsv.ipn2data.R.string.decode_result_controlDigit,
                        rnokpp.controlDigit
                    )
                else
                    stringResource(
                        R.string.decode_result_expectedControlDigit,
                        rnokpp.expectedControlDigit
                    )
            )
        }
    }
}