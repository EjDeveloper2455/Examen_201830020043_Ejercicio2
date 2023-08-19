package hn.uth.examen201830020043.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.databinding.FragmentDashboardBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;
import hn.uth.examen201830020043.ui.home.ContactAdapter;
import hn.uth.examen201830020043.ui.home.ContactoViewModel;

public class DashboardFragment extends Fragment implements OnItemClickListener<Contacto> {

    private FragmentDashboardBinding binding;

    ContactAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactoViewModel contactoViewModel =
                new ViewModelProvider(this).get(ContactoViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new ContactAdapter(new ArrayList<>(),this);

        contactoViewModel.getForLugar(2).observe(getViewLifecycleOwner(), contactoList -> {
            if (!contactoList.isEmpty()){
                adapter.setItems(contactoList);
            }
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
    public void onItemClick(Contacto data, int accion) {

    }
}