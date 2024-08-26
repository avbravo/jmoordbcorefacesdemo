/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nerys.converter;

import com.avbravo.jmoordbutils.ConsoleUtil;
import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbCoreContext;
import com.nerys.converter.services.IconoConverterServices;
import com.nerys.model.Sede;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */

@Named
@FacesConverter(forClass = Sede.class, managed = true)
public class SedeConverter implements Converter<Sede> {

    @Inject
    IconoConverterServices iconoConverterServices;

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Sede t) {
        try {

            if (t == null) {

                return "";
            }

            if (t.getIdsede()!= null) {

                return t.getIdsede().toString();
            } else {

            }
        } catch (Exception e) {

            new FacesMessage("Error en converter  " + e.getLocalizedMessage());
        }

        return "";
    }

    @Override
    public Sede getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
      Sede a = new Sede();
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            Optional<Sede> optional =Optional.empty();
            List<Sede> sedes = (ArrayList<Sede>)JmoordbCoreContext.get("sedeConverter");
            if(sedes == null || sedes.isEmpty()){
                  
            }else{
                for(Sede s:sedes){
                    if(s.getIdsede().equals(submittedValue)){
                       optional = Optional.of(s);
                    }
                }
             
           }            

            if (!optional.isEmpty()) {

               return optional.get();
            }
            
        } catch (Exception e) {

            
            ConsoleUtil.error("\t " + FacesUtil.nameOfClassAndMethod() + " submittedValue " + submittedValue);
            
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid selecction from Converter"), e);
        }
        return a;
    }

   

}
