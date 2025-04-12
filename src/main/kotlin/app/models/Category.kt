package app.models

import app.core.Database
import java.sql.ResultSet

data class Category(
    var name: String,
    var imageUrl: String,
    var id: Int? = null,
) {

    companion object {
        fun findById(id: Int): Category? {
            val query = "SELECT * FROM categories WHERE id = ?"
            return Database.executeQuery(query, id) { rs ->
                if (rs.next()) fromResultSet(rs)
                else null
            }
        }

        fun findByIds(ids: List<Int>): List<Category> {
            val conn = Database.getConnection()
            val sql = "SELECT * FROM categories WHERE id = ANY(?)"
            val categoryList = mutableListOf<Category>()

            Database.executeQuery(sql, ids) { rs ->
                while (rs.next()) {
                    categoryList.add(
                        Category(
                            id = rs.getInt("id"),
                            name = rs.getString("name"),
                            imageUrl = rs.getString("image_url")
                        )
                    )
                }
            }

            conn.prepareStatement(sql).use { stmt ->
                stmt.setArray(1, conn.createArrayOf("INTEGER", ids.toTypedArray()))
                val rs = stmt.executeQuery()

                while (rs.next()) {
                    categoryList.add(
                        Category(
                            id = rs.getInt("id"),
                            name = rs.getString("name"),
                            imageUrl = rs.getString("image_url")
                        )
                    )
                }
            }
            return categoryList
        }

        fun findByName(name: String): Category? {
            val query = "SELECT * FROM categories WHERE name = ?"
            return Database.executeQuery(query, name) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findAll(): List<Category> {
            val query = "SELECT * FROM categories"
            return Database.executeQuery(query) { rs ->
                val categories = mutableListOf<Category>()
                while (rs.next()) {
                    categories.add(fromResultSet(rs))
                }
                categories
            } ?: emptyList()
        }

        private fun fromResultSet(rs: ResultSet): Category {
            return Category(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                imageUrl = rs.getString("image_url")
            )
        }
    }

    fun save(): Boolean {
        val query = "INSERT INTO categories (name, image_url) VALUES (?, ?)"
        return Database.executeUpdate(query, name, imageUrl) > 0
    }

    fun update(): Boolean {
        if (id == null) return false

        val query = "UPDATE categories SET name = ?, image_url = ? WHERE id = ?"
        return Database.executeUpdate(query, name, imageUrl, id!!) > 0
    }

    fun delete(): Boolean {
        if (id == null) return false

        val query = "DELETE FROM categories WHERE id = ?"
        return Database.executeUpdate(query, id!!) > 0
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "id" to this.id,
        "name" to this.name,
        "imageUrl" to this.imageUrl
    )
}

