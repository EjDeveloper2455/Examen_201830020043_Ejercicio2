package hn.uth.examen201830020043.DataBase;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hn.uth.examen201830020043.DataBase.Daos.ContactoDao;
import hn.uth.examen201830020043.DataBase.Daos.LugarFavoritoDao;
import hn.uth.examen201830020043.DataBase.Daos.VisitaDao;
import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Entities.Visita;

@Database(version = 1, exportSchema = false, entities = {LugarFavorito.class, Contacto.class, Visita.class})
public abstract class DB extends RoomDatabase {
    public abstract LugarFavoritoDao lugarFavoritoDao();
    public abstract ContactoDao contactoDao();
    public abstract VisitaDao visitaDao();

    private static volatile DB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //GENERANDO UNA INSTANCIA MEDIANTE PATRÓN DE SOFTWARE SINGLETON
    public static DB getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DB.class){
                if(INSTANCE == null){

                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                LugarFavoritoDao dao = INSTANCE.lugarFavoritoDao();
                                dao.deleteAll();
                                //dao.insert(new LugarFavorito("Prueba","Playa",-1,-1));
                                ContactoDao contactDao = INSTANCE.contactoDao();
                                contactDao.deleteAll();

                                VisitaDao visita = INSTANCE.visitaDao();
                                visita.deleteAll();
                            });

                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DB.class, "examen_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}

