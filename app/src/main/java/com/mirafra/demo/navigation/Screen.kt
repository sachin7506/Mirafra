package com.mirafra.demo.navigation

sealed class Screen(val route: String) {
    object Welcome       : Screen("welcome")
    object Onboarding    : Screen("onboarding")
    object Login         : Screen("login")
    object Projects      : Screen("projects")
    object CreateProject : Screen("create_project")
}