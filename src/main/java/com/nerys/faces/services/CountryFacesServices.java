/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.services;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Country;
import java.util.List;
import java.util.Optional;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author avbravo
 */
public interface CountryFacesServices {

    // <editor-fold defaultstate="collapsed" desc="void onRowCancelCountry(RowEditEvent<Country> event)">
    public default void onRowCancelCountry(RowEditEvent<Country> event) {
    }

    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc=" Optional<Country> countryFindFirst(List<Country> countryList, String search)">
    public default Optional<Country> countryFindFirst(List<Country> countryList, String search) {
        try {
          return countryList.stream().filter(x -> x.getCountry().equals(search)).findFirst();

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

