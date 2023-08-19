package hn.uth.examen201830020043.DataBase.Entities;

import java.io.Serializable;

public class DeviceContact implements Serializable {
    private String nombre;
    private String telefono;
    private String emai;

    public DeviceContact(String nombre, String telefono, String emai) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.emai = emai;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }
}
