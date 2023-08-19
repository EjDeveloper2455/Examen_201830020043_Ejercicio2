package hn.uth.examen201830020043.DataBase.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
@Dao
public interface VisitaDao {
    @Insert
    void insert(Visita nuevo);

    @Update
    void update(Visita actualizar);

    @Query("DELETE FROM visita_table")
    void deleteAll();

    @Delete
    void delete(Visita eliminar);

    @Query("select * from visita_table order by fecha desc")
    LiveData<List<Visita>> getAlls();
}
