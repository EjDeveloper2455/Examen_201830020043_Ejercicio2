package hn.uth.examen201830020043.DataBase.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;

@Dao
public interface ContactoDao {

    @Insert
    void insert(Contacto nuevo);

    @Update
    void update(Contacto actualizar);

    @Query("DELETE FROM contacto_table")
    void deleteAll();

    @Delete
    void delete(Contacto eliminar);

    @Query("select * from contacto_table order by nombre")
    LiveData<List<Contacto>> getAlls();

    @Query("select * from contacto_table where lugar_id = :id order by nombre")
    LiveData<List<Contacto>> getForLugar(int id);
}
