package pp.chsv.ipn2data.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pp.chsv.ipn2data.R
import pp.chsv.rnokpp.Rnokpp

import java.time.Instant

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


private val dateFormatter =
    DateTimeFormatter.ISO_LOCAL_DATE

/**
 * Экран генерации номера.
 */
@Composable
fun GenerateScreen(
    generateRnokpp: (
        LocalDate,
        Int,
        Int
    ) -> Rnokpp,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    // --------------------------------
    // УПРАВЛЕНИЕ ФОКУСОМ
    // --------------------------------

    val genderFocusRequester = remember {
        FocusRequester()
    }

    val sequenceFocusRequester = remember {
        FocusRequester()
    }

    // --------------------------------
    // СОСТОЯНИЕ ПОЛЕЙ
    // --------------------------------

    var birthDate by rememberSaveable {
        mutableStateOf<LocalDate?>(null)
    }

    var sequenceNumber by rememberSaveable {
        mutableStateOf("")
    }

    var genderDigit by rememberSaveable {
        mutableStateOf("")
    }

    var generatedRnokpp by remember {
        mutableStateOf<Rnokpp?>(null)
    }

    var generationError by remember {
        mutableStateOf<String?>(null)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    // -------------------------------
    // DATE PICKER
    // -------------------------------

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthDate
            ?.atStartOfDay()
            ?.toInstant(ZoneOffset.UTC)
            ?.toEpochMilli(),
        initialDisplayMode = DisplayMode.Input
    )

    // --------------------------------
    // ПРОВЕРКА ДАННЫХ
    // --------------------------------

    val isSequenceValid =
        sequenceNumber.length == 3 &&
                sequenceNumber.all { it in '0'..'9' }

    val isGenderValid =
        genderDigit.length == 1 &&
                genderDigit[0] in '0'..'9'

    val genderHint = when {

        !isGenderValid ->
            stringResource(R.string.generate_isGenderValid)

        genderDigit.toInt() % 2 == 0 ->
            stringResource(R.string.app_genderFemale)

        else ->
            stringResource(R.string.app_genderMale)
    }

    val canGenerate =
        birthDate != null &&
                isSequenceValid &&
                isGenderValid

    // -------------------------------
    // DATE PICKER DIALOG
    // -------------------------------

    if (showDatePicker) {

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState
                            .selectedDateMillis
                            ?.let { millis ->

                                birthDate =
                                    Instant.ofEpochMilli(millis)
                                        .atZone(ZoneOffset.UTC)
                                        .toLocalDate()

                                generatedRnokpp = null
                                generationError = null
                            }

                        showDatePicker = false

                        if (birthDate != null) {
                            sequenceFocusRequester.requestFocus()
                        }
                    }
                ) {
                    Text(
                        stringResource(R.string.generate_datePickerOk)
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(
                        stringResource(R.string.generate_datePickerCancel)
                    )
                }
            }
        ) {

            DatePicker(
                state = datePickerState,
                showModeToggle = true
            )
        }
    }

    // --------------------------------
    // ИНТЕРФЕЙС
    // --------------------------------

    Column(

        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // -------------------------------
        // Заголовок
        // -------------------------------

        Text(
            text = stringResource(R.string.generate_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // -------------------------------
        // Поля ввода
        // -------------------------------

        Text(
            text = stringResource(R.string.generate_description),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------
        // 1. ДАТА РОЖДЕНИЯ
        // -------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePicker = true
                }
        ) {

            OutlinedTextField(
                value = birthDate?.format(dateFormatter) ?: "",

                onValueChange = {},

                label = {
                    Text(
                        stringResource(R.string.generate_dateOfBirth)
                    )
                },

                placeholder = {
                    Text(
                        stringResource(R.string.generate_dateOfBirth_placeHolder)
                    )
                },

                supportingText = {
                    Text(
                        stringResource(R.string.generate_dateOfBirth_supportingText)
                    )
                },

                readOnly = true,

                enabled = false,

                modifier = Modifier.fillMaxWidth(),

                colors =
                    OutlinedTextFieldDefaults.colors(
                        disabledTextColor =
                            MaterialTheme.colorScheme.onSurface,

                        disabledBorderColor =
                            MaterialTheme.colorScheme.outline,

                        disabledLabelColor =
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        // Цвет supportingText как у активного поля
                        disabledSupportingTextColor =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------
        // 2. ПОРЯДКОВЫЙ НОМЕР
        // -------------------------------

        OutlinedTextField(
            value = sequenceNumber,

            onValueChange = { newValue ->

                val filtered = newValue
                    .filter { it in '0'..'9' }
                    .take(3)

                val previousLength =
                    sequenceNumber.length

                sequenceNumber = filtered

                generatedRnokpp = null
                generationError = null

                // Автоматический переход
                // после третьей цифры

                if (
                    filtered.length == 3 &&
                    previousLength < 3
                ) {

                    genderFocusRequester
                        .requestFocus()
                }
            },

            label = {
                Text(
                    stringResource(R.string.generate_sequenceNumber)
                )
            },

            placeholder = {
                Text(
                    stringResource(R.string.generate_sequenceNumber_placeHolder)
                )
            },

            supportingText = {
                Text(
                    stringResource(R.string.generate_sequenceNumber_supportingText)
                )
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number,
                    imeAction =
                        ImeAction.Next
                ),

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(
                    sequenceFocusRequester
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------
        // 3. ЦИФРА ПОЛА
        // -------------------------------

        OutlinedTextField(
            value = genderDigit,

            onValueChange = { newValue ->

                genderDigit = newValue
                    .filter { it in '0'..'9' }
                    .take(1)

                generatedRnokpp = null
                generationError = null
            },

            label = {
                Text(
                    stringResource(R.string.generate_genderDigit)
                )
            },

            placeholder = {
                Text(
                    stringResource(R.string.generate_genderDigit_placeholder)
                )
            },

            supportingText = {

                Text(
                    text = genderHint,

                    color =
                        if (isGenderValid)
                            MaterialTheme
                                .colorScheme.primary
                        else
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                )
            },

            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number,
                    imeAction =
                        ImeAction.Done
                ),

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(
                    genderFocusRequester
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------
        // КНОПКА ГЕНЕРАЦИИ
        // -------------------------------

        Button(
            onClick = {

                val date = birthDate
                    ?: return@Button

                focusManager.clearFocus()

                try {

                    generatedRnokpp =
                        generateRnokpp(
                            date,
                            sequenceNumber.toInt(),
                            genderDigit.toInt()
                        )

                    generationError = null

                } catch (e: Exception) {

                    generatedRnokpp = null

                    generationError =
                        (e.message ?: R.string.generate_error) as String?
                }
            },

            enabled = canGenerate,

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {

            Text(
                stringResource(R.string.generate_button)
            )
        }

        // -------------------------------
        // ОШИБКА
        // -------------------------------

        generationError?.let { error ->

            Text(
                text = error,
                color =
                    MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -------------------------------
        // КАРТОЧКА РЕЗУЛЬТАТА
        // -------------------------------

        generatedRnokpp?.let { rnokpp ->
            GenerateResultCard(rnokpp = rnokpp)
        }

    }
}

// -------------------------------
// Карточка результата
// -------------------------------

@Composable
private fun GenerateResultCard(
    rnokpp: Rnokpp,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(R.string.generate_generatedNumber),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            HorizontalDivider()

            Text(
                text = rnokpp.number,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // -------------------------------
            // КНОПКА КОПИРОВАНИЯ
            // -------------------------------
            val clipboardLabel = stringResource(R.string.generate_number)
            val copiedMessage = stringResource(R.string.generate_numberCopied)

            OutlinedButton(
                onClick = {
                    val clipboard = context.getSystemService(
                        Context.CLIPBOARD_SERVICE
                    ) as ClipboardManager

                    clipboard.setPrimaryClip(
                        ClipData.newPlainText(clipboardLabel, rnokpp.number)
                    )

                    if (Build.VERSION.SDK_INT < 33) {
                        Toast.makeText(
                            context,
                            copiedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.generate_btnCopyNumber)
                )
            }
        }
    }
}