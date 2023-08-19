package hn.uth.examen201830020043;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import hn.uth.examen201830020043.DataBase.Entities.DeviceContact;
import hn.uth.examen201830020043.databinding.ActivityMostrarContactosBinding;
import hn.uth.examen201830020043.ui.OnItemClickListener;
import hn.uth.examen201830020043.ui.home.DeviceContactAdapter;

public class MostrarContactosActivity extends AppCompatActivity implements OnItemClickListener<DeviceContact> {

    private static final int PERMISSION_REQUEST_READ_CONTACT = 100;
    ActivityMostrarContactosBinding binding;

    DeviceContactAdapter adapter;

    private List<DeviceContact> dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMostrarContactosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        adapter = new DeviceContactAdapter(solicitarPermisoContactos(),this);

        binding.imgSearch.setOnClickListener(e -> adapter.setItems(solicitarPermisoContactos()));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvDeviceContact.setLayoutManager(linearLayoutManager);
        binding.rvDeviceContact.setAdapter(adapter);
    }

    private List<DeviceContact> solicitarPermisoContactos(){
        //PREGUNTANDO SI YA TENGO UN DETERMINADO PERMISO
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            //ENTRA AQUI SI NO ME HAN DADO EL PERMISO, Y DEBO DE SOLICITARLO
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACT);
            return new ArrayList<>();
        }else{
            //ENTRA AQUI SI EL USUARIO YA ME OTORGÓ EL PERMISO ANTES, PUEDO HACER USO DE LA LECTURA DE CONTACTOS
            return getContacts();
        }
    }

    private List<DeviceContact> getContacts() {
        List<DeviceContact> contactos = new ArrayList<>();

        //String buscar = binding.tilSearch.getEditText().getText().toString();

        String charName = binding.tilSearchContact.getEditText().getText().toString();
        if (charName.isEmpty()) charName = "A";

        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, ContactsContract.Contacts.DISPLAY_NAME + " LIKE '"+charName+"%'", null, ContactsContract.Contacts.DISPLAY_NAME + " DESC");

        boolean continuar = true;
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int idColumnIndex = Math.max(cursor.getColumnIndex(ContactsContract.Contacts._ID), 0);
                int nameColumnIndex = Math.max(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME), 0);
                int phoneColumnIndex = Math.max(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER), 0);//ME DICE SI TIENE O NO UN TELEFONO GUARDADO

                String id = cursor.getString(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                if(Integer.parseInt(cursor.getString(phoneColumnIndex)) > 0){

                    //EL CONTACTO SI TIENE TELEFONO ALMACENADO
                    Cursor cursorPhone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

                    String phone = "";
                    while (cursorPhone.moveToNext()){
                        int phoneCommonColumIndex = cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phone = cursorPhone.getString(phoneCommonColumIndex);

                        /*Contacto nuevo = new Contacto();
                        nuevo.setName(name);
                        nuevo.setPhone(phone);
                        contactos.add(nuevo);*/
                        continuar = false;
                    }

                    Cursor cursorCorreo = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{id}, null);
                    String correo ="";
                    while (cursorCorreo.moveToNext()){
                        int emailCommonColumIndex = cursorCorreo.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1);
                        correo = cursorCorreo.getString(emailCommonColumIndex);
                    }
                    DeviceContact nuevo = new DeviceContact(name,phone,correo);

                    contactos.add(nuevo);

                    cursorCorreo.close();
                    cursorPhone.close();
                }
            }
            cursor.close();
        }

        return contactos;
    }

    @Override
    public void onItemClick(DeviceContact data, int accion) {
        Intent dataIntent = new Intent();
        dataIntent.putExtra("contact",data);
        setResult(RESULT_OK,dataIntent);
        finish();
    }
}