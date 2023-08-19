package hn.uth.examen201830020043.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
import hn.uth.examen201830020043.ListaContactosActivity;
import hn.uth.examen201830020043.R;
import hn.uth.examen201830020043.databinding.FragmentDashboardBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;
import hn.uth.examen201830020043.ui.home.ContactAdapter;
import hn.uth.examen201830020043.ui.home.ContactoViewModel;
import hn.uth.examen201830020043.ui.notifications.VisitaAdapter;
import hn.uth.examen201830020043.ui.notifications.VisitaViewModel;

public class DashboardFragment extends Fragment implements OnItemClickListener<Visita> {

    private FragmentDashboardBinding binding;

    VisitaAdapter adapter;
    VisitaViewModel visitaViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitaViewModel =
                new ViewModelProvider(this).get(VisitaViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new VisitaAdapter(new ArrayList<>(),this);

        visitaViewModel.getDataset().observe(getViewLifecycleOwner(), visitas -> {
            if (!visitas.isEmpty()){
                adapter.setItems(visitas);
            }
        });

        binding.btnHome.setOnClickListener(e ->{
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);
        });

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvVisitados.setLayoutManager(linearLayoutManager);
        binding.rvVisitados.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Visita data, int accion) {
        if (accion == 0){
            visitaViewModel.delete(data);
        }else{
            Intent intent = new Intent(this.getContext(), ListaContactosActivity.class);
            intent.putExtra("visita",data);
            startActivity(intent);
        }
    }
}