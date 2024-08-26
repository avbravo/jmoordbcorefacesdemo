/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.model;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.Embedded;
import com.jmoordb.core.annotation.Entity;
import com.jmoordb.core.annotation.Id;
import com.jmoordb.core.annotation.enumerations.GenerationType;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@Entity
public class Cliente {

    @Id(strategy = GenerationType.AUTO)
    private Long idcliente;
    @Column(generateQuery = true)
    private String cedularuc;

    @Column(commentary = "natural/juridico", generateQuery = true)
    private String tipocliente;
    @Column
    private String dv;
    @Column(generateQuery = true)
    private String name;
    @Embedded
    private List<Sede> sede;

    @Embedded
    private List<Representante> representante;

    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private String celular;

    @Column
    private String fax;

    @Column
    private String email;
    @Column
    private String observacion;

    @Column
    private Boolean gobierno;

    @Column
    private Boolean active;

    @Embedded
    List<ActionHistory> actionHistory;

    public Cliente() {
    }

    public Cliente(Long idcliente, String cedularuc, String tipocliente, String dv, String name, List<Sede> sede, List<Representante> representante, String direccion, String telefono, String celular, String fax, String email, String observacion, Boolean gobierno, Boolean active, List<ActionHistory> actionHistory) {
        this.idcliente = idcliente;
        this.cedularuc = cedularuc;
        this.tipocliente = tipocliente;
        this.dv = dv;
        this.name = name;
        this.sede = sede;
        this.representante = representante;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.fax = fax;
        this.email = email;
        this.observacion = observacion;
        this.gobierno = gobierno;
        this.active = active;
        this.actionHistory = actionHistory;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getCedularuc() {
        return cedularuc;
    }

    public void setCedularuc(String cedularuc) {
        this.cedularuc = cedularuc;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sede> getSede() {
        return sede;
    }

    public void setSede(List<Sede> sede) {
        this.sede = sede;
    }

    public List<Representante> getRepresentante() {
        return representante;
    }

    public void setRepresentante(List<Representante> representante) {
        this.representante = representante;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getGobierno() {
        return gobierno;
    }

    public void setGobierno(Boolean gobierno) {
        this.gobierno = gobierno;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ActionHistory> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<ActionHistory> actionHistory) {
        this.actionHistory = actionHistory;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idcliente);
        hash = 71 * hash + Objects.hashCode(this.cedularuc);
        hash = 71 * hash + Objects.hashCode(this.tipocliente);
        hash = 71 * hash + Objects.hashCode(this.dv);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.sede);
        hash = 71 * hash + Objects.hashCode(this.representante);
        hash = 71 * hash + Objects.hashCode(this.direccion);
        hash = 71 * hash + Objects.hashCode(this.telefono);
        hash = 71 * hash + Objects.hashCode(this.celular);
        hash = 71 * hash + Objects.hashCode(this.fax);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.observacion);
        hash = 71 * hash + Objects.hashCode(this.gobierno);
        hash = 71 * hash + Objects.hashCode(this.active);
        hash = 71 * hash + Objects.hashCode(this.actionHistory);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.cedularuc, other.cedularuc)) {
            return false;
        }
        if (!Objects.equals(this.tipocliente, other.tipocliente)) {
            return false;
        }
        if (!Objects.equals(this.dv, other.dv)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.celular, other.celular)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.idcliente, other.idcliente)) {
            return false;
        }
        if (!Objects.equals(this.sede, other.sede)) {
            return false;
        }
        if (!Objects.equals(this.representante, other.representante)) {
            return false;
        }
        if (!Objects.equals(this.gobierno, other.gobierno)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

    @Override
    public String toString() {
        return "Cliente{" + "idcliente=" + idcliente + ", cedularuc=" + cedularuc + ", tipocliente=" + tipocliente + ", dv=" + dv + ", name=" + name + ", sede=" + sede + ", representante=" + representante + ", direccion=" + direccion + ", telefono=" + telefono + ", celular=" + celular + ", fax=" + fax + ", email=" + email + ", observacion=" + observacion + ", gobierno=" + gobierno + ", active=" + active + ", actionHistory=" + actionHistory + '}';
    }

}
