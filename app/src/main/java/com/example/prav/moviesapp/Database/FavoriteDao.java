package com.example.prav.moviesapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("Select * from Favorite WHERE movieId = :movieId")
    Favorite getFav(String movieId);

    @Query("Select * from Favorite")
    List<Favorite> getFav();

    @Insert
    void insertFav(Favorite favorite);

    @Query("DELETE FROM Favorite WHERE movieId = :movieId")
    void deleteFav(String movieId);
}
