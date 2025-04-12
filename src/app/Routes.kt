package app

import app.interfaces.Renderable
import utills.UI
import validations.SignupValidation
import views.*
import views.auth.LoginView
import views.auth.SignupView
import views.categories.CreateCategoryView
import views.categories.ShowCategoryView
import views.categories.SingleCategoryView
import views.posts.CreatePostView
import views.posts.ShowPostView
import views.posts.SinglePost
import java.util.*

data class Route(val renderable: Renderable, val props: Any?)

object Routes {

    private val backStack = Stack<Route>()
    private val routes: MutableMap<String, Renderable> = mutableMapOf()

    fun defineRoutes() {
        route("onBoarding") {
            OnBoarding()
        }
        route("signup") { SignupView(SignupValidation) }
        route("login") { LoginView() }


        route("home") {
            HomeView()
        }

        route("profile") { ProfileView() }

        route("categories") {
            ShowCategoryView()
        }

        route("category") {
            SingleCategoryView()
        }

        route("categories.create") {
            CreateCategoryView()
        }

        route("ingredients") {
            views.ingredients.ShowIngredientView()
        }

        route("ingredient") {
            views.ingredients.SingleIngredientView()
        }

        route("ingredients.create") {
            views.ingredients.CreateIngredientView()
        }

        route("posts") {
            ShowPostView()
        }

        route("posts.create") {
            CreatePostView()
        }

        route("post") {
            SinglePost()
        }
    }

    fun navigate(route: String, props: Any? = null) {
        backStack.push(routes[route]?.let { Route(it, props) })
        UI.separator(20, '_')
        render()
    }

    fun navigateBack() {
        popView()
        render()
    }


    private fun route(route: String, renderable: () -> Renderable) {

        routes[route] = renderable()

    }

    private fun render() {
        val route = backStack.peek()
        route.renderable.middleware()
        route.renderable.render(route.props)
    }

    fun popView() {
        if (backStack.size > 1)
            backStack.pop()
    }


}