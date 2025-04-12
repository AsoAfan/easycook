package app.models

import app.core.Database
import java.sql.ResultSet
import java.util.*

data class User(
    var name: String = "",
    var username: String,
    var imageUrl: String? = null,
    var email: String,
    var password: String,
    var emailVerifiedAt: String? = null,
    var createdAt: String = Date().toString(),
    var updatedAt: String = Date().toString(),
    var deletedAt: String? = null,
    var role: String = "user",
    var id: Int? = null,
) {

    companion object {

        fun admins(): List<User>? {
            val query = "SELECT * FROM users WHERE role=?"
            val users: MutableList<User> = mutableListOf()
            return Database.executeQuery(query) { rs ->
                while (rs.next()) {
                    users.add(fromResultSet(rs))
                }
                users.toList()
            }
        }

        fun findById(id: Int): User? {
            val query = "SELECT * FROM users WHERE id = ?"
            return Database.executeQuery(query, id) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findByEmail(email: String): User? {
            val query = "SELECT * FROM users WHERE email = ?"
            return Database.executeQuery(query, email) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findByUsername(username: String): User? {
            val query = "SELECT * FROM users WHERE username = ?"
            return Database.executeQuery(query, username) { rs ->
                if (rs.next()) {
                    fromResultSet(rs)
                } else {
                    null
                }
            }
        }

        fun findAll(): List<User> {
            val query = "SELECT * FROM users"
            return Database.executeQuery(query) { rs ->
                val users = mutableListOf<User>()
                while (rs.next()) {
                    users.add(fromResultSet(rs))
                }
                users
            } ?: emptyList()
        }

        private fun fromResultSet(rs: ResultSet): User = User(
            id = rs.getInt("id"),
            username = rs.getString("username"),
            email = rs.getString("email"),
            password = rs.getString("password"),
            role = rs.getString("role")
        )
    }

    fun save(): Boolean {
        val query = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)"
        return Database.executeUpdate(query, username, email, password, role) > 0
    }

    fun update(): Boolean {
        if (id == null) return false

        val query = "UPDATE users SET username = ?, email = ?, password = ?, role = ? WHERE id = ?"
        return Database.executeUpdate(query, username, email, password, role, id!!) > 0
    }

    fun delete(): Boolean {
        if (id == null) return false

        val query = "DELETE FROM users WHERE id = ?"
        return Database.executeUpdate(query, id!!) > 0
    }

    fun toMap(): Map<String, Any?> = mapOf(
        "id" to this.id,
        "username" to this.username,
        "email" to this.email,
        "password" to this.password,
        "role" to this.role,
    )

}
