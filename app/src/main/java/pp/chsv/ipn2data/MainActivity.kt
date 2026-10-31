package pp.chsv.ipn2data

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import pp.chsv.ipn2data.ui.navigation.AppNavigation
import pp.chsv.ipn2data.ui.theme.IPN2DataTheme
import pp.chsv.rnokpp.Rnokpp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            IPN2DataTheme {

                AppNavigation()

            }
        }
        val result = Rnokpp.validate("1234567890")

        Log.d("RNOKPP", "Результат: $result")
    }
}