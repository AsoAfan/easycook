package app.models

import app.core.Database
import java.sql.ResultSet

data class Post(
    var title: String,
    var content: String,
    val userId: Int?,
    var ingredients: List<Ingredient> = mutableListOf(),
    var categories: MutableList<Category> = mutableListOf(),
    var id: Int? = null
) {

    companion object {

        fun findById(id: Int): Post? {
            val query = "SELECT * FROM posts WHERE id = ?"
            return Database.executeQuery(query, id) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findAll(): List<Post> {
            val query = "SELECT * FROM posts"
            return Database.executeQuery(query) { rs ->
                val posts = mutableListOf<Post>()
                while (rs.next()) {
                    posts.add(fromResultSet(rs))
                }
                posts
            } ?: emptyList()
        }

        private fun fromResultSet(rs: ResultSet): Post {
            return Post(
                id = rs.getInt("id"),
                title = rs.getString("title"),
                content = rs.getString("content"),
                userId = rs.getInt("user_id")
            ).apply {
                this.ingredients = getIngredients(rs.getInt("id"))
                this.categories = getCategories(rs.getInt("id"))
            }
        }

        private fun getIngredients(postId: Int): MutableList<Ingredient> {
            val query = """
                SELECT i.* FROM ingredients i
                JOIN post_ingredients pi ON i.id = pi.ingredient_id
                WHERE pi.post_id = ?
            """
            return Database.executeQuery(query, postId) { rs ->
                val ingredients = mutableListOf<Ingredient>()
                while (rs.next()) {
                    ingredients.add(Ingredient.findById(rs.getInt("id"))!!)
                }
                ingredients
            } ?: mutableListOf()
        }

        private fun getCategories(postId: Int): MutableList<Category> {
            val query = """
                SELECT c.* FROM categories c
                JOIN post_categories pc ON c.id = pc.category_id
                WHERE pc.post_id = ?
            """
            return Database.executeQuery(query, postId) { rs ->
                val categories = mutableListOf<Category>()
                while (rs.next()) {
                    categories.add(Category.findById(rs.getInt("id"))!!)
                }
                categories
            } ?: mutableListOf()
        }
    }

    fun save(): Boolean {
        val query = "INSERT INTO posts (title, content, user_id) VALUES (?, ?, ?)"
        val result = Database.executeUpdate(query, title, content, userId!!) > 0

        // TODO: manage relations

        return result
    }

}
