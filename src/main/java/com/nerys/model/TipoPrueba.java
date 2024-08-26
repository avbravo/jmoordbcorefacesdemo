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
import com.jmoordb.core.annotation.faces.FacesEntity;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@Entity
@FacesEntity(rolesAllowed = {"ADMIN","SUPER.USER"},saveRolesAllowed ={"ADMIN"})
public class TipoPrueba {

   
    @Id(strategy = GenerationType.AUTO)
    private Long idtipoprueba;
    @Column
    private String tipoPrueba;

    @Column
    private Boolean active;
    
    @Embedded
    List<ObservacionTipoPrueba> observacionTipoPrueba;

    @Embedded
    List<ActionHistory> actionHistory;

    public TipoPrueba() {
    }

    public TipoPrueba(Long idtipoprueba, String tipoPrueba, Boolean active, List<ObservacionTipoPrueba> observacionTipoPrueba, List<ActionHistory> actionHistory) {
        this.idtipoprueba = idtipoprueba;
        this.tipoPrueba = tipoPrueba;
        this.active = active;
        this.observacionTipoPrueba = observacionTipoPrueba;
        this.actionHistory = actionHistory;
    }

    public Long getIdtipoprueba() {
        return idtipoprueba;
    }

    public void setIdtipoprueba(Long idtipoprueba) {
        this.idtipoprueba = idtipoprueba;
    }

    public String getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(String tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ObservacionTipoPrueba> getObservacionTipoPrueba() {
        return observacionTipoPrueba;
    }

    public void setObservacionTipoPrueba(List<ObservacionTipoPrueba> observacionTipoPrueba) {
        this.observacionTipoPrueba = observacionTipoPrueba;
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
        hash = 19 * hash + Objects.hashCode(this.idtipoprueba);
        hash = 19 * hash + Objects.hashCode(this.tipoPrueba);
        hash = 19 * hash + Objects.hashCode(this.active);
        hash = 19 * hash + Objects.hashCode(this.observacionTipoPrueba);
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
        final TipoPrueba other = (TipoPrueba) obj;
        if (!Objects.equals(this.tipoPrueba, other.tipoPrueba)) {
            return false;
        }
        if (!Objects.equals(this.idtipoprueba, other.idtipoprueba)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.observacionTipoPrueba, other.observacionTipoPrueba)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

    @Override
    public String toString() {
        return "TipoPrueba{" + "idtipoprueba=" + idtipoprueba + ", tipoPrueba=" + tipoPrueba + ", active=" + active + ", observacionTipoPrueba=" + observacionTipoPrueba + ", actionHistory=" + actionHistory + '}';
    }

    
    

}
