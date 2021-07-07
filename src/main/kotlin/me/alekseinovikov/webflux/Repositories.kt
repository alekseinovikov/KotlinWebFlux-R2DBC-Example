package me.alekseinovikov.webflux

import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Repository

interface UserRepository {
    suspend fun initDB()
    suspend fun saveAndReturnId(login: String): Long
    suspend fun findById(id: Long): String?
}

@Repository
class UserRepositoryImpl(private val db: DatabaseClient) : UserRepository {

    override suspend fun initDB() = db.sql(
        """CREATE TABLE IF NOT EXISTS users( id BIGSERIAL NOT NULL PRIMARY KEY,
                                            login VARCHAR(255) NOT NULL,
                                            CONSTRAINT uix_users_login UNIQUE(login));
        """.trimIndent()
    ).await()

    override suspend fun saveAndReturnId(login: String): Long = db.sql(
        """INSERT INTO users(login) 
                VALUES(:login)
                ON CONFLICT(login) DO UPDATE SET login=EXCLUDED.login
                RETURNING id
                """.trimIndent()
    ).bind("login", login)
        .fetch()
        .awaitSingle()
        .values
        .first() as Long

    override suspend fun findById(id: Long): String? =
        db.sql("SELECT login FROM users WHERE id=:id")
            .bind("id", id)
            .fetch()
            .awaitOneOrNull()
            ?.values
            ?.first() as String?

}