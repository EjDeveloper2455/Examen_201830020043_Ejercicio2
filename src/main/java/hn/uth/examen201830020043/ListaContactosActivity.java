package hn.uth.examen201830020043;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
import hn.uth.examen201830020043.databinding.ActivityListaContactosBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;
import hn.uth.examen201830020043.ui.home.ContactAdapter;
import hn.uth.examen201830020043.ui.home.ContactoViewModel;

public class ListaContactosActivity extends AppCompatActivity implements OnItemClickListener<Contacto> {

    private ActivityListaContactosBinding binding;
    private ContactAdapter adapter;
    private ArrayList<Contacto> dataset;

    private ContactoViewModel contactoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaContactosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactoViewModel =
                new ViewModelProvider(this).get(ContactoViewModel.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        adapter = new ContactAdapter(new ArrayList<>(),this);

        Intent getDataIntent = getIntent();
        if(getDataIntent.hasExtra("listContact")){
            dataset = (ArrayList<Contacto>) getDataIntent.getSerializableExtra("listContact");
            List<Contacto> nuevoList = new ArrayList<>();
            for (Contacto contact: dataset) {
                if (contact.getLugarId() > -2)nuevoList.add(contact);
            }
            adapter.setItems(nuevoList);
        }

        if(getDataIntent.hasExtra("lugar")){
            LugarFavorito lugarFavorito = (LugarFavorito) getDataIntent.getSerializableExtra("lugar");
            contactoViewModel.getForLugar(lugarFavorito.getId()).observe(this, contactos -> {
                if (!contactos.isEmpty()) {
                    adapter.setItems(contactos);
                }
            });

        }

        if(getDataIntent.hasExtra("visita")){
            Visita visita = (Visita) getDataIntent.getSerializableExtra("visita");
            contactoViewModel.getForVisita(visita.getId()).observe(this, visitas -> {
                if (!visitas.isEmpty()) {
                    adapter.setItems(visitas);
                }
            });

        }

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvListContact.setLayoutManager(linearLayoutManager);
        binding.rvListContact.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Contacto data, int accion) {
        Intent dataIntent = new Intent();
        dataIntent.putExtra("contacto",data);
        dataIntent.putExtra("posicion",accion);
        setResult(RESULT_OK,dataIntent);
        finish();
    }
}