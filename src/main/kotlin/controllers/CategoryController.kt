package controllers

import models.Category
import models.DataSource
import serializeToMap

class CategoryController {

    fun index(body: Any): String {

        val categories = DataSource.categories

        return categories.toString()
    }

    fun sync(body: Any): String {
        DataSource.syncCategoriesToFile()
        return "sync success"
    }

    fun show(body: String): String {
        val data = serializeToMap(body)
        val category = DataSource.categories.find { it.id.toString() == data["id"] }
        if (category == null) {
            return mapOf(
                "error" to "category not found"
            ).toString()
        }

        return category.toString()
//        return body
    }

    fun delete(body: String): String {
        val data = serializeToMap(body)
        val category = DataSource.categories.find { it.id.toString() == data["id"] }
        if (category == null) {
            return mapOf(
                "error" to "category not found"
            ).toString()
        }
        DataSource.categories.remove(category)
        DataSource.syncCategoriesToFile()
        return mapOf(
            "success" to "${category.name} deleted successfully"
        ).toString()
    }

    fun store(body: String): String {
        val data = serializeToMap(body)
        val duplicatedCategory = DataSource.categories.find { category -> category.name == data["name"] }

        if (duplicatedCategory != null) {
            return mapOf(
                "error" to "${duplicatedCategory.name} already exists.",
            ).toString()
        }

        val category = Category(
            name = data["name"] ?: "",
            imagUrl = data["imageUrl"] ?: "",
        )
        DataSource.categories.add(category)
        return mapOf(
            "success" to "${category.name} created successfully"
        ).toString()
    }

    fun update(formInput: String): String {
        val data = serializeToMap(formInput)
        val category = DataSource.categories.find { it.id.toString() == data["id"] }

        if (category == null) {
            return mapOf(
                "error" to "category not found"
            ).toString()
        }

        val duplicatedCategory = DataSource.categories.find { cat -> cat.name == data["name"] }

        if (duplicatedCategory != null) {
            return mapOf(
                "error" to "${duplicatedCategory.name} already exists.",
            ).toString()
        }


        val name = category.name

        category.let {
            it.name = data["name"] as String
            it.imagUrl = data["imageUrl"] as String
        }

        return mapOf(
            "success" to "$name updated"
        ).toString()
    }


    /*
        fun update(category: Category?, inputs: Map<String, String>) {
            category?.let {
                it.name = inputs["name"] as String
                it.imagUrl = inputs["image"] as String
            }

            Routes.navigateBack()
        }*/
}