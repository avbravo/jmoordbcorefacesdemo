/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.services;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author avbravo
 */
public interface ClienteFacesServices {

    // <editor-fold defaultstate="collapsed" desc="void onRowCancelCliente(RowEditEvent<Cliente> event)">
    public default void onRowCancelCliente(RowEditEvent<Cliente> event) {
    }

    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc=" Optional<Cliente> ClienteFindFirst(List<Cliente> ClienteList, String search)">
    public default Optional<Cliente> ClienteFindFirst(List<Cliente> ClienteList, String search) {
        try {
          return ClienteList.stream().filter(x -> x.getName().equals(search)).findFirst();

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

