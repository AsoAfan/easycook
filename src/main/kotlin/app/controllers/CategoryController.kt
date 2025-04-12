package app.controllers

import app.core.DataSource
import app.core.serializeToMap
import app.models.Category

class CategoryController {

    fun index(body: Any): String {

        val categories = Category.findAll()

        return categories.toString()
    }

    fun sync(body: Any): String {
        DataSource.syncCategoriesToFile()
        return "sync success"
    }

    fun show(body: String): String {
        val data = serializeToMap(body)
        val category = Category.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "category not found"
            ).toString()

        return category.toString()
    }

    fun delete(body: String): String {
        val data = serializeToMap(body)
        val category = Category.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "category not found"
            ).toString()

        if (!category.delete()) {
            return mapOf("error" to "category delete failed").toString()
        }
        DataSource.categories.remove(category)
        return mapOf(
            "success" to "${category.name} deleted successfully"
        ).toString()
    }

    fun store(body: String): String {
        val data = serializeToMap(body)
        val duplicatedCategory = Category.findByName(data["name"] as String)

        if (duplicatedCategory != null) {
            return mapOf(
                "error" to "${duplicatedCategory.name} already exists.",
            ).toString()
        }

        val category = Category(
            name = data["name"] ?: "",
            imageUrl = data["imageUrl"] ?: "",
        )
        if (!category.save()) {
            return mapOf(
                "error" to "category save failed"
            ).toString()
        }
        DataSource.categories.add(category)
        return mapOf(
            "success" to "${category.name} created successfully"
        ).toString()
    }

    fun update(formInput: String): String {
        val data = serializeToMap(formInput)
        val category = Category.findById(data["id"].toString().toInt())
            ?: return mapOf(
                "error" to "category not found"
            ).toString()


        val duplicatedCategory = Category.findByName(data["name"] as String)

        if (duplicatedCategory != null) {
            return mapOf(
                "error" to "${duplicatedCategory.name} already exists.",
            ).toString()
        }


        val name = category.name

        category.let {
            it.name = data["name"] as String
            it.imageUrl = data["imageUrl"] as String
        }

        category.update()

        return mapOf(
            "success" to "$name updated"
        ).toString()
    }

}