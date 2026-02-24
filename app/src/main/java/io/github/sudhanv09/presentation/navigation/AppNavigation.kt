package io.github.sudhanv09.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import io.github.sudhanv09.presentation.accounts.AccountDetailScreen
import io.github.sudhanv09.presentation.accounts.AccountsScreen
import io.github.sudhanv09.presentation.accounts.AddEditAccountScreen
import io.github.sudhanv09.presentation.categories.AddEditCategoryScreen
import io.github.sudhanv09.presentation.categories.CategoriesScreen
import io.github.sudhanv09.presentation.home.HomeScreen
import io.github.sudhanv09.presentation.more.MoreScreen
import io.github.sudhanv09.presentation.transactions.AddTransactionScreen
import io.github.sudhanv09.presentation.transactions.TransactionDetailScreen
import io.github.sudhanv09.presentation.transactions.TransactionListScreen
import io.github.sudhanv09.presentation.goals.AddEditGoalScreen
import io.github.sudhanv09.presentation.goals.GoalDetailScreen
import io.github.sudhanv09.presentation.goals.GoalsScreen
import io.github.sudhanv09.presentation.savings.AddEditSavingBucketScreen
import io.github.sudhanv09.presentation.savings.SavingBucketDetailScreen
import io.github.sudhanv09.presentation.savings.SavingBucketsScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val showDock = currentDestination.isTopLevelDestination()

    Scaffold(
        bottomBar = {
            if (showDock) {
                AppDock(
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route, navOptions {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        })
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(padding)
        ) {
            composable<Home> {
                HomeScreen(
                    onNavigateToAccounts = { navController.navigate(Accounts) },
                    onNavigateToTransactions = { navController.navigate(Transactions) },
                    onNavigateToAddTransaction = { navController.navigate(AddTransaction) }
                )
            }

            composable<More> {
                MoreScreen(
                    onNavigateToCategories = { navController.navigate(Categories) },
                    onNavigateToGoals = { navController.navigate(Goals) },
                    onNavigateToSavingBuckets = { navController.navigate(SavingBuckets) }
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

            composable<Goals> {
            GoalsScreen(
                onNavigateToAddGoal = { navController.navigate(AddGoal) },
                onNavigateToGoalDetail = { goalId ->
                    navController.navigate(GoalDetail(goalId))
                }
            )
        }

            composable<AddGoal> {
            AddEditGoalScreen(
                goalId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

            composable<GoalDetail> { backStackEntry ->
            val route: GoalDetail = backStackEntry.toRoute()
            GoalDetailScreen(
                goalId = route.goalId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

            composable<SavingBuckets> {
            SavingBucketsScreen(
                onNavigateToAddBucket = { navController.navigate(AddSavingBucket) },
                onNavigateToBucketDetail = { bucketId ->
                    navController.navigate(SavingBucketDetail(bucketId))
                }
            )
        }

            composable<AddSavingBucket> {
            AddEditSavingBucketScreen(
                bucketId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

            composable<SavingBucketDetail> { backStackEntry ->
            val route: SavingBucketDetail = backStackEntry.toRoute()
            SavingBucketDetailScreen(
                bucketId = route.bucketId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        }
    }
}

@Composable
private fun AppDock(
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit
) {
    val items = listOf(
        DockItem("Home", Icons.Default.Home, Home),
        DockItem("Txns", Icons.Default.ReceiptLong, Transactions),
        DockItem("Accounts", Icons.Default.AccountBalanceWallet, Accounts),
        DockItem("More", Icons.Default.MoreHoriz, More)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination.isRouteSelected(item.route),
                onClick = { onNavigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

private data class DockItem(
    val label: String,
    val icon: ImageVector,
    val route: Any
)

private fun NavDestination?.isTopLevelDestination(): Boolean {
    return this.isRouteSelected(Home) ||
        this.isRouteSelected(Transactions) ||
        this.isRouteSelected(Accounts) ||
        this.isRouteSelected(More)
}

private fun NavDestination?.isRouteSelected(route: Any): Boolean {
    val currentRoute = this?.route ?: return false
    return when (route) {
        Home -> currentRoute.contains("Home")
        Transactions -> currentRoute.contains("Transactions")
        Accounts -> currentRoute.contains("Accounts")
        More -> currentRoute.contains("More")
        else -> false
    }
}
