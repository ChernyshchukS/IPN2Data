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
import androidx.compose.ui.unit.dp


@Composable
fun ValidateScreen(
    onShowResult: (String) -> Unit
) {

    var rnokpp by rememberSaveable {
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
            text = "Проверка ИПН"
        )

        OutlinedTextField(

            value = rnokpp,

            onValueChange = {
                rnokpp = it
            },

            label = {
                Text("Введите ИПН")
            },

            singleLine = true
        )

        Button(

            onClick = {
                onShowResult(rnokpp)
            },

            enabled = rnokpp.isNotBlank(),

            modifier = Modifier.padding(top = 16.dp)

        ) {

            Text("Проверить")
        }
    }
}