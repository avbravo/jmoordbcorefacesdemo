/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.encode.EncodeUtil;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import com.nerys.model.Ensayo;
import com.nerys.model.TipoPrueba;
import com.nerys.restclient.EnsayoRestClient;
import com.nerys.services.EnsayoServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Comparator;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class EnsayoServicesImpl implements EnsayoServices {
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    JmoordbResourcesFiles rf;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Rest Client">
    @Inject
    EnsayoRestClient ensayoRestClient;
// </editor-fold>

    @Override
    public List<Ensayo> findAll() {
        return ensayoRestClient.findAll();
    }

    @Override
   public Optional<Ensayo> findByIdensayo(String idensayo) {

        try {
           Ensayo result =ensayoRestClient.findByIdensayo(idensayo);
            if (result == null || result.getIdensayo()== null) {

            } else {
                return Optional.of(result);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
        
    }

    @Override
    public List<Ensayo> findByEnsayo(String ensayo) {
        return ensayoRestClient.findByEnsayo(ensayo);
    }

    // <editor-fold defaultstate="collapsed" desc="Optional<Ensayo> save(Ensayo ensayo)">
    @Override
    public Optional<Ensayo> save(Ensayo ensayo) {

        try {

            Response response = ensayoRestClient.save(ensayo);

            if (response.getStatus() == 400) {

                String error = (response.readEntity(String.class));

                return Optional.empty();
            }

            Ensayo result = (Ensayo) (response.readEntity(Ensayo.class));

            return Optional.of(result);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean update(Ensayo ensayo)">
    @Override
    public Boolean update(Ensayo ensayo) {
        Boolean result = Boolean.FALSE;
        try {

            Integer status = ensayoRestClient.update(ensayo).getStatus();

            if (status == 201) {
                result = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean delete(String idensayo)">
    @Override
    public Boolean delete(String idensayo) {
        Boolean result = Boolean.FALSE;
        try {

            Integer status = ensayoRestClient.delete(idensayo).getStatus();

            if (status == 201) {
                result = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Ensayo> lookup(Bson filter, Document sort, Integer page, Integer size)">
    @Override
    public List<Ensayo> lookup(Bson filter, Document sort, Integer page, Integer size) {
        List<Ensayo> ensayoList = new ArrayList<>();
        try {
            ensayoList = ensayoRestClient.lookup(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return ensayoList;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Long count(Bson filter, Document sort, Integer page, Integer size)">

    @Override
    public Long count(Bson filter, Document sort, Integer page, Integer size) {
        Long result = 0L;
        try {
            result = ensayoRestClient.count(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean existsEnsayo(Ensayo ensayo)">
    /**
     * Verifica si tiene un Sprint con ese nombre para el proyecto
     *
     * @param proyecto
     * @param sprint
     * @return
     */
    @Override
    public Boolean existsEnsayo(Ensayo ensayo) {
        Boolean result = Boolean.FALSE;
        try {

            Bson filter = and(
                    eq("ensayo", ensayo.getEnsayo()),
                    eq("tipoPrueba.idtipoprueba", ensayo.getTipoPrueba().getIdtipoprueba())
            );
            Document sort = new Document("idensayo", -1);
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
    // <editor-fold defaultstate="collapsed" desc="Boolean existsEnsayoWithDiferentId(Ensayo ensayo)">
    /**
     *
     * @param ensayo
     * @return Verifica que no exista otro tipo y grupo generalmente para editar
     */
    @Override
    public Boolean existsEnsayoWithDiferentId(Ensayo ensayo) {
        Boolean result = Boolean.FALSE;
        try {

            Bson filter = and(
                    eq("ensayo", ensayo.getEnsayo()),
                    eq("tipoPrueba.idtipoprueba", ensayo.getTipoPrueba().getIdtipoprueba()),
                    ne("idensayo", ensayo.getIdensayo())
            );
            Document sort = new Document("idensayo", -1);
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

    // <editor-fold defaultstate="collapsed" desc="Long countLikeByEnsayo( String ensayo)">
    @Override
    public Long countLikeByEnsayo(String ensayo) {
        Long result = 0L;
        try {
            result = ensayoRestClient.countLikeByEnsayo(ensayo);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Ensayo> likeByEnsayo(String ensayo)">
    @Override
    public List<Ensayo> likeByEnsayo(String ensayo) {
        return ensayoRestClient.likeByEnsayo(ensayo);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Ensayo> loadEnsayoByTipoPrueba(TipoPrueba tipoPrueba)">
    public List<Ensayo> loadEnsayoByTipoPrueba(TipoPrueba tipoPrueba) {
        List<Ensayo> ensayoList = new ArrayList<>();
        try {

            Integer page = 0;
            Integer size = 0;
            Bson filter = new Document("active", Boolean.TRUE);

            Document sort = new Document("ensayo", 1);

          
                List<Ensayo> tipoTarjetaListTemp = new ArrayList<>();

               
                    Bson filter1 = and(filter,
                            eq("tipoPrueba.idtipoprueba", tipoPrueba.getIdtipoprueba())
                    );
                    tipoTarjetaListTemp = lookup(
                            filter1,
                            sort,
                            page, size);
                    if (tipoTarjetaListTemp == null || tipoTarjetaListTemp.isEmpty()) {

                    } else {
                        for (Ensayo tp : tipoTarjetaListTemp) {
                            ensayoList.add(tp);
                        }
                    }
                

                if (ensayoList == null || ensayoList.isEmpty()) {

                } else {
                    Comparator<Ensayo> comparator
                            = (c1, c2) -> c1.getEnsayo().compareTo(c2.getEnsayo());

                    ensayoList.sort(comparator);
                }
           

        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return ensayoList;
    }
// </editor-fold>

}
