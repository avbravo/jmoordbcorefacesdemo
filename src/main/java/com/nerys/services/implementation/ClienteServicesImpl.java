/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.services.implementation;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.encode.EncodeUtil;
import static com.mongodb.client.model.Filters.eq;
import com.nerys.model.Cliente;
import com.nerys.restclient.ClienteRestClient;
import com.nerys.services.ClienteServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class ClienteServicesImpl implements ClienteServices {
    // <editor-fold defaultstate="collapsed" desc="@Inject">

    @Inject
    JmoordbResourcesFiles rf;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Rest Client">
    @Inject
    ClienteRestClient clienteRestClient;
// </editor-fold>

    @Override
    public List<Cliente> findAll() {
        return clienteRestClient.findAll();
    }

    @Override
    public Optional<Cliente> findByIdcliente(Long idcliente) {
        try {
            Cliente cliente = clienteRestClient.findByIdcliente(idcliente);
            if (cliente == null || cliente.getIdcliente() == null) {

            } else {
                return Optional.of(cliente);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cliente> findByName(String name) {
        try {
            Cliente cliente = clienteRestClient.findByName(name);
            if (cliente == null || cliente.getIdcliente() == null) {

            } else {
                return Optional.of(cliente);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        try {
            Cliente cliente = clienteRestClient.findByEmail(email);
            if (cliente == null || cliente.getIdcliente() == null) {

            } else {
                return Optional.of(cliente);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }

    @Override
    public Optional<Cliente> findByCedularuc(String cedularuc) {
        try {
            Cliente cliente = clienteRestClient.findByCedularuc(cedularuc);
            if (cliente == null || cliente.getIdcliente() == null) {

            } else {

                return Optional.of(cliente);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Optional.empty();

    }
    @Override
    public Optional<Cliente> findByDV(String dv) {
        try {
            Cliente cliente = clienteRestClient.findByCedularuc(dv);
            if (cliente == null || cliente.getIdcliente() == null) {

            } else {

                return Optional.of(cliente);
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return Optional.empty();

    }

    // <editor-fold defaultstate="collapsed" desc="Optional<Cliente> save(Cliente cliente)">
    @Override
    public Optional<Cliente> save(Cliente cliente) {

        try {

            Response response = clienteRestClient.save(cliente);
            if (response.getStatus() == 400) {

                String error = (response.readEntity(String.class));

                return Optional.empty();
            }

            Cliente result = (Cliente) (response.readEntity(Cliente.class));

            return Optional.of(result);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return Optional.empty();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean update(Cliente cliente)">
    @Override
    public Boolean update(Cliente cliente) {
        Boolean result = Boolean.FALSE;
        try {

            Integer status = clienteRestClient.update(cliente).getStatus();

            if (status == 201) {
                result = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean delete(Long idcliente)">
    @Override
    public Boolean delete(Long idcliente) {
        Boolean result = Boolean.FALSE;
        try {

            Integer status = clienteRestClient.delete(idcliente).getStatus();

            if (status == 201) {
                result = Boolean.TRUE;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Cliente> lookup(Bson filter, Document sort, Integer page, Integer size)">
    @Override
    public List<Cliente> lookup(Bson filter, Document sort, Integer page, Integer size) {
        List<Cliente> ClienteList = new ArrayList<>();
        try {
            ClienteList = clienteRestClient.lookup(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return ClienteList;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Long count(Bson filter, Document sort, Integer page, Integer size)">

    @Override
    public Long count(Bson filter, Document sort, Integer page, Integer size) {
        Long result = 0L;
        try {
            result = clienteRestClient.count(
                    EncodeUtil.encodeBson(filter),
                    EncodeUtil.encodeBson(sort),
                    page, size);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Cliente> likeByName(String name)">
    @Override
    public List<Cliente> likeByName(String name) {
        return clienteRestClient.likeByName(name);
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
    // <editor-fold defaultstate="collapsed" desc="Boolean validCliente(Cliente clienteSelected)">
    @Override
    public Boolean validCliente(Cliente clienteSelected) {
        Boolean result = Boolean.FALSE;
        try {
            if (clienteSelected.getName() == null || clienteSelected.getName().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.Clientename"));
                return result;
            }
            if (clienteSelected.getName() == null || clienteSelected.getName().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.name"));
                return result;
            }

            if (clienteSelected.getCedularuc() == null || clienteSelected.getCedularuc().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.cedularuc"));
                return result;
            }
            if (clienteSelected.getTipocliente() == null || clienteSelected.getTipocliente().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.tipocliente"));
                return result;
            }
            if (clienteSelected.getDireccion() == null || clienteSelected.getDireccion().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.direccion"));
                return result;
            }

            if (clienteSelected.getTipocliente().equals("juridico")) {
                if (clienteSelected.getRepresentante() == null || clienteSelected.getRepresentante().isEmpty()) {
                    FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.representante"));
                    return result;
                }

                if (clienteSelected.getSede() == null || clienteSelected.getSede().isEmpty()) {
                    FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.sede"));
                    return result;
                }
            }

            if (!validEmail(clienteSelected.getEmail())) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("formato.emailnovalido"));
                return result;
            }
            if (!validEmail(clienteSelected.getObservacion())) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.observacion"));
                return result;
            }
            result = Boolean.TRUE;

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean existsCliente(Cliente cliente)">
    /**
     * Verifica si tiene un Sprint con ese nombre para el proyecto
     *
     * @param proyecto
     * @param sprint
     * @return
     */
    @Override
    public Boolean existsCliente(Cliente cliente) {
        Boolean result = Boolean.FALSE;
        try {
            Bson filter = eq("name", cliente.getName());
            Document sort = new Document("idcliente", -1);
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

       // <editor-fold defaultstate="collapsed" desc="Map.Entry<Boolean, String> isValid(Cliente cliente)">

    @Override
    public Map.Entry<Boolean, String> isValid(Cliente cliente) {

        Map.Entry<Boolean, String> result;
        try {
            if (cliente.getCedularuc() == null || cliente.getCedularuc().equals("")) {
                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresecedularuc");
            } else {
                if (cliente.getName() == null || cliente.getName().equals("")) {
                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresenombre");
                } else {
                    if (cliente.getDireccion() == null || cliente.getDireccion().equals("")) {
                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresedireccion");
                    } else {
                        if (cliente.getTelefono() == null || cliente.getTelefono().equals("")) {
                            result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresetelefono");
                        } else {

                            if (cliente.getCelular() == null || cliente.getCelular().equals("")) {
                                result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresecelular");
                            } else {
                                if (cliente.getDv() == null || cliente.getDv().equals("")) {
                                    result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingresedv");
                                } else {
                                    if (cliente.getEmail() == null || cliente.getEmail().equals("")) {
                                        result = new AbstractMap.SimpleEntry<>(Boolean.FALSE, "warning.ingreseemail");
                                    } else {
                                        if (!validEmail(cliente.getEmail())) {
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

            return result;

        } catch (Exception e) {
        }
        return new AbstractMap.SimpleEntry<>(Boolean.FALSE, "");
    }
// </editor-fold>


 // <editor-fold defaultstate="collapsed" desc="List<Cliente> likeByCedularuc(String cedularuc)">
    @Override
    public List<Cliente> likeByCedularuc(String cedularuc) {
        return clienteRestClient.likeByCedularuc(cedularuc); 
    }
// </editor-fold>



}
