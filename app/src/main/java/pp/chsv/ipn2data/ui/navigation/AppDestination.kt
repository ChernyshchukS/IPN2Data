package pp.chsv.ipn2data.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {

    @Serializable
    data object Decode : AppDestination

    @Serializable
    data object Generate : AppDestination

    @Serializable
    data object About : AppDestination

}