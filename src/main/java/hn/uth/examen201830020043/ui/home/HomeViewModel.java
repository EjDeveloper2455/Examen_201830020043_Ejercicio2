package hn.uth.examen201830020043.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Repositories.LugarFavoritoRepository;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<LugarFavorito>> dataset;
    private LugarFavoritoRepository repository;
    public HomeViewModel(Application app) {
        super(app);
        repository = new LugarFavoritoRepository(app);
        this.dataset = repository.getDataset();
    }

    public LiveData<List<LugarFavorito>> getDataset() {
        return this.dataset;
    }
    public void delete(LugarFavorito eliminar){
        repository.delete(eliminar);
    }
    public void insert(LugarFavorito nuevo){
        repository.insert(nuevo);
    }
    public void update(LugarFavorito nuevo){
        repository.update(nuevo);
    }
}