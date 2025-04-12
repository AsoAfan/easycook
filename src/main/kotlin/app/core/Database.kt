package app.core

import java.sql.*

object Database {
    private lateinit var conn: Connection

    fun getConnection(): Connection {
        val url = "jdbc:postgresql://172.29.194.67:5432/easycook"
        val user = "postgres"
        val password = "aso123456"

        if (!Database::conn.isInitialized || conn.isClosed) {
            conn = DriverManager.getConnection(url, user, password)
        }
        return conn
    }

    fun executeQuery(query: String, vararg params: Any): ResultSet? {
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            preparedStatement = getConnection().prepareStatement(query)
            setParameters(preparedStatement, params)
            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return resultSet
    }

    fun executeUpdate(query: String, vararg params: Any): Int {
        var preparedStatement: PreparedStatement? = null
        var result = 0

        try {
            preparedStatement = getConnection().prepareStatement(query)
            setParameters(preparedStatement, params)
            result = preparedStatement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            preparedStatement?.close()
        }
        return result
    }

    private fun setParameters(preparedStatement: PreparedStatement, params: Array<out Any>) {
        for ((index, param) in params.withIndex()) {
            preparedStatement.setObject(index + 1, param)
        }
    }


    fun closeResources(vararg resources: AutoCloseable?) {
        for (resource in resources) {
            try {
                resource?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Helper function to execute a query and handle ResultSet within a lambda
    fun <T> executeQuery(query: String, vararg params: Any, handler: (ResultSet) -> T): T? {
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        return try {
            preparedStatement = getConnection().prepareStatement(query)
            setParameters(preparedStatement, params)
            resultSet = preparedStatement.executeQuery()
            handler(resultSet)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        } finally {
            closeResources(resultSet, preparedStatement)
        }
    }
}
