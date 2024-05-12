package org.d3if3139.rentara.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3139.rentara.model.Recipe
import org.d3if3139.rentara.model.User

@Dao
interface RentaraDao {

    //    User
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    suspend fun login(phoneNumber: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    suspend fun getCurrentUser(phoneNumber: String): User?

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    suspend fun isUserExist(phoneNumber: String): User?

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserById(id: Long)


    //    Recipe
    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes ORDER BY judul ASC")
    fun getAllRecipe(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Long): Recipe?

    @Query("SELECT * FROM recipes WHERE judul = :title")
    suspend fun getRecipeByTitle(title: String): Recipe?

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipeById(id: Long)


}