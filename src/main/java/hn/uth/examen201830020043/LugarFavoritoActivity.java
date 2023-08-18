package hn.uth.examen201830020043;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.databinding.ActivityLugarFavoritoBinding;

public class LugarFavoritoActivity extends AppCompatActivity {
    private ActivityLugarFavoritoBinding binding;
    private List<Contacto> contactoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        binding = ActivityLugarFavoritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactoList = new ArrayList<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_de_lugares,
                android.R.layout.simple_spinner_item
        );

        // Especifica el diseño que se utilizará cuando se despliegue la lista
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        binding.spnTipos.setAdapter(adapter);



    }
}