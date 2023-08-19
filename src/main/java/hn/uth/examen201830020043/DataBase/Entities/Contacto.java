package hn.uth.examen201830020043.DataBase.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contacto_table")
public class Contacto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "lugar_id")
    private int lugarId;

    @NonNull
    @ColumnInfo(name = "visita_id")
    private int visitaId;

    public Contacto(@NonNull String nombre, @NonNull String telefono, @NonNull String email, int lugarId, @NonNull int visitaId) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.lugarId = lugarId;
        this.visitaId = visitaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public int getLugarId() {
        return lugarId;
    }

    public void setLugarId(int lugarId) {
        this.lugarId = lugarId;
    }

    @NonNull
    public int getVisitaId() {
        return visitaId;
    }

    public void setVisitaId(@NonNull int visitaId) {
        this.visitaId = visitaId;
    }
}
