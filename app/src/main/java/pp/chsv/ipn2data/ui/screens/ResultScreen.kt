package pp.chsv.ipn2data.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ResultScreen(
    rnokpp: String,
    onBack: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "Результат проверки"
        )

        Text(
            text = "ИПН: $rnokpp",
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(

            onClick = onBack,

            modifier = Modifier.padding(top = 24.dp)

        ) {

            Text("Назад")
        }
    }
}