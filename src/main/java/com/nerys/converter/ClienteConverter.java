/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.s.converter;

import com.avbravo.jmoordbutils.ConsoleUtil;
import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.converter.services.ClienteConverterServices;
import com.nerys.model.Cliente;
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
@FacesConverter(forClass = Cliente.class, managed = true)
public class ClienteConverter implements Converter<Cliente> {

    @Inject
    ClienteConverterServices clienteConverterServices;
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Cliente t) {
        try {
            if (t == null) {
                return "";
            }
            if (t.getIdcliente() != null) {
                return t.getIdcliente().toString();
            } else {
            }
        } catch (Exception e) {
            new FacesMessage("Error en converter  " + e.getLocalizedMessage());
        }
        return "";
    }

    @Override
    public Cliente getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        Cliente a = new Cliente();
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
        try {
            Integer id = Integer.parseInt(submittedValue);
            Long idcliente = id.longValue();
            Optional<Cliente> optional = clienteConverterServices.get(idcliente);
            if(optional.isPresent()){
                   a = optional.get();
            }

            return a;
        } catch (Exception e) {
          
              ConsoleUtil.error("\t "+FacesUtil.nameOfClassAndMethod()+" submittedValue " +submittedValue);
                        throw new ConverterException(new FacesMessage(submittedValue + " is not a valid selecction from Converter"), e);
        }
    }
}
