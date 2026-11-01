package pp.chsv.ipn2data.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pp.chsv.ipn2data.R
import pp.chsv.rnokpp.Rnokpp


@Composable
fun ResultScreen(
    inputNumber: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val rnokpp = Rnokpp.parse(inputNumber)
    Log.d(
        "RNOKPP", stringResource(
            R.string.result_birthDate,
            rnokpp.birthDate
        )
    )
    Log.d(
        "RNOKPP", stringResource(
            R.string.result_sequence,
            rnokpp.sequence
        )
    )
    Log.d(
        "RNOKPP", stringResource(
            R.string.result_gender,
            rnokpp.gender
        )
    )
    Log.d(
        "RNOKPP", stringResource(
            R.string.result_controlDigit,
            rnokpp.controlDigit
        )
    )
    Log.d(
        "RNOKPP", stringResource(
            R.string.result_expectedControlDigit,
            rnokpp.expectedControlDigit
        )
    )

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

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
                        R.string.result_title,
                        rnokpp.number
                    )
                )

                Text(
                    text = stringResource(
                        R.string.result_birthDate,
                        rnokpp.birthDate
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = stringResource(
                        R.string.result_sequence,
                        rnokpp.sequence
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = stringResource(
                        R.string.result_gender,
                        rnokpp.gender
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = if (rnokpp.isValid)
                        stringResource(
                            R.string.result_controlDigit,
                            rnokpp.controlDigit
                        )
                    else
                        stringResource(
                            R.string.result_expectedControlDigit,
                            rnokpp.expectedControlDigit
                        ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            Button(

                onClick = onBack,

                modifier = Modifier.fillMaxWidth()

            ) {

                Text(
                    stringResource(R.string.result_back)
                )
            }
        }
    }
}