/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.services;

import com.nerys.model.UserView;
import java.util.List;

/**
 *
 * @author avbravo
 */
public interface DashboardFacesServices {

    public List<UserView> loadUserViewHowPropietarioNewOrEdit(String query, List<UserView> userViewList);
    public List<UserView> loadUserViewHowColaboradoresNewOrEdit(String query, List<UserView> userViewList);
}
