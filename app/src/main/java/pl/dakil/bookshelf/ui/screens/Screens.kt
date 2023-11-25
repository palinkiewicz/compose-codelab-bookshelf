package pl.dakil.bookshelf.ui.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(val route: String, val navArguments: List<NamedNavArgument> = emptyList()) {
    object VolumeList : Screens(route = "volumeList")

    object VolumeDetail : Screens(
        route = "volumeDetail/{volumeId}",
        navArguments = listOf(navArgument("volumeId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(volumeId: String) = "volumeDetail/${volumeId}"
    }
}