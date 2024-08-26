/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.converter.services;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.TipoPrueba;
import com.nerys.services.TipoPruebaServices;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
@ViewScoped
@Named
public class TipoPruebaConverterServices implements Serializable {

    @Inject
    TipoPruebaServices tipoPruebaServices;

    List<TipoPrueba> tipoPruebas = new ArrayList<>();

    public List<TipoPrueba> getTipoPruebas() {
        return tipoPruebas;
    }

    public void setTipoPruebas(List<TipoPrueba> tipoPruebas) {
        this.tipoPruebas = tipoPruebas;
    }

    // <editor-fold defaultstate="collapsed" desc="Optional<TipoPrueba> get(Long id)">
    public Optional<TipoPrueba> get(Long id) {
        Optional<TipoPrueba> result;

        try {
            result = tipoPruebas.stream().filter(x -> x.getIdtipoprueba().equals(id)).findFirst();
            if (!result.isPresent()) {
                Optional<TipoPrueba> tipoPrueba = tipoPruebaServices.findByIdTipoPrueba(id);
                if (tipoPrueba.isPresent()) {
                    tipoPruebas.add(tipoPrueba.get());
                    return tipoPrueba;
                }
                result = Optional.empty();
            }
            return result;
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void add(List<TipoPrueba> tipoPruebas)">

    public void add(List<TipoPrueba> tipoPruebas) {
        try {
            destroyed();
            this.tipoPruebas = tipoPruebas;
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="void destroyed()">
    public void destroyed() {
        try {
            this.tipoPruebas = new ArrayList<>();
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

}
