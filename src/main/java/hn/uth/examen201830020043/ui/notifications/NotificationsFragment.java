package hn.uth.examen201830020043.ui.notifications;

import android.app.Activity;
import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.DataBase.Entities.Visita;
import hn.uth.examen201830020043.MostrarContactosActivity;
import hn.uth.examen201830020043.R;
import hn.uth.examen201830020043.databinding.FragmentNotificationsBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;
import hn.uth.examen201830020043.ui.home.ContactAdapter;
import hn.uth.examen201830020043.ui.home.ContactoViewModel;

public class NotificationsFragment extends Fragment implements OnItemClickListener<Contacto> {

    private FragmentNotificationsBinding binding;
    private ActivityResultLauncher<Intent> launcher;

    ContactAdapter adapter;
    List<Contacto> contactList;
    VisitaViewModel visitaViewModel;
    ContactoViewModel contactoViewModel;
    LugarFavorito lugarFavorito;
    int idVisita;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitaViewModel =
                new ViewModelProvider(this).get(VisitaViewModel.class);
        contactoViewModel = new ViewModelProvider(this).get(ContactoViewModel.class);

        adapter = new ContactAdapter(new ArrayList<>(),this);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        idVisita = -1;

        contactList = new ArrayList<>();
        lugarFavorito = null;

        Bundle bundle = getArguments();
        if (bundle != null) {
              lugarFavorito = (LugarFavorito) bundle.getSerializable("lugarVisita");
             binding.tvTituloVisita.setText(lugarFavorito.getNombre());

        }else{
            binding.btnAddContactoVisita.setEnabled(false);
            binding.btnGuardarVisita.setEnabled(false);
        }


        binding.btnAddContactoVisita.setOnClickListener(e ->{
            launcher.launch(new Intent(this.getContext(), MostrarContactosActivity.class));
        });

        binding.btnGuardarVisita.setOnClickListener(e -> {
            if(lugarFavorito == null)return;

            Date fecha = new Date();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaString = dateTimeFormat.format(fecha);

            visitaViewModel.insert(new Visita(fechaString,lugarFavorito.getNombre(),lugarFavorito.getId()));
            visitaViewModel.getDataset().observe(getViewLifecycleOwner(),visitas -> {
                for (Visita visita : visitas) {
                    idVisita = visita.getId();
                    break;
                }

                if(idVisita > -1) {
                    for (Contacto contacto : contactList) {
                        Contacto newContacto = new Contacto(contacto.getNombre(), contacto.getTelefono(), contacto.getEmail(), lugarFavorito.getId(), idVisita);
                        contactoViewModel.insert(newContacto);
                    }
                }

                NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_dashboard);
            });

        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        DeviceContact deviceContact = (DeviceContact) result.getData().getSerializableExtra("contact");
                        Contacto newContact = new Contacto(deviceContact.getNombre(), deviceContact.getTelefono(), deviceContact.getEmai(), -1, -1);
                        contactList.add(newContact);
                        adapter.setItems(contactList);
                    } else {
                        Snackbar.make(binding.rvContactosVisita, "Operacion cancelada",Snackbar.LENGTH_LONG).show();
                    }
                }
        );

        setupRecyclerView();

        return root;
    }


    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvContactosVisita.setLayoutManager(linearLayoutManager);
        binding.rvContactosVisita.setAdapter(adapter);
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