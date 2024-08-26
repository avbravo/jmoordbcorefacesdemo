/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.converter.services;

import com.avbravo.jmoordbutils.FacesUtil;
import com.nerys.model.Cliente;
import com.nerys.services.ClienteServices;
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
public class ClienteConverterServices implements Serializable {

    @Inject
    ClienteServices clienteServices;

    List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> Clientes) {
        this.clientes = Clientes;
    }

    // <editor-fold defaultstate="collapsed" desc="Optional<Cliente> get(Long id)">
    public Optional<Cliente> get(Long id) {
        Optional<Cliente> result;

        try {
            result = clientes.stream().filter(x -> x.getIdcliente().equals(id)).findFirst();
            if (!result.isPresent()) {
                Optional<Cliente> cliente = clienteServices.findByIdcliente(id);
                if (cliente.isPresent()) {
                    clientes.add(cliente.get());
                    return cliente;
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
    // <editor-fold defaultstate="collapsed" desc="void add(List<Cliente> Clientes)">

    public void add(List<Cliente> Clientes) {
        try {
            destroyed();
            this.clientes = clientes;
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="void destroyed()">
    public void destroyed() {
        try {
            this.clientes = new ArrayList<>();
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

}
