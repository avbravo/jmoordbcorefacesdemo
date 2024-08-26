/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nerys.converter;


import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.converter.services.BuildingConverterServices;
import com.nerys.model.Building;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
//@RequestScoped
@Named
@FacesConverter(forClass = Building.class, managed = true)
public class BuildingConverter implements Converter<Building> {
    @Inject
    BuildingConverterServices  buildingConverterServices;
  @Override
    public String getAsString(FacesContext fc, UIComponent uic, Building t) {
        try {
              
           if (t == null) {

                return "";
            }

            if (t.getIdbuilding()!= null) {

                return t.getIdbuilding().toString();
            } else {

            }
      } catch (Exception e) {

            new FacesMessage("Error en converter  "+e.getLocalizedMessage());
      }

 return "";
    }

    @Override
    public Building getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
      Building a = new Building();
     if (submittedValue == null || submittedValue.isEmpty()) {
                return null;
            }


        try {
           // String idUser =submittedValue;
          
            Integer id=Integer.parseInt(submittedValue);
          
           Long idBuilding= id.longValue();
          
         Optional<Building> optional =buildingConverterServices.get(idBuilding);
          
            if (optional.isPresent()) {
          
                a = optional.get();
            }
            return a;
        } catch (Exception e) {

            System.out.println("====================");
            System.out.println(FacesUtil.nameOfClassAndMethod()+" " +e.getLocalizedMessage());
            System.out.println("====================");
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid selecction from Converter"), e);
        }
    }

}

