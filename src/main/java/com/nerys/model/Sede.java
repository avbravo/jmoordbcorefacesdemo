/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.model;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.DocumentEmbeddable;
import com.jmoordb.core.annotation.Ignore;
import com.jmoordb.core.annotation.Secondary;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@DocumentEmbeddable
public class Sede {

    @Column
    private String idsede;
    @Column
    private String nombre;
    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private String email;
    @Column
    private Boolean active;

    public Sede() {
    }

    public Sede(String idsede, String nombre, String direccion, String telefono, String email, Boolean active) {
        this.idsede = idsede;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.active = active;
    }

    public String getIdsede() {
        return idsede;
    }

    public void setIdsede(String idsede) {
        this.idsede = idsede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idsede);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.direccion);
        hash = 29 * hash + Objects.hashCode(this.telefono);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.active);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sede other = (Sede) obj;
        if (!Objects.equals(this.idsede, other.idsede)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.active, other.active);
    }

    @Override
    public String toString() {
        return "Sede{" + "idsede=" + idsede + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", active=" + active + '}';
    }

   
   
   

}
