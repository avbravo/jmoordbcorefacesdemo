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
import com.jmoordb.core.annotation.faces.FacesBreadcrumb;
import com.jmoordb.core.annotation.faces.FacesEntity;
import com.jmoordb.core.annotation.faces.enumerations.FacesIcon;
import com.jmoordb.core.annotation.faces.enumerations.GenerateController;
import com.jmoordb.core.annotation.faces.enumerations.RegenerationCode;
import com.jmoordb.core.annotation.faces.enumerations.TypeForm;
import com.jmoordb.core.annotation.faces.leftmenu.FacesMenuEntityItem;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@Entity
@FacesEntity(
        rolesAllowed = "ADMIN,SUPER-USER",
        title = "#{form.ensayo",
        typeForm = TypeForm.CRUD,
        generateController = GenerateController.YES)
/*
Genera el submenu
 */
@FacesMenuEntityItem(action = "ensayo",
        facesIcon = FacesIcon.AMAZON,
        value = "",
        title = "#{submenu.ensayo}",
        id = "submenuTipoprueba", rolesAllowed = {"ADMIN"},
        subMenuFather = "subMenuGeneral",
        regeneration = RegenerationCode.NO)

@FacesBreadcrumb(action = {"dashboard", "ensayo"}, label = {"#{msg['breadcrumb.dashboard']}", "#{msg['breadcrumb.ensayo']}"})
public class Ensayo {

    @Id()
    private String idensayo;
    @Column
    private String ensayo;
    @Column
    private String observacion;
    @Column
    private Boolean active;

    @Referenced(from = "tipoprueba", localField = "idtipoprueba")
    private TipoPrueba tipoPrueba;
    
    
     @Embedded
    List<ActionHistory> actionHistory;

    public Ensayo() {
    }

    public Ensayo(String idensayo, String ensayo, String observacion, Boolean active, TipoPrueba tipoPrueba, List<ActionHistory> actionHistory) {
        this.idensayo = idensayo;
        this.ensayo = ensayo;
        this.observacion = observacion;
        this.active = active;
        this.tipoPrueba = tipoPrueba;
        this.actionHistory = actionHistory;
    }

    public String getIdensayo() {
        return idensayo;
    }

    public void setIdensayo(String idensayo) {
        this.idensayo = idensayo;
    }

    public String getEnsayo() {
        return ensayo;
    }

    public void setEnsayo(String ensayo) {
        this.ensayo = ensayo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public TipoPrueba getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(TipoPrueba tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
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
        hash = 53 * hash + Objects.hashCode(this.idensayo);
        hash = 53 * hash + Objects.hashCode(this.ensayo);
        hash = 53 * hash + Objects.hashCode(this.observacion);
        hash = 53 * hash + Objects.hashCode(this.active);
        hash = 53 * hash + Objects.hashCode(this.tipoPrueba);
        hash = 53 * hash + Objects.hashCode(this.actionHistory);
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
        final Ensayo other = (Ensayo) obj;
        if (!Objects.equals(this.idensayo, other.idensayo)) {
            return false;
        }
        if (!Objects.equals(this.ensayo, other.ensayo)) {
            return false;
        }
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.tipoPrueba, other.tipoPrueba)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

    @Override
    public String toString() {
        return "Ensayo{" + "idensayo=" + idensayo + ", ensayo=" + ensayo + ", observacion=" + observacion + ", active=" + active + ", tipoPrueba=" + tipoPrueba + ", actionHistory=" + actionHistory + '}';
    }

    
     
     
     

}
