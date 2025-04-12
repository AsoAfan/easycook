package app.models

import app.core.Database
import java.sql.ResultSet

data class Ingredient(
    var name: String,
    var imageUrl: String,
    var id: Int? = null,
) {

    companion object {

        fun findById(id: Int): Ingredient? {
            val query = "SELECT * FROM ingredients WHERE id = ?"
            return Database.executeQuery(query, id) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findByIds(ids: List<Int>): List<Ingredient> {
            val conn = Database.getConnection()
            val sql = "SELECT * FROM ingredients WHERE id = ANY(?)"
            val ingredientList = mutableListOf<Ingredient>()

            conn.prepareStatement(sql).use { stmt ->
                stmt.setArray(1, conn.createArrayOf("INTEGER", ids.toTypedArray()))
                val rs = stmt.executeQuery()

                while (rs.next()) {
                    ingredientList.add(
                        Ingredient(
                            id = rs.getInt("id"),
                            name = rs.getString("name"),
                            imageUrl = rs.getString("image_url")
                        )
                    )
                }
            }
            return ingredientList
        }

        fun findByName(name: String): Ingredient? {
            val query = "SELECT * FROM ingredients WHERE name = ?"
            return Database.executeQuery(query, name) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findAll(): List<Ingredient> {
            val query = "SELECT * FROM ingredients"
            return Database.executeQuery(query) { rs ->
                val ingredients = mutableListOf<Ingredient>()
                while (rs.next()) {
                    ingredients.add(fromResultSet(rs))
                }
                ingredients
            } ?: emptyList()
        }

        private fun fromResultSet(rs: ResultSet): Ingredient {
            return Ingredient(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                imageUrl = rs.getString("image_url")
            )
        }
    }

    fun save(): Boolean {
        val query = "INSERT INTO ingredients (name, image_url) VALUES (?, ?)"
        return Database.executeUpdate(query, name, imageUrl) > 0
    }

    fun update(): Boolean {
        if (id == null) return false

        val query = "UPDATE ingredients SET name = ?, image_url = ? WHERE id = ?"
        return Database.executeUpdate(query, name, imageUrl, id!!) > 0
    }

    fun delete(): Boolean {
        if (id == null) return false

        val query = "DELETE FROM ingredients WHERE id = ?"
        return Database.executeUpdate(query, id!!) > 0
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to this.id,
            "name" to this.name,
            "imageUrl" to this.imageUrl
        )
    }
}
