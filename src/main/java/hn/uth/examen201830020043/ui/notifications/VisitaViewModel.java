package hn.uth.examen201830020043.ui.notifications;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
import hn.uth.examen201830020043.DataBase.Repositories.VisitaRepository;

public class VisitaViewModel extends AndroidViewModel {

    private LiveData<List<Visita>> dataset;
    private VisitaRepository repository;

    public VisitaViewModel(Application app) {
        super(app);
        repository = new VisitaRepository(app);
        this.dataset = repository.getDataset();
    }

    public LiveData<List<Visita>> getDataset() {
        return this.dataset;
    }
    public void insert(Visita nuevo){
        repository.insert(nuevo);
    }
    public void delete(Visita visita){
        repository.delete(visita);
    }
}