/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.encode.EncodeUtil;
import static com.mongodb.client.model.Filters.eq;
import com.nerys.model.TipoPrueba;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.nerys.restclient.TipoPruebaRestClient;
import com.nerys.services.TipoPruebaServices;
import jakarta.enterprise.context.ApplicationScoped;


/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class TipoPruebaServicesImpl implements TipoPruebaServices{
  // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    JmoordbResourcesFiles rf;
   // </editor-fold>
 // <editor-fold defaultstate="collapsed" desc="Microprofile Rest Client">
    @Inject
   TipoPruebaRestClient tipoPruebaRestClient;
// </editor-fold>
    
    
    
    
    @Override
    public List<TipoPrueba> findAll() {
       return tipoPruebaRestClient.findAll();
    }

    @Override
    public Optional<TipoPrueba> findByIdTipoPrueba(Long idtipoPrueba) {

       
        try {
           TipoPrueba result = tipoPruebaRestClient.findByIdTipoPrueba(idtipoPrueba);
            if (result == null || result.getIdtipoprueba()== null) {

            } else {
                return Optional.of(result);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
        
    }

    @Override
    public TipoPrueba findByTipoPrueba(String tipoPrueba) {
       return tipoPruebaRestClient.findByTipoPrueba(tipoPrueba);
    }

    // <editor-fold defaultstate="collapsed" desc="Optional<TipoPrueba> save(TipoPrueba tipoPrueba)">

    @Override
    public Optional<TipoPrueba> save(TipoPrueba tipoPrueba) {
        
          try {

            Response response = tipoPruebaRestClient.save(tipoPrueba);

            if (response.getStatus() == 400) {

                String error = (response.readEntity(String.class));

                return Optional.empty();
            }

          TipoPrueba result = (TipoPrueba) (response.readEntity(TipoPrueba.class));

            return Optional.of(result);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
        
        
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean update(TipoPrueba tipoPrueba)">

    @Override
    public Boolean update(TipoPrueba tipoPrueba) {
        Boolean result = Boolean.FALSE;
         try {
             
        
        Integer status = tipoPruebaRestClient.update(tipoPrueba).getStatus();
        
        if(status == 201){
            result = Boolean.TRUE;
        }
             
        } catch (Exception e) {
         FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean delete(Long idtipoPrueba)">

    @Override
    public Boolean delete(Long idtipoPrueba) {
       Boolean result = Boolean.FALSE;
         try {
             
        
        Integer status = tipoPruebaRestClient.delete(idtipoPrueba).getStatus();
        
        if(status == 201){
            result = Boolean.TRUE;
        }
             
        } catch (Exception e) {
         FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
        // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<TipoPrueba> lookup(Bson filter, Document sort, Integer page, Integer size)">

    @Override
    public List<TipoPrueba> lookup(Bson filter, Document sort, Integer page, Integer size) {
      List<TipoPrueba> tipoPruebaList = new ArrayList<>();
        try {
            tipoPruebaList = tipoPruebaRestClient.lookup(
                        EncodeUtil.encodeBson(filter),
                        EncodeUtil.encodeBson(sort),
                        page, size);
        } catch (Exception e) {
         FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return tipoPruebaList;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Long count(Bson filter, Document sort, Integer page, Integer size)">

  
    @Override
    public Long count(Bson filter, Document sort, Integer page, Integer size) {
     Long result = 0L;
        try {
             result = tipoPruebaRestClient.count(
                        EncodeUtil.encodeBson(filter),
                        EncodeUtil.encodeBson(sort),
                        page, size);
        } catch (Exception e) {
         FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    
    // </editor-fold>
    
    
    
    
        // <editor-fold defaultstate="collapsed" desc="Boolean existsTipoPrueba(TipoPrueba tipoPrueba)">
    /**
     * Verifica si tiene un Sprint con ese nombre para el proyecto
     *
     * @param proyecto
     * @param sprint
     * @return
     */
    @Override
    public Boolean existsTipoPrueba(TipoPrueba tipoPrueba) {
        Boolean result = Boolean.FALSE;
        try {
            Bson filter = eq("tipoPrueba", tipoPrueba.getTipoPrueba() );
            Document sort = new Document("idtipoPrueba", -1);
            Integer total = count(filter, sort, 1, 1).intValue();

            if (total >= 1) {

                result = Boolean.TRUE;
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;

    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Long countLikeByTipoPrueba( String tipoPrueba)">
    @Override
    public Long countLikeByTipoPrueba( String tipoPrueba) {
        Long result = 0L;
        try {
           result = tipoPruebaRestClient.countLikeByTipoPrueba(tipoPrueba);
               
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    
        // <editor-fold defaultstate="collapsed" desc="List<TipoPrueba> likeByTipoPrueba(String tipoPrueba)">
    @Override
    public List<TipoPrueba> likeByTipoPrueba(String tipoPrueba) {
        return tipoPruebaRestClient.likeByTipoPrueba(tipoPrueba);
    }
// </editor-fold>

    
}
