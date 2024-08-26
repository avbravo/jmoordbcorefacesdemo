/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Representante;
import com.nerys.services.RepresentanteServices;
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
public class RepresentanteServicesImpl implements RepresentanteServices {

    @Override
    public Map.Entry<Boolean, String> isValid(Representante representante) {
        Map.Entry<Boolean, String> result;
        try {
            if (representante.getCedula()== null || representante.getCedula().equals("")) {
                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresecedula");
            } else {
                if (representante.getNombre() == null || representante.getNombre().equals("")) {
                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresenombre");
                } else {
                    if (representante.getDv()== null || representante.getDv().equals("")) {
                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresedv");
                    } else {
                        if (representante.getTelefono() == null || representante.getTelefono().equals("")) {
                            result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresetelefono");
                        } else {
                            if (representante.getEmail() == null || representante.getEmail().equals("")) {
                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseemail");
                            } else {
                                   if(!validEmail(representante.getEmail())){
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
