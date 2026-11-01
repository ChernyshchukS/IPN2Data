package pp.chsv.ipn2data.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import pp.chsv.rnokpp.Rnokpp
import pp.chsv.ipn2data.R


@Composable
fun ValidateScreen(
    onShowResult: (String) -> Unit
) {

    var inputNumber by rememberSaveable {
        mutableStateOf("")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = stringResource(R.string.validate_title),
        )

        OutlinedTextField(

            value = inputNumber,

            onValueChange = {
                inputNumber = it
            },

            label = {
                Text(stringResource(R.string.validate_hint))
            },

            singleLine = true
        )

        Button(

            onClick = {
                val result = runCatching {
                    Rnokpp.parse(inputNumber)
                }

                result.onSuccess { rnokpp ->
                    // Номер имеет правильный формат.
                    onShowResult(inputNumber)
// TODO: вывод в этом же окне rnokpp
                }

                result.onFailure { error ->
                    // Ошибка формата.
                    inputNumber = error.message!!
                }
            },

            enabled = inputNumber.isNotBlank(),

            modifier = Modifier.padding(top = 16.dp)

        ) {

            Text(stringResource(R.string.validate_button))
        }
    }
}