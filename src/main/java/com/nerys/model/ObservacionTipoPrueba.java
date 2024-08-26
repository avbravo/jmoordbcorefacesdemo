/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.model;

import com.jmoordb.core.annotation.Column;
import com.jmoordb.core.annotation.DocumentEmbeddable;
import com.jmoordb.core.annotation.Ignore;
import java.util.Objects;

/**
 *
 * @author avbravo
 */
@DocumentEmbeddable
public class ObservacionTipoPrueba {

    @Ignore
    private Long id;
    @Column
    private String observacion;
 
    @Column
    private Boolean active;

    public ObservacionTipoPrueba() {
    }

    public ObservacionTipoPrueba(Long id, String observacion, Boolean active) {
        this.id = id;
        this.observacion = observacion;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.observacion);
        hash = 89 * hash + Objects.hashCode(this.active);
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
        final ObservacionTipoPrueba other = (ObservacionTipoPrueba) obj;
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.active, other.active);
    }

    @Override
    public String toString() {
        return "ObservacionTipoPrueba{" + "id=" + id + ", observacion=" + observacion + ", active=" + active + '}';
    }

   
    
    
    

}
