package pp.chsv.ipn2data.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pp.chsv.ipn2data.R
import pp.chsv.rnokpp.Rnokpp


// --------------------------------------------------
// Экран "Генерация"
// --------------------------------------------------

@Composable
fun GenerateScreen(
    modifier: Modifier = Modifier
) {

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
            text = stringResource(R.string.generate_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // Поле ввода
        // --------------------------------------------------


        Spacer(modifier = Modifier.height(16.dp))

        // --------------------------------------------------
        // Кнопка декодирования
        // --------------------------------------------------

        Spacer(modifier = Modifier.height(24.dp))

        // --------------------------------------------------
        // Результат
        // --------------------------------------------------

    }
}


// --------------------------------------------------
// Карточка результата
// --------------------------------------------------

@Composable
private fun GenerateResultCard(
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
                text = "Новый номер",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            HorizontalDivider()

            Text(
                text = "Новый номер"
            )

            Text(
                text = "Новый номер"
            )

            Text(
                text = "Новый номер"
            )

        }
    }
}