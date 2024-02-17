package com.fajar.githubuserappdicoding.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteDao {

/*    @Query("SELECT * FROM User_Favorite")
    fun getUserFavorites(): Flow<List<UserFavorite>>*/

    @Query("SELECT EXISTS (SELECT * FROM User_Favorite WHERE username = :username)")
    fun isUserInFavorites(username: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserToFav(userFav: UserFavorite): Long

    @Query("DELETE FROM User_Favorite WHERE username = :username")
    suspend fun removeUserFromFav(username: String)

    @Query("SELECT * FROM User_Favorite WHERE username LIKE '%' || :query || '%'")
    fun searchUserInFavorite(query: String): Flow<List<UserFavorite>>
}