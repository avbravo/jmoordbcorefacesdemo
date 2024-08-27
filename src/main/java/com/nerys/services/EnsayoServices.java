/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.nerys.model.Ensayo;
import com.nerys.model.Proyecto;
import com.nerys.model.TipoPrueba;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public interface EnsayoServices {

    public List<Ensayo> findAll();

    public Optional<Ensayo> findByIdensayo(String idensayo);

    public List<Ensayo> findByEnsayo(String ensayo);

    public Optional<Ensayo> save(Ensayo ensayo);

    public Boolean update(Ensayo ensayo);

    public Boolean delete(String idensayo);

    public List<Ensayo> lookup(Bson filter, Document sort, Integer page, Integer size);

    public Long count(Bson filter, Document sort, Integer page, Integer size);
    public Boolean existsEnsayo(Ensayo ensayo);
    public Boolean existsEnsayoWithDiferentId(Ensayo ensayo); 
    
     public Long countLikeByEnsayo(String ensayo);
    

    public List<Ensayo> likeByEnsayo( String ensayo);
    
    public List<Ensayo> loadEnsayoByTipoPrueba(TipoPrueba tipoPrueba);

}
