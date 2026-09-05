package com.mirafra.demo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mirafra.demo.ui.auth.login.LoginScreen
import com.mirafra.demo.ui.onboarding.OnboardingScreen
import com.mirafra.demo.ui.projects.CreateProjectScreen
import com.mirafra.demo.ui.projects.ProjectsScreen
import com.mirafra.demo.ui.welcome.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(Screen.Projects.route) {
                        popUpTo(Screen.Login.route) { inclusive = true } // ✅ can't go back to login
                    }
                }
            )
        }
        composable(Screen.Projects.route) {
            ProjectsScreen(
                onNewProject = {
                    navController.navigate(Screen.CreateProject.route)
                },
                onProjectClick = { projectId ->
                    // navigate to project detail later
                }
            )
        }
        composable(Screen.CreateProject.route) {
            CreateProjectScreen(
                onBack = { navController.popBackStack() },
                onProjectCreated = {
                    navController.popBackStack() // ✅ go back to projects list
                }
            )
        }
    }
}