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
import io.github.sudhanv09.presentation.categories.AddEditCategoryScreen
import io.github.sudhanv09.presentation.categories.CategoriesScreen
import io.github.sudhanv09.presentation.home.HomeScreen
import io.github.sudhanv09.presentation.transactions.AddTransactionScreen
import io.github.sudhanv09.presentation.transactions.TransactionDetailScreen
import io.github.sudhanv09.presentation.transactions.TransactionListScreen

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
                onNavigateToTransactions = { navController.navigate(Transactions) },
                onNavigateToCategories = { navController.navigate(Categories) },
                onNavigateToAddTransaction = { navController.navigate(AddTransaction) }
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

        composable<Categories> {
            CategoriesScreen(
                onNavigateToAddCategory = { navController.navigate(AddCategory) }
            )
        }

        composable<AddCategory> {
            AddEditCategoryScreen(
                categoryId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Transactions> {
            TransactionListScreen(
                onNavigateToAddTransaction = { navController.navigate(AddTransaction) },
                onNavigateToTransactionDetail = { transactionId ->
                    navController.navigate(TransactionDetail(transactionId))
                }
            )
        }

        composable<AddTransaction> {
            AddTransactionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<TransactionDetail> { backStackEntry ->
            val route: TransactionDetail = backStackEntry.toRoute()
            TransactionDetailScreen(
                transactionId = route.transactionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
