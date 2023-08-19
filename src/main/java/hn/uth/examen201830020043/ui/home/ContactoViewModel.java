package hn.uth.examen201830020043.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Repositories.ContactoRepository;
import hn.uth.examen201830020043.DataBase.Repositories.LugarFavoritoRepository;

public class ContactoViewModel extends AndroidViewModel {
    private LiveData<List<Contacto>> dataset;
    private ContactoRepository repository;
    public ContactoViewModel(Application app) {
        super(app);
        repository = new ContactoRepository(app);
        this.dataset = repository.getDataset();
    }

    public LiveData<List<Contacto>> getDataset() {
        return this.dataset;
    }

    public LiveData<List<Contacto>> getForLugar(int lugar) {
        return repository.getForLugar(lugar);
    }
    public LiveData<List<Contacto>> getForVisita(int lugar) {
        return repository.getForVisita(lugar);
    }
    public void delete(Contacto contacto){
        repository.delete(contacto);
    }
    public void insert(Contacto contacto){
        repository.insert(contacto);
    }
}
