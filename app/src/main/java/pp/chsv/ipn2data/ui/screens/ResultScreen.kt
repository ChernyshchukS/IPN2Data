package pp.chsv.ipn2data.ui.screens

import android.util.Log
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
import pp.chsv.rnokpp.Rnokpp


@Composable
fun ResultScreen(
    rnokpp: String,
    onBack: () -> Unit
) {

    val result = if (Rnokpp.validate(rnokpp) == -1) {
        "Контрольная цифра правильная."
    } else {
        "Правильная контрольная цифра: " + Rnokpp.validate(rnokpp)
    }

    Log.d("RNOKPP", "Результат: $result")
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
            text = result,
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