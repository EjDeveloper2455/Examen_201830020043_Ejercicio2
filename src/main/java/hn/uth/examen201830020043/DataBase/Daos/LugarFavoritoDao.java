package hn.uth.examen201830020043.DataBase.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;

@Dao
public interface LugarFavoritoDao {

    @Insert
    void insert(LugarFavorito nuevo);

    @Update
    void update(LugarFavorito actualizar);

    @Query("DELETE FROM lugar_favorito_table")
    void deleteAll();

    @Delete
    void delete(LugarFavorito eliminar);

    @Query("select * from lugar_favorito_table order by id asc")
    LiveData<List<LugarFavorito>> getAlls();
}
