/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.model;

import com.nerys.model.Area;
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
public class Objetivo {

    @Id(strategy = GenerationType.AUTO)
    private Long idobjetivo;
    @Column
    private String codigo;
    @Column
    private String objetivo;
    @Column
    private Boolean active;
    @Column
    private Integer anio;
    @Column
    private String productoesperado;

    @Referenced(from = "area", localField = "idarea")
    Area area;
@Embedded
List<ActionHistory> actionHistory;
    public Objetivo() {
    }

    public Objetivo(Long idobjetivo, String codigo, String objetivo, Boolean active, Integer anio, String productoesperado, Area area, List<ActionHistory> actionHistory) {
        this.idobjetivo = idobjetivo;
        this.codigo = codigo;
        this.objetivo = objetivo;
        this.active = active;
        this.anio = anio;
        this.productoesperado = productoesperado;
        this.area = area;
        this.actionHistory = actionHistory;
    }

    public Long getIdobjetivo() {
        return idobjetivo;
    }

    public void setIdobjetivo(Long idobjetivo) {
        this.idobjetivo = idobjetivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getProductoesperado() {
        return productoesperado;
    }

    public void setProductoesperado(String productoesperado) {
        this.productoesperado = productoesperado;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
        sb.append("Objetivo{");
        sb.append("idobjetivo=").append(idobjetivo);
        sb.append(", codigo=").append(codigo);
        sb.append(", objetivo=").append(objetivo);
        sb.append(", active=").append(active);
        sb.append(", anio=").append(anio);
        sb.append(", productoesperado=").append(productoesperado);
        sb.append(", area=").append(area);
        sb.append(", actionHistory=").append(actionHistory);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idobjetivo);
        hash = 79 * hash + Objects.hashCode(this.codigo);
        hash = 79 * hash + Objects.hashCode(this.objetivo);
        hash = 79 * hash + Objects.hashCode(this.active);
        hash = 79 * hash + Objects.hashCode(this.anio);
        hash = 79 * hash + Objects.hashCode(this.productoesperado);
        hash = 79 * hash + Objects.hashCode(this.area);
        hash = 79 * hash + Objects.hashCode(this.actionHistory);
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
        final Objetivo other = (Objetivo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.objetivo, other.objetivo)) {
            return false;
        }
        if (!Objects.equals(this.productoesperado, other.productoesperado)) {
            return false;
        }
        if (!Objects.equals(this.idobjetivo, other.idobjetivo)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.anio, other.anio)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        return Objects.equals(this.actionHistory, other.actionHistory);
    }

   
}
