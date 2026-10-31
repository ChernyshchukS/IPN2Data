package pp.chsv.ipn2data

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import pp.chsv.ipn2data.ui.navigation.AppNavigation
import pp.chsv.ipn2data.ui.theme.IPN2DataTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            IPN2DataTheme {

                AppNavigation()

            }
        }
    }
}