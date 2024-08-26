/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.encode.EncodeUtil;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import com.nerys.model.Area;
import com.nerys.model.DepartamentView;
import com.nerys.services.ProyectoServices;
import com.nerys.model.Proyecto;
import com.nerys.model.User;
import com.nerys.model.UserView;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.nerys.restclient.ProyectoRestClient;
import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class ProyectoServicesImpl implements ProyectoServices {
// <editor-fold defaultstate="collapsed" desc="Services">


// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Rest Client">
    @Inject
    ProyectoRestClient proyectoRestClient;
   
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Proyecto> save(Proyecto proyecto)">
    @Override
    public Optional<Proyecto> save(Proyecto proyecto) {

        try {

            Response response = proyectoRestClient.save(proyecto);

            if (response.getStatus() == 400) {

                String error = (response.readEntity(String.class));

                return Optional.empty();
            }

            Proyecto result = (Proyecto) (response.readEntity(Proyecto.class));

            return Optional.of(result);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Proyecto lookup(User user) ">
    public List<Proyecto> lookup(User user) {
        List<Proyecto> proyectoList = new ArrayList<>();
        try {

            Integer page = 0;
            Integer size = 0;

            Bson filter = and(eq("proyectoMiembro.user.iduser", user.getIduser()),
                    eq("active", Boolean.TRUE),
                    ne("estado", "finalizado")
            );

            Document sort = new Document("proyecto.idproyecto", 1);

            proyectoList = proyectoRestClient.lookup(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return proyectoList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean update(Proyecto proyecto)">
    @Override
    public Boolean update(Proyecto proyecto) {
        Boolean result = Boolean.FALSE;
        try {
            Response response = proyectoRestClient.update(proyecto);
            if (response.getStatus() == 201) {
                result = Boolean.TRUE;
            }
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean nombreProyectoExist(String proyectoName) ">
    @Override
    public Boolean nombreProyectoExiste(String proyectoName) {
        Boolean result = Boolean.FALSE;
        try {
            Integer page = 0;
            Integer size = 0;
            Bson filter = and(
                    eq("proyecto", proyectoName)
            );

            Document sort = new Document("idproyecto", 1);
            if (count(filter, sort, page, size) > 0) {
                result = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Proyecto> lookup(Bson filter, Document sort, Integer page, Integer size)">
    @Override
    public List<Proyecto> lookup(Bson filter, Document sort, Integer page, Integer size) {
        List<Proyecto> proyectoList = new ArrayList<>();
        try {
            proyectoList = proyectoRestClient.lookup(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return proyectoList;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Long count(Bson filter, Document sort, Integer page, Integer size)">
    @Override
    public Long count(Bson filter, Document sort, Integer page, Integer size) {
        Long result = 0L;
        try {
            result = proyectoRestClient.count(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Proyecto>findByIdproyecto findByIdproyecto(Long idproyecto) ">
    @Override
    public Optional<Proyecto> findByIdproyecto(Long idproyecto) {
        try {
            Proyecto result = proyectoRestClient.findByIdproyecto(idproyecto);
            if (result == null || result.getIdproyecto() == null) {

            } else {
                return Optional.of(result);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<Proyecto> proyectoConIgualNombre(String proyectoName)">
    @Override
    public Optional<Proyecto> proyectoConIgualNombre(String proyectoName) {
        Optional<Proyecto> result = Optional.empty();
        try {
            Integer page = 0;
            Integer size = 0;
            Bson filter = and(
                    eq("proyecto", proyectoName)
            );

            Document sort = new Document("idproyecto", 1);
            List<Proyecto> list = lookup(filter, sort, page, size);
            if (list == null || list.isEmpty()) {
                return Optional.empty();
            }
            result = Optional.of(list.get(0));
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean isValidProyecto(Proyecto proyecto,String[] diasLaborableSelected, JmoordbResourcesFiles rf)">
    @Override
    public Boolean isValidProyecto(Proyecto proyecto, List<DepartamentView> departamentViewList, List<Area> areaList, List<UserView> userViewPropietarioSelectedList, JmoordbResourcesFiles rf) {
        Boolean result = Boolean.FALSE;
        try {
            if (proyecto.getProyecto() == null || proyecto.getProyecto().equals("")) {

                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.nombreproyecto"));
                return result;
            }

            if (proyecto.getDescripcion() == null || proyecto.getDescripcion().equals("")) {

                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.descripcion"));
                return result;
            }

            if (departamentViewList == null || departamentViewList.isEmpty()) {

                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.departamentoproyecto"));
                return result;
            }

            if (areaList == null || areaList.isEmpty()) {

                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.areaproyecto"));
                return result;
            }
            if (userViewPropietarioSelectedList == null || userViewPropietarioSelectedList.isEmpty()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.propietarioproyecto"));
                return result;
            }

            result = Boolean.TRUE;
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="DiasLaborable procesarDias(String[] diasLaborableSelected, JmoordbResourcesFiles rf) ">
//
//    @Override
//    
//    public DiasLaborable procesarDiasLaborable(String[] diasLaborableSelected, JmoordbResourcesFiles rf) {
//        DiasLaborable diasLaborable = new DiasLaborable(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
//        try {
//             
//
//            for (String s : diasLaborableSelected) {
//                if (s.equals(rf.fromMessage("field.lunes"))) {
//                    diasLaborable.setLunes(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.martes"))) {
//                    diasLaborable.setMartes(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.miercoles"))) {
//                    diasLaborable.setMiercoles(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.jueves"))) {
//                    diasLaborable.setJueves(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.viernes"))) {
//                    diasLaborable.setViernes(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.sabado"))) {
//                    diasLaborable.setSabado(Boolean.TRUE);
//                }
//                if (s.equals(rf.fromMessage("field.domingo"))) {
//                    diasLaborable.setDomingo(Boolean.TRUE);
//                }
//            }
//
//       } catch (Exception e) {
//            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
//        }
//        return diasLaborable;
//    }
//    
//    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean isPropietarioDelProyecto(Proyecto proyecto, User userLogged)">
    public Boolean isPropietarioDelProyecto(Proyecto proyecto, User userLogged) {
        Boolean result = Boolean.FALSE;
        try {
//            for (ProyectoMiembro pm : proyecto.getProyectoMiembro()) {
//                if (pm.getUserView().getIduser().equals(userLogged.getIduser())) {
//                    if (pm.getPropietario()) {
//                        result = Boolean.TRUE;
//                        break;
//                    }
//
//                }
//            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isMiembroDelProyecto(Proyecto proyecto, User userLogged)">
    public Boolean isMiembroDelProyecto(Proyecto proyecto, User userLogged) {
        Boolean result = Boolean.FALSE;
        try {
//            for (ProyectoMiembro pm : proyecto.getProyectoMiembro()) {
//                if (pm.getUserView().getIduser().equals(userLogged.getIduser())) {
//                    if (!pm.getPropietario()) {
//                        result = Boolean.TRUE;
//                        break;
//                    }
//
//                }
//            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Long countLikeByProyecto( String proyecto)">
    @Override
    public Long countLikeByProyecto(String proyecto) {
        Long result = 0L;
        try {
//            result = tarjetaRestClient.countLikeByTarjeta(tarjeta);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Proyecto> likeByProyecto(String proyecto)">
    @Override
    public List<Proyecto> likeByProyecto(String proyecto) {
        return proyectoRestClient.likeByProyecto(proyecto);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Map.Entry<Boolean, String> isValid(Proyecto proyecto, String juridicoproperties)">
    @Override
    public Map.Entry<Boolean, String> isValid(Proyecto proyecto, String juridicoproperties) {

        Map.Entry<Boolean, String> result;
        try {
            if (proyecto.getProyecto() == null || proyecto.getProyecto().equals("")) {
                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseproyecto");
            } else {
                if (proyecto.getDescripcion() == null || proyecto.getDescripcion().equals("")) {
                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.descripcion");
                } else {
                    if (proyecto.getDireccion() == null || proyecto.getDireccion().equals("")) {
                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresedireccion");
                    } else {
                        if (proyecto.getTelefono() == null || proyecto.getTelefono().equals("")) {
                            result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresetelefono");
                        } else {

                            if (proyecto.getEstado() == null || proyecto.getEstado().equals("")) {
                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseestado");
                            } else {
                                if (proyecto.getTipocliente() == null || proyecto.getTipocliente().equals("")) {
                                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresetipocliente");
                                } else {

                                    if (proyecto.getCliente() == null || proyecto.getCliente().getIdcliente() == null) {
                                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresecliente");
                                    } else {
                                        if (proyecto.getTipocliente().equals(juridicoproperties)) {
                                            if (proyecto.getRepresentante() == null || proyecto.getRepresentante().getCedula().equals("")) {
                                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreserepresentante");
                                            } else {
                                                if (proyecto.getSede() == null || proyecto.getSede().getIdsede().equals("")) {
                                                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresesede");
                                                } else {

                                                }
                                            }
                                        }
                                        if (proyecto.getEmail() == null || proyecto.getEmail().equals("")) {
                                            result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseemail");
                                        } else {
                                            if (!validEmail(proyecto.getEmail())) {
                                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.emailnovalido");
                                            } else {
                                                result = new AbstractMap.SimpleEntry<>(Boolean.TRUE, "");
                                            }

                                        }

                                    }

                                }

                            }
                        }
                    }

                }
            }

            return result;

        } catch (Exception e) {
        }
        return new AbstractMap.SimpleEntry<>(Boolean.FALSE, "");
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean validEmail(String email)">
    @Override
    public Boolean validEmail(String email) {
        Boolean result = Boolean.FALSE;
        try {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            result = matcher.find();

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
}
