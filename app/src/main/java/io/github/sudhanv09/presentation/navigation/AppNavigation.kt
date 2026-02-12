package io.github.sudhanv09.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.sudhanv09.presentation.accounts.AccountDetailScreen
import io.github.sudhanv09.presentation.accounts.AccountsScreen
import io.github.sudhanv09.presentation.accounts.AddEditAccountScreen
import io.github.sudhanv09.presentation.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToAccounts = { navController.navigate(Accounts) },
                onNavigateToAddTransaction = { }
            )
        }

        composable<Accounts> {
            AccountsScreen(
                onNavigateToAddAccount = { navController.navigate(AddAccount) },
                onNavigateToAccountDetail = { accountId ->
                    navController.navigate(AccountDetail(accountId))
                }
            )
        }

        composable<AddAccount> {
            AddEditAccountScreen(
                accountId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<AccountDetail> { backStackEntry ->
            val route: AccountDetail = backStackEntry.toRoute()
            AccountDetailScreen(
                accountId = route.accountId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { accountId ->
                    navController.navigate(EditAccount(accountId))
                }
            )
        }

        composable<EditAccount>{ backStackEntry ->
            val route: EditAccount = backStackEntry.toRoute()
            AddEditAccountScreen(
                accountId = route.accountId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
