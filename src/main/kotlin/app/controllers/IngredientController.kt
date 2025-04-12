package app.controllers

import app.core.DataSource
import app.core.serializeToMap
import app.models.Ingredient

class IngredientController {
    fun index(body: String): String {
        val ingredients = Ingredient.findAll()

        return ingredients.toString()
    }

    fun show(body: String): String {
        val data = serializeToMap(body)
        val ingredient = Ingredient.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "ingredient not found"
            ).toString()

        return ingredient.toString()
    }

    fun store(body: String): String {
        val data = serializeToMap(body)
        val duplicatedIngredient = Ingredient.findByName(data["name"] as String)

        if (duplicatedIngredient != null) {
            return mapOf(
                "error" to "${duplicatedIngredient.name} already exists.",
            ).toString()
        }

        val ingredient = Ingredient(
            name = data["name"] ?: "",
            imageUrl = data["imageUrl"] ?: "",
        )
        if (!ingredient.save()) {
            return mapOf("error" to "save ingredient failed").toString()
        }
        DataSource.ingredients.add(ingredient)
        return mapOf(
            "success" to "${ingredient.name} created successfully"
        ).toString()
    }

    fun update(body: String): String {
        val data = serializeToMap(body)
        val ingredient = Ingredient.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "ingredient not found"
            ).toString()

        val duplicatedIngredient = Ingredient.findByName(data["name"] as String)

        if (duplicatedIngredient != null) {
            return mapOf(
                "error" to "${duplicatedIngredient.name} already exists.",
            ).toString()
        }


        val name = ingredient.name

        ingredient.let {
            it.name = data["name"] as String
            it.imageUrl = data["imageUrl"] as String
        }

        if (!ingredient.update()) {
            return mapOf("error" to "update ingredient failed").toString()
        }

        return mapOf(
            "success" to "$name updated"
        ).toString()

    }

    fun delete(body: String): String {
        val data = serializeToMap(body)
        val ingredient = Ingredient.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "ingredient not found"
            ).toString()

        if (!ingredient.delete()) {
            return mapOf("error" to "delete ingredient failed").toString()
        }
        DataSource.ingredients.remove(ingredient)
        return mapOf(
            "success" to "${ingredient.name} deleted successfully"
        ).toString()
    }

    fun sync(body: Any): String {
        DataSource.syncCategoriesToFile()
        return "sync success"
    }

}