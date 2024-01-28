package edu.ufp.pam.project.healthquiz.db.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    /**
     * Gets all users in the database.
     * @return all users from the table.
     */
    @Query("SELECT * FROM users")
    fun loadAllUsers(): LiveData<List<User>>

    /**
     * Gets one user from the database whose username matches the requested string.
     * @return existing user.
     */
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun loadUserByUsername(username: String): User?

    /**
     * Inserts one user in the database (returns id), and aborts if it already exists.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    /**
     * Delete all customers.
     */
    @Query("DELETE FROM users")
    fun deleteAllUsers(): Int
}