package hn.uth.examen201830020043.DataBase.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201830020043.DataBase.DB;
import hn.uth.examen201830020043.DataBase.Daos.LugarFavoritoDao;
import hn.uth.examen201830020043.DataBase.Daos.VisitaDao;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Entities.Visita;

public class VisitaRepository {
    private VisitaDao dao;
    private LiveData<List<Visita>> dataset;

    public VisitaRepository(Application app) {
        DB db = DB.getDatabase(app);
        this.dao = db.visitaDao();
        this.dataset = dao.getAlls();
    }

    public LiveData<List<Visita>> getDataset() {
        return dataset;
    }

    public void insert(Visita nuevo){
        //INSERTANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.insert(nuevo);
        });
    }

    public void update(Visita actualizar){
        //ACTUALIZANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.update(actualizar);
        });
    }

    public void delete(Visita borrar){
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
