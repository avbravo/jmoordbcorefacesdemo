/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.nerys.model.TipoPrueba;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public interface TipoPruebaServices {

    public List<TipoPrueba> findAll();

    public Optional<TipoPrueba> findByIdTipoPrueba(Long idtipoprueba);
    


    public TipoPrueba findByTipoPrueba(String tipoPrueba);

    public Optional<TipoPrueba> save(TipoPrueba tipoPrueba);

    public Boolean update(TipoPrueba tipoPrueba);

    public Boolean delete(Long idtipoprueba);

    public List<TipoPrueba> lookup(Bson filter, Document sort, Integer page, Integer size);

    public Long count(Bson filter, Document sort, Integer page, Integer size);


    public Boolean existsTipoPrueba(TipoPrueba tipoPrueba);
    
     public Long countLikeByTipoPrueba(String tipoPrueba);
    

    public List<TipoPrueba> likeByTipoPrueba( String tipoPrueba);

}
