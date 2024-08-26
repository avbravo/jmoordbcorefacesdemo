/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Sede;
import com.nerys.services.SedeServices;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class SedeServicesImpl implements SedeServices {

    @Override
    public Map.Entry<Boolean, String> isValid(Sede sede) {
        Map.Entry<Boolean, String> result;
        try {
            if (sede.getIdsede() == null || sede.getIdsede().equals("")) {
                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseidsede");
            } else {
                if (sede.getNombre() == null || sede.getNombre().equals("")) {
                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresesede");
                } else {
                    if (sede.getDireccion() == null || sede.getDireccion().equals("")) {
                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresedireccion");
                    } else {
                        if (sede.getTelefono() == null || sede.getTelefono().equals("")) {
                            result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresetelefono");
                        } else {
                            if (sede.getEmail() == null || sede.getEmail().equals("")) {
                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseemail");
                            } else {
                                   if(!validEmail(sede.getEmail())){
                                       result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.emailnovalido");
                                   }else{
                                       result = new AbstractMap.SimpleEntry<>(Boolean.TRUE, "");
                                   }
                                
                            }
                        }
                    }
                }

            }
            return result;

        } catch (Exception e) {
        }
        return new AbstractMap.SimpleEntry<>(Boolean.FALSE, "");
    }

    
    
     @Override
    public Boolean validEmail(String email) {
        Boolean result = Boolean.FALSE;
        try {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            result = matcher.find();

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
}
