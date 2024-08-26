/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.services;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Proyecto;
import java.util.List;
import java.util.Optional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author avbravo
 */
public interface ProyectoFacesServices {

    // <editor-fold defaultstate="collapsed" desc="void onRowCancelProyecto(RowEditEvent<Proyecto> event)">
    public default void onRowCancelProyecto(RowEditEvent<Proyecto> event) {
    }

    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc=" Optional<Proyecto> sprintFindFirst(List<Proyecto> sprintList, String search)">
    public default Optional<Proyecto> tipotarjetaFindFirst(List<Proyecto> tipotarjetaList, String search) {
        try {
          return tipotarjetaList.stream().filter(x -> x.getProyecto().equals(search)).findFirst();

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="updateClientId(String clientId)">
    public default void updateClientId(String clientId){

            PrimeFaces.current().ajax().update(clientId);
          
    }
// </editor-fold>
    
}

