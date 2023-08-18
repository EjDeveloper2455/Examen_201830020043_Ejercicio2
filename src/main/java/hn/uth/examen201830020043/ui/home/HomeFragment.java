package hn.uth.examen201830020043.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.LugarFavoritoActivity;
import hn.uth.examen201830020043.databinding.FragmentHomeBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;

public class HomeFragment extends Fragment implements OnItemClickListener<LugarFavorito> {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private LugarFavoritoAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

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
            startActivity(new Intent(this.getContext(), LugarFavoritoActivity.class));
        });

        setupRecyclerView();

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
        switch (accion){
            case 2:
                abrirMapa(data.getLatitud(),data.getLongitud());
                break;
        }
    }

    private void abrirMapa(double latitud,double longitud) {
        Uri mapLocation = Uri.parse("geo:"+latitud+","+longitud+"?z=14");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapLocation);
        startActivity(mapIntent);
    }
}