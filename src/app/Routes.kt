package app

import validations.validators.SignupValidation
import views.*
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
            SingleCategory()
        }

        route("categories.create") {
            CreateCategoryView()
        }

        route("ingredients") {
            ShowIngredientsView()
        }

        route("ingredient") {
            SingleIngredient()
        }

        route("ingredients.create") {
            CreateIngredientView()
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