package hn.uth.examen201830020043.DataBase.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201830020043.DataBase.DB;
import hn.uth.examen201830020043.DataBase.Daos.LugarFavoritoDao;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;

public class LugarFavoritoRepository {
    private LugarFavoritoDao dao;
    private LiveData<List<LugarFavorito>> dataset;

    public LugarFavoritoRepository(Application app) {
        DB db = DB.getDatabase(app);
        this.dao = db.lugarFavoritoDao();
        this.dataset = dao.getAlls();
    }

    public LiveData<List<LugarFavorito>> getDataset() {
        return dataset;
    }

    public void insert(LugarFavorito nuevo){
        //INSERTANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.insert(nuevo);
        });
    }

    public void update(LugarFavorito actualizar){
        //ACTUALIZANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.update(actualizar);
        });
    }

    public void delete(LugarFavorito borrar){
        //BORRANDO UN REGISTRO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.delete(borrar);
        });
    }

    public void deleteAll(){
        //BORRANDO TODOS LOS REGISTROS DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }
}
