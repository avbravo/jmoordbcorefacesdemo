/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.nerys.model.Cliente;
import com.nerys.model.Sede;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface ClienteServices {

    public Optional<Cliente> findByIdcliente(Long idcliente);

    public Optional<Cliente> findByName(String name);

    public Optional<Cliente> findByEmail(String email);

    public Optional<Cliente> findByCedularuc(String cedularuc);
    public Optional<Cliente> findByDV(String dv);

    public List<Cliente> findAll();

    public Optional<Cliente> save(Cliente cliente);

    public Boolean update(Cliente cliente);

    public Boolean delete(Long idCliente);

    public List<Cliente> lookup(Bson filter, Document sort, Integer page, Integer size);

    public Long count(Bson filter, Document sort, Integer page, Integer size);

    public List<Cliente> likeByName(@QueryParam("name") String name);

    public List<Cliente> likeByCedularuc(@QueryParam("cedularuc") String cedularuc);

    public Boolean validEmail(String email);

    public Boolean validCliente(Cliente cliente);

    public Map.Entry<Boolean, String> isValid(Cliente cliente);

    public Boolean existsCliente(Cliente cliente);
    


}
