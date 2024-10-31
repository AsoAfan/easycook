package models

import java.io.File

object DataSource {

    val clientHandlers = mutableListOf<ClientController>()
    val users: MutableList<User> = mutableListOf()
    val session: MutableMap<String, Map<String, Any?>?> = mutableMapOf()

    val categories: MutableList<Category> = mutableListOf()
    val ingredients: MutableList<Ingredient> = mutableListOf()
    val posts: MutableList<Post> = mutableListOf()

    private const val CSV_FOLDER = "src/main/resources/csv"
    fun syncToFile() {
        val folder = File(CSV_FOLDER)
        if (!folder.exists()) {
            folder.mkdir()
        }
        syncUsersToFile()
        syncCategoriesToFile()
        syncIngredientsToFile()
    }


    fun syncCategoriesToFile() {
        val fileName = "${CSV_FOLDER}/categories.csv"
        val file = File(fileName)
        if (file.exists()) {
            file.delete()
            file.createNewFile()
        }
        file.bufferedWriter().use { out ->

            out.write("name,imageUrl,id")
            out.newLine()

            categories.forEach { category ->
                val row = "${category.name},${category.imagUrl},${category.id}"
                out.write(row)
                out.newLine()
            }

        }
    }

    fun syncIngredientsToFile() {
        val fileName = "${CSV_FOLDER}/ingredients.csv"
        val file = File(fileName)
        if (file.exists()) {
            file.delete()
            file.createNewFile()
        }
        file.bufferedWriter().use { out ->

            out.write("name,imageUrl,id")
            out.newLine()

            ingredients.forEach { ingredient ->
                val row = "${ingredient.name},${ingredient.imagUrl},${ingredient.id}"
                out.write(row)
                out.newLine()
            }

        }
    }

    fun syncUsersToFile() {
        val fileName = "${CSV_FOLDER}/users.csv"
        val file = File(fileName)
        file.delete()
        file.createNewFile()
        file.bufferedWriter().use { out ->
            out.write("username,email,password,role,id")
            out.newLine()
            users.forEach { user ->
                val row = "${user.username},${user.email},${user.password},${user.role},${user.id}"
                out.write(row)
                out.newLine()
            }
        }
    }

    fun loadFile() {
        val folder = File(CSV_FOLDER)
        if (!folder.exists()) {
            return
        }
        loadUsers()
        loadCategories()
        loadIngredients()
    }

    private fun loadCategories() {
        val fileName = "${CSV_FOLDER}/categories.csv"
        val file = File(fileName)
        if (!file.exists()) {
            return
        }
        val data = file.bufferedReader().useLines { lines ->
            lines.drop(1).map { line ->

                val parts = line.split(",")
                Category(
                    name = parts[0],
                    imagUrl = parts[1],
                    id = parts[2].toInt(),
                )
            }.toList()
        }
        categories.clear()
        categories.addAll(data)
    }

    private fun loadIngredients() {
        val fileName = "${CSV_FOLDER}/ingredients.csv"
        val file = File(fileName)
        if (!file.exists()) {
            return
        }
        val data = file.bufferedReader().useLines { lines ->
            lines.drop(1).map { line ->

                val parts = line.split(",")
                Ingredient(
                    name = parts[0],
                    imagUrl = parts[1],
                    id = parts[2].toInt(),
                )
            }.toList()
        }
        ingredients.clear()
        ingredients.addAll(data)
    }

    private fun loadUsers() {
        val fileName = "${CSV_FOLDER}/users.csv"
        val file = File(fileName)
        if (!file.exists()) {
            return
        }
        val data = file.bufferedReader().useLines { lines ->
            lines.drop(1).map { line ->
                val parts = line.split(",")
                User(
                    username = parts[0],
                    email = parts[1],
                    password = parts[2],
                    role = parts[3],
                    id = parts[4].toInt(),
                )
            }.toList()
        }

        users.clear()
        users.addAll(data)
    }

}
