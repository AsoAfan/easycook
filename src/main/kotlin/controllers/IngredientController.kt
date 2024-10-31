package controllers

import models.DataSource
import models.Ingredient
import serializeToMap

class IngredientController {
    fun index(body: String): String {
        val ingredients = DataSource.ingredients

        return ingredients.toString()
    }

    fun sync(body: Any): String {
        DataSource.syncCategoriesToFile()
        return "sync success"
    }

    fun show(body: String): String {
        val ingredient = DataSource.ingredients.find { it.id.toString() == body }
        if (ingredient == null) {
            return mapOf(
                "error" to "ingredient not found"
            ).toString()
        }

        return ingredient.toString()
    }

    fun store(body: String): String {
        val data = serializeToMap(body)
        val duplicatedIngredient = DataSource.ingredients.find { ingredient -> ingredient.name == data["name"] }

        if (duplicatedIngredient != null) {
            return mapOf(
                "error" to "${duplicatedIngredient.name} already exists.",
            ).toString()
        }

        val element = Ingredient(
            name = data["name"] ?: "",
            imagUrl = data["imageUrl"] ?: "",
        )
        DataSource.ingredients.add(element)
        return mapOf(
            "success" to "${element.name} created successfully"
        ).toString()
    }

    fun update(body: String): String {
        val data = serializeToMap(body)
        val ingredient = DataSource.ingredients.find { it.id.toString() == data["id"] }

        if (ingredient == null) {
            return mapOf(
                "error" to "ingredient not found"
            ).toString()
        }

        val duplicatedIngredient = DataSource.ingredients.find { ind -> ind.name == data["name"] }

        if (duplicatedIngredient != null) {
            return mapOf(
                "error" to "${duplicatedIngredient.name} already exists.",
            ).toString()
        }


        val name = ingredient.name

        ingredient.let {
            it.name = data["name"] as String
            it.imagUrl = data["imageUrl"] as String
        }

        return mapOf(
            "success" to "$name updated"
        ).toString()

    }

    fun delete(body: String): String {
        val data = serializeToMap(body)
        val ingredient = DataSource.ingredients.find { it.id.toString() == data["id"] }
        if (ingredient == null) {
            return mapOf(
                "error" to "ingredient not found"
            ).toString()
        }
        DataSource.ingredients.remove(ingredient)
        DataSource.syncCategoriesToFile()
        return mapOf(
            "success" to "${ingredient.name} deleted successfully"
        ).toString()
    }


}