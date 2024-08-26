/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.model;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.Embedded;
import com.jmoordb.core.annotation.Entity;
import com.jmoordb.core.annotation.Id;
import com.jmoordb.core.annotation.Referenced;
import com.jmoordb.core.annotation.ViewReferenced;
import com.jmoordb.core.annotation.enumerations.GenerationType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@Entity
public class Proyecto {

    @Id(strategy = GenerationType.AUTO)
    private Long idproyecto;

    @Column(commentary = "Se genera por cada departamento")
    private Long id;

    @ViewReferenced(from = "departament", localField = "iddepartament")
    private DepartamentView departamentView;

    @Column
    private String proyecto;
    @Column
    private String descripcion;

    @Column
    private String direccion;

    @Column
    private String telefono;

    @Column
    private String fax;

    @Column
    private String email;

    @Column
    private Boolean active;

    @Column(commentary = "iniciado, detenido, cancelado,finalizado")
    private String estado;

    @Column(commentary = "Panameño en el extranejero/Extranjero con cédula/Naturalizado/Panameño antes de vigencia/Población indigena/Número de pasaporte/Juridico", generateQuery = true)
    private String tipocliente;

    @Referenced(from = "Cliente", localField = "idcliente")
    private Cliente cliente;

    @Embedded
    private Sede sede;

    @Embedded
    private Representante representante;

    @Column
    private Date fechaincial;

    @Column
    private Date fechafinal;

    @Embedded
    List<ActionHistory> actionHistory;

    public Proyecto() {
    }

    public Proyecto(Long idproyecto, Long id, DepartamentView departamentView, String proyecto, String descripcion, String direccion, String telefono, String fax, String email, Boolean active, String estado, String tipocliente, Cliente cliente, Sede sede, Representante representante, Date fechaincial, Date fechafinal, List<ActionHistory> actionHistory) {
        this.idproyecto = idproyecto;
        this.id = id;
        this.departamentView = departamentView;
        this.proyecto = proyecto;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.email = email;
        this.active = active;
        this.estado = estado;
        this.tipocliente = tipocliente;
        this.cliente = cliente;
        this.sede = sede;
        this.representante = representante;
        this.fechaincial = fechaincial;
        this.fechafinal = fechafinal;
        this.actionHistory = actionHistory;
    }

    public Long getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Long idproyecto) {
        this.idproyecto = idproyecto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DepartamentView getDepartamentView() {
        return departamentView;
    }

    public void setDepartamentView(DepartamentView departamentView) {
        this.departamentView = departamentView;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public Date getFechaincial() {
        return fechaincial;
    }

    public void setFechaincial(Date fechaincial) {
        this.fechaincial = fechaincial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public List<ActionHistory> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<ActionHistory> actionHistory) {
        this.actionHistory = actionHistory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.idproyecto);
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.departamentView);
        hash = 19 * hash + Objects.hashCode(this.proyecto);
        hash = 19 * hash + Objects.hashCode(this.descripcion);
        hash = 19 * hash + Objects.hashCode(this.direccion);
        hash = 19 * hash + Objects.hashCode(this.telefono);
        hash = 19 * hash + Objects.hashCode(this.fax);
        hash = 19 * hash + Objects.hashCode(this.email);
        hash = 19 * hash + Objects.hashCode(this.active);
        hash = 19 * hash + Objects.hashCode(this.estado);
        hash = 19 * hash + Objects.hashCode(this.tipocliente);
        hash = 19 * hash + Objects.hashCode(this.cliente);
        hash = 19 * hash + Objects.hashCode(this.sede);
        hash = 19 * hash + Objects.hashCode(this.representante);
        hash = 19 * hash + Objects.hashCode(this.fechaincial);
        hash = 19 * hash + Objects.hashCode(this.fechafinal);
        hash = 19 * hash + Objects.hashCode(this.actionHistory);
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
        final Proyecto other = (Proyecto) obj;
        if (!Objects.equals(this.proyecto, other.proyecto)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.tipocliente, other.tipocliente)) {
            return false;
        }
        if (!Objects.equals(this.idproyecto, other.idproyecto)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.departamentView, other.departamentView)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.sede, other.sede)) {
            return false;
        }
        if (!Objects.equals(this.representante, other.representante)) {
            return false;
        }
        if (!Objects.equals(this.fechaincial, other.fechaincial)) {
            return false;
        }
        if (!Objects.equals(this.fechafinal, other.fechafinal)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

    @Override
    public String toString() {
        return "Proyecto{" + "idproyecto=" + idproyecto + ", id=" + id + ", departamentView=" + departamentView + ", proyecto=" + proyecto + ", descripcion=" + descripcion + ", direccion=" + direccion + ", telefono=" + telefono + ", fax=" + fax + ", email=" + email + ", active=" + active + ", estado=" + estado + ", tipocliente=" + tipocliente + ", cliente=" + cliente + ", sede=" + sede + ", representante=" + representante + ", fechaincial=" + fechaincial + ", fechafinal=" + fechafinal + ", actionHistory=" + actionHistory + '}';
    }

}
