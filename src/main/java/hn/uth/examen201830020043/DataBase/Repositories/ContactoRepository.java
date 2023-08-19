package hn.uth.examen201830020043.DataBase.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201830020043.DataBase.DB;
import hn.uth.examen201830020043.DataBase.Daos.ContactoDao;
import hn.uth.examen201830020043.DataBase.Daos.LugarFavoritoDao;
import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;

public class ContactoRepository {
    private ContactoDao dao;
    private LiveData<List<Contacto>> dataset;

    public ContactoRepository(Application app) {
        DB db = DB.getDatabase(app);
        this.dao = db.contactoDao();
        this.dataset = dao.getAlls();
    }

    public LiveData<List<Contacto>> getDataset() {
        return dataset;
    }
    public LiveData<List<Contacto>> getForLugar(int lugar) {
        return dao.getForLugar(lugar);
    }

    public void insert(Contacto nuevo){
        //INSERTANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.insert(nuevo);
        });
    }

    public void update(Contacto actualizar){
        //ACTUALIZANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        DB.databaseWriteExecutor.execute(() -> {
            dao.update(actualizar);
        });
    }

    public void delete(Contacto borrar){
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
