package hn.uth.examen201830020043.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.ListaContactosActivity;
import hn.uth.examen201830020043.LugarFavoritoActivity;
import hn.uth.examen201830020043.databinding.FragmentHomeBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class HomeFragment extends Fragment implements OnItemClickListener<LugarFavorito> {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private LugarFavoritoAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;

    private  ContactoViewModel contactoViewModel;

    ArrayList<Contacto> listContacto;

    private int idLugar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        contactoViewModel =
                new ViewModelProvider(this).get(ContactoViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        adapter = new LugarFavoritoAdapter(new ArrayList<>(),this);

        homeViewModel.getDataset().observe(getViewLifecycleOwner(), lugaresFavoritos -> {
            if(lugaresFavoritos.isEmpty()){
                Snackbar.make(binding.rvLugaresFavoritos,"No hay lugares favoritos creados", Snackbar.LENGTH_LONG).show();
            }else{
                adapter.setItems(lugaresFavoritos);
            }
        });

        binding.fabAddLugar.setOnClickListener(e -> {
            Intent intent = new Intent(this.getContext(), LugarFavoritoActivity.class);
            launcher.launch(intent);
        });

        setupRecyclerView();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        LugarFavorito lugarFavorito = (LugarFavorito) result.getData().getSerializableExtra("lugar");
                        ArrayList<Contacto> contactoList = (ArrayList<Contacto>) result.getData().getSerializableExtra("listContactos");

                        if(result.getData().getStringExtra("action").equals("nuevo"))homeViewModel.insert(lugarFavorito);
                        else homeViewModel.update(lugarFavorito);
                        idLugar = -1;

                        homeViewModel.getDataset().observe(getViewLifecycleOwner(), lugares -> {
                            if(!lugares.isEmpty()){
                                for (LugarFavorito lugar: lugares) {
                                    idLugar = lugar.getId();
                                }
                                if (idLugar>-1){
                                    for (Contacto contacto: contactoList) {
                                        if (contacto.getId() == -1){
                                            Contacto newContacto = new Contacto(contacto.getNombre(),contacto.getTelefono(),contacto.getEmail(),idLugar,-1);
                                            contactoViewModel.insert(newContacto);
                                        }if(contacto.getLugarId() == -2) contactoViewModel.delete(contacto);

                                    }
                                }
                            }
                        });
                        //Toast.makeText(this.getContext(),idLugar+"",Toast.LENGTH_LONG).show();

                        Snackbar.make(binding.rvLugaresFavoritos, "Se ha guardado correctamente",Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(binding.rvLugaresFavoritos, "Operacion cancelada",Snackbar.LENGTH_LONG).show();
                    }
                }
        );

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvLugaresFavoritos.setLayoutManager(linearLayoutManager);
        binding.rvLugaresFavoritos.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(LugarFavorito data, int accion) {
        if(accion == 0)
                homeViewModel.delete(data);
        else if (accion == 1) {
            Intent intent = new Intent(this.getContext(), LugarFavoritoActivity.class);
            intent.putExtra("lugar", data);
            listContacto = new ArrayList<>();

            launcher.launch(intent);
        } else if (accion == 2) {
            abrirMapa(data.getLatitud(),data.getLongitud());
        }else if (accion == 3) {
            Intent intent = new Intent(this.getContext(), ListaContactosActivity.class);
            intent.putExtra("lugar",data);
            startActivity(intent);
        }

    }

    private void abrirMapa(double latitud,double longitud) {
        Uri mapLocation = Uri.parse("geo:"+latitud+","+longitud+"?z=14");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapLocation);
        startActivity(mapIntent);
    }
}