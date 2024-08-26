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
import com.nerys.model.Representante;
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
//@RequestScoped
@Named
@FacesConverter(forClass = Representante.class, managed = true)
public class RepresentanteConverter implements Converter<Representante> {

    @Inject
    IconoConverterServices iconoConverterServices;

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Representante t) {
        try {            
            if (t == null) {
                return "";
            }
            if (t.getCedula()!= null) {
                return t.getCedula().toString();
            }
        } catch (Exception e) {
            new FacesMessage("Error en converter  " + e.getLocalizedMessage());
        }
        return "";
    }

    @Override
    public Representante getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
      Representante a = new Representante();
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }           
        try {
            Optional<Representante> optional =Optional.empty();
            List<Representante> representantes = (ArrayList<Representante>)JmoordbCoreContext.get("representanteConverter");
            if(representantes == null || representantes.isEmpty()){              
              
            }else{
                 for(Representante r:representantes){
                    if(r.getCedula().equals(submittedValue)){
                       optional = Optional.of(r);                        
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
