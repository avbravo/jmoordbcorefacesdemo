/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.nerys.model.Area;
import com.nerys.model.DepartamentView;
import com.nerys.model.Proyecto;
import com.nerys.model.User;
import com.nerys.model.UserView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
public interface ProyectoServices {

    public List<Proyecto> lookup(User user);

    public List<Proyecto> lookup(Bson filter, Document sort, Integer page, Integer size);

    Long count(Bson filter, Document sort, Integer page, Integer size);

    public Optional<Proyecto> findByIdproyecto(Long idproyecto);

    public Optional<Proyecto> save(Proyecto proyecto);

    public Boolean update(Proyecto proyecto);

    public Boolean nombreProyectoExiste(String proyectoName);

    public Optional<Proyecto> proyectoConIgualNombre(String proyectoName);

    public Boolean isValidProyecto(Proyecto proyecto, List<DepartamentView> departamentViewList, List<Area> areaList, List<UserView> userViewPropietarioSelectedList, JmoordbResourcesFiles rf);

    public Long countLikeByProyecto(String proyecto);

    public List<Proyecto> likeByProyecto(String proyecto);
//selectonemenu.juridico
    Map.Entry<Boolean, String> isValid(Proyecto proyecto, String juridicoproperties);

    public Boolean validEmail(String email);
}
