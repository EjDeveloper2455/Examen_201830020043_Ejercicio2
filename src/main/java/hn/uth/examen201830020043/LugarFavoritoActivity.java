package hn.uth.examen201830020043;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.Contacto;
import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.DataBase.Entities.LugarFavorito;
import hn.uth.examen201830020043.databinding.ActivityLugarFavoritoBinding;
import hn.uth.examen201830020043.ui.home.ContactoViewModel;
import hn.uth.examen201830020043.ui.home.HomeViewModel;

public class LugarFavoritoActivity extends AppCompatActivity implements LocationListener {
    private ActivityLugarFavoritoBinding binding;
    private ArrayList<Contacto> contactoList;
    private ActivityResultLauncher<Intent> launcher;
    private static final int REQUEST_CODE_GPS = 555;
    private LocationManager locationManager;

    private int RESULT_CODE;
    private int idLugar = -1;

    private LugarFavorito lugar;

    private ContactoViewModel contactoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLugarFavoritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tilLatitud.getEditText().setText("33");
        binding.tilLongitud.getEditText().setText("-33");

        contactoViewModel =
                new ViewModelProvider(this).get(ContactoViewModel.class);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        lugar = null;
        contactoList = new ArrayList<>();

        Intent getIntent = getIntent();
        if(getIntent.hasExtra("lugar")){
            lugar = (LugarFavorito) getIntent.getSerializableExtra("lugar");
            binding.tilLatitud.getEditText().setText(lugar.getLatitud()+"");
            binding.tilLongitud.getEditText().setText(lugar.getLongitud()+"");
            binding.tilNombre.getEditText().setText(lugar.getNombre());

            contactoViewModel.getForLugar(lugar.getId()).observe(this, contactos -> {
                if (!contactos.isEmpty()) {
                    contactoList = (ArrayList<Contacto>) contactos;
                }
            });
        }


        RESULT_CODE = 0;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_de_lugares,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spnTipos.setAdapter(adapter);
        binding.btnAddContactoLugar.setOnClickListener(e -> {
            launcher.launch(new Intent(this, MostrarContactosActivity.class));
            RESULT_CODE = 1;
        });

        binding.btnVerContactosLugar.setOnClickListener(e ->{
            Intent intent = new Intent(this, ListaContactosActivity.class);
            intent.putExtra("listContact",contactoList);
            launcher.launch(intent);
            RESULT_CODE = 2;
        });

        binding.imgLocation.setOnClickListener(e -> solicitarPermisosGPS());

        binding.btnGuardarLugar.setOnClickListener(e ->{
            if(!validar())return;
            LugarFavorito lugarFavorito = new LugarFavorito(binding.tilNombre.getEditText().getText().toString(),
                    binding.spnTipos.getSelectedItem().toString(),
                    Double.parseDouble(binding.tilLatitud.getEditText().getText().toString()),
                    Double.parseDouble(binding.tilLongitud.getEditText().getText().toString()));

            if (lugar != null)lugarFavorito.setId(lugar.getId());

            Intent intent = new Intent();
            intent.putExtra("lugar",lugarFavorito);
            intent.putExtra("listContactos",contactoList);
            intent.putExtra("action",(lugar == null)?"nuevo":"edit");
            setResult(RESULT_OK,intent);
            finish();
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if(RESULT_CODE == 1) {
                            DeviceContact deviceContact = (DeviceContact) result.getData().getSerializableExtra("contact");
                            Contacto newContact = new Contacto(deviceContact.getNombre(), deviceContact.getTelefono(), deviceContact.getEmai(), -1, -1);
                            newContact.setId(-1);
                            contactoList.add(newContact);
                            RESULT_CODE = 0;
                        }else if(RESULT_CODE == 2) {
                            Contacto contacto = (Contacto) result.getData().getSerializableExtra("contacto");
                            int posicion = result.getData().getIntExtra("posicion",-1);
                            if (contacto.getId() == -1){
                                contactoList.remove(posicion);
                            }else{
                                Contacto contacto1 = contactoList.get(posicion);
                                contacto1.setLugarId(-2);
                                contactoList.set(posicion,contacto1);
                            }
                            RESULT_CODE = 0;
                        }
                    } else {
                        Snackbar.make(binding.barLugarActivity, "Operacion cancelada",Snackbar.LENGTH_LONG).show();
                    }
                }
        );
    }

    private boolean validar(){
        if(binding.tilNombre.getEditText().getText().toString().isEmpty()){
            binding.tilNombre.setError("*Campo requerido*");
            return false;
        }else if(binding.tilLatitud.getEditText().getText().toString().isEmpty()){
            binding.tilLatitud.setError("*Campo requerido*");
            return false;
        }
        else if(binding.tilLongitud.getEditText().getText().toString().isEmpty()){
            binding.tilLongitud.setError("*Campo requerido*");
            return false;
        }
        return true;
    }

    private void solicitarPermisosGPS() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //TENGO EL PERMISO, PUEDO UTILIZAR EL GPS
            useFineLocation();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GPS){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    useFineLocation();
                }else if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    useCoarseLocation();
                }
            }else{

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useCoarseLocation() {
        //OBTIENE EL SERVICIO DE UBICACIÓN DEL DISPOSITIVO
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //SOLICITAMOS ACTUALIZAR LA POSICIÓN GPS CON DETERMINADA APROXIMACIÓN (NETWORK_PROVIDER = COARSE_LOCATION = UBICACIÓN APROXIMADA)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useFineLocation() {
        //OBTIENE EL SERVICIO DE UBICACIÓN DEL DISPOSITIVO
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //SOLICITAMOS ACTUALIZAR LA POSICIÓN GPS CON DETERMINADA APROXIMACIÓN (GPS_PROVIDER = FINE_LOCATION = UBICACIÓN EXACTA)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        binding.tilLatitud.getEditText().setText(location.getLatitude()+"");
        binding.tilLongitud.getEditText().setText(location.getLongitude()+"");

        //DETENER ACTUALIZACION DE UBICACION PARA DEJARLO DE UN SOLO USO (SI SE QUIERE SEGUIMIENTO NO HACER ESTA PARTE)
        locationManager.removeUpdates(this);
    }
}