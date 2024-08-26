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
public class Representante {

    @Ignore
    private Long id;
    @Column
    private String cedula;
    @Column
    private String nombre;
    @Column
    private String dv;
    @Column
    private String telefono;
    @Column
    private String email;
    @Column
    private Boolean active;

    public Representante() {
    }

    public Representante(Long id, String cedula, String nombre, String dv, String telefono, String email, Boolean active) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.dv = dv;
        this.telefono = telefono;
        this.email = email;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
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
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.cedula);
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + Objects.hashCode(this.dv);
        hash = 79 * hash + Objects.hashCode(this.telefono);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.active);
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
        final Representante other = (Representante) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.dv, other.dv)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.active, other.active);
    }

    @Override
    public String toString() {
        return "NeryRepresentante{" + "id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + ", dv=" + dv + ", telefono=" + telefono + ", email=" + email + ", active=" + active + '}';
    }

   
    
    
    

}
