package com.coffeeandcookies.portal125;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Gonzalo on 18/02/2017.
 */

@Table(name = "Categoria")
public class Categoria extends Model
{
    @Column(name = "local_id")
    int local_id;
    @Column(name = "nombre")
    String nombre;


    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int id) {
        this.local_id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
