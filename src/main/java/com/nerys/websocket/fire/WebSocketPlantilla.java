/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.websocket.fire;

import com.nerys.model.Proyecto;
import com.nerys.model.User;
import com.nerys.model.UserView;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author avbravo
 */
public interface WebSocketPlantilla {

    public void sendMessageAll(String message, String scoped);
    
    public void sendMessageCliente(String message, User user);
    
    public void sendMessageClienteMultiple(String message, List<UserView> userViewList);
    

    public void sendMessageProyecto(String message, User user);

    public void sendMessageProyectoMultiple(String message, List<UserView> userViewList);
    
    
    public void sendMessageSprint(String message, User user);

    public void sendMessageSprintMultiple(String message, List<UserView> userViewList);

    public void sendMessageTablero(String message, User user);

    public void sendMessageTableroMultiple(String message, List<UserView> userViewList);

    public void sendMessageBacklog(String message, User user);

    public void sendMessageBacklogMultiple(String message, List<UserView> userViewList);
    
    public void sendMessagePapeleraReciclaje(String message, User user);

    public void sendMessagePapeleraReciclajeMultiple(String message, List<UserView> userViewList);
    
    public void sendMessagePanelTrabajo(String message, User user);

    public void sendMessagePanelTrabajoMultiple(String message, List<UserView> userViewList);

    public Collection<Long> collectionOfUserList(List<UserView> userList);
}
