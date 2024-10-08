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
import com.jmoordb.core.annotation.enumerations.GenerationType;
import com.jmoordb.core.annotation.enumerations.TypeReferenced;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@Entity
public class Actividad {
@Id(strategy = GenerationType.AUTO)
    private Long idactividad;
    @Column
 private String codigo;
    @Column
 private String actividad;
    @Column
 private Integer anio;
    @Column
    private Boolean active;
   @Referenced(from = "objetivo", localField = "idobjetivo") 
   Objetivo objetivo;

   
   @Embedded
List<ActionHistory> actionHistory;
   
    public Actividad() {
    }

    public Actividad(Long idactividad, String codigo, String actividad, Integer anio, Boolean active, Objetivo objetivo, List<ActionHistory> actionHistory) {
        this.idactividad = idactividad;
        this.codigo = codigo;
        this.actividad = actividad;
        this.anio = anio;
        this.active = active;
        this.objetivo = objetivo;
        this.actionHistory = actionHistory;
    }

    public Long getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(Long idactividad) {
        this.idactividad = idactividad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public List<ActionHistory> getActionHistory() {
        return actionHistory;
    }

    public void setActionHistory(List<ActionHistory> actionHistory) {
        this.actionHistory = actionHistory;
    }

   

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Actividad{");
        sb.append("idactividad=").append(idactividad);
        sb.append(", codigo=").append(codigo);
        sb.append(", actividad=").append(actividad);
        sb.append(", anio=").append(anio);
        sb.append(", active=").append(active);
        sb.append(", objetivo=").append(objetivo);
        sb.append(", actionHistory=").append(actionHistory);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idactividad);
        hash = 41 * hash + Objects.hashCode(this.codigo);
        hash = 41 * hash + Objects.hashCode(this.actividad);
        hash = 41 * hash + Objects.hashCode(this.anio);
        hash = 41 * hash + Objects.hashCode(this.active);
        hash = 41 * hash + Objects.hashCode(this.objetivo);
        hash = 41 * hash + Objects.hashCode(this.actionHistory);
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
        final Actividad other = (Actividad) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.actividad, other.actividad)) {
            return false;
        }
        if (!Objects.equals(this.idactividad, other.idactividad)) {
            return false;
        }
        if (!Objects.equals(this.anio, other.anio)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.objetivo, other.objetivo)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

    

}
