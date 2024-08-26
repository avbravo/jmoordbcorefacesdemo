/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.websocket.fire;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.websocket.MessageWebSocket;
import com.nerys.model.Proyecto;
import com.nerys.model.User;
import com.nerys.model.UserView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface FacesWebSocket {

    public String refreshFromWebsocket();

    public String updateForRemoteWebsocket();

    

    public void sendMensajesWebsocket(MessageWebSocket messageWebSocket, Boolean exclude);

    public void onCompleteWebSocket();


    // <editor-fold defaultstate="collapsed" desc="List<UserView> filterUserViewListForWebSocket(List<UserView> userViewProjectoSelectedForWebSocketList,Boolean exclude, User... excludeUser) {">
    public default List<UserView> filterUserViewListForWebSocket(List<UserView> userViewProjectoSelectedForWebSocketList, Boolean exclude, User... excludeUser) {
        List<UserView> result = new ArrayList<>();

        try {
            Long iduser = 0l;
            if (excludeUser.length != 0) {
                iduser = excludeUser[0].getIduser();

            }
            if (!exclude) {
                result.addAll(userViewProjectoSelectedForWebSocketList);
            } else {
                for (UserView uv : userViewProjectoSelectedForWebSocketList) {
                           if(!uv.getIduser().equals(iduser)){
                               result.add(uv);
                           }
                }
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<UserView> userViewFromProjectExclude(Proyecto proyectoSelected, Boolean exclude, User... excludeUser)">
    /**
     *
     * @param proyectoSelected
     * @return Obtiene la lista de userView del proyecto
     */
    public default List<UserView> userViewFromProjectExclude(Proyecto proyectoSelected, Boolean exclude, User... excludeUser) {
        List<UserView> result = new ArrayList<>();
        try {
            Long iduser = 0l;
            if (excludeUser.length != 0) {
                iduser = excludeUser[0].getIduser();
//                for (ProyectoMiembro pm : proyectoSelected.getProyectoMiembro()) {
//                    if (exclude) {
//                        if (iduser.equals(pm.getUserView().getIduser())) {
//                        } else {
//                            result.add(pm.getUserView());
//                        }
//                    } else {
//                        result.add(pm.getUserView());
//                    }
//
//                }
            } else {
//                proyectoSelected.getProyectoMiembro().forEach(pm -> {
//                    result.add(pm.getUserView());
//                });
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }

    // </editor-fold>   
   
   
   
    public default String validarEstadoIniciadoYFechaProyecto(Proyecto proyecto, JmoordbResourcesFiles rf) {
        String result = "";
        try {

            if (!proyecto.getEstado().equals("iniciado")) {
                result = rf.fromMessage("warning.proyectoestadonoiniciado");

            }

//            if ((JmoordbCoreDateUtil.fechaIgual(JmoordbCoreDateUtil.getFechaHoraActual(), proyecto.getFechainicial())
//                    || JmoordbCoreDateUtil.fechaMayor(JmoordbCoreDateUtil.getFechaHoraActual(), proyecto.getFechainicial()))
//                    && (JmoordbCoreDateUtil.fechaIgual(JmoordbCoreDateUtil.getFechaHoraActual(), proyecto.getFechafinal())
//                    || JmoordbCoreDateUtil.fechaMenor(JmoordbCoreDateUtil.getFechaHoraActual(), proyecto.getFechafinal()))) {
//
//            } else {
//                result = rf.fromMessage("warning.fechasfuerarangoproyecto");
//
//            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
}
