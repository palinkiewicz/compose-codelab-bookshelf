package pl.dakil.bookshelf.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.dakil.bookshelf.ui.screens.Screens
import pl.dakil.bookshelf.ui.screens.volumelist.VolumeListScreen

@Composable
fun BookshelfApp() {
    val navController = rememberNavController()
    BookshelfNavHost(navController = navController)
}

@Composable
fun BookshelfNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.VolumeList.route) {
        composable(
            route = Screens.VolumeList.route,
            arguments = Screens.VolumeList.navArguments
        ) {
            VolumeListScreen(onListItemClick = { volumeId ->
                navController.navigate(Screens.VolumeDetail.createRoute(volumeId))
            })
        }
        composable(
            route = Screens.VolumeDetail.route,
            arguments = Screens.VolumeDetail.navArguments
        ) {
            // VolumeDetail()
        }
    }
}