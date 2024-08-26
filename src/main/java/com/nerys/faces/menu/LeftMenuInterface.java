/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.menu;

import com.jmoordb.core.annotation.faces.leftmenu.FacesLeftMenu;
import com.jmoordb.core.annotation.faces.leftmenu.FacesLeftMenuCommandLink;
import com.jmoordb.core.annotation.faces.leftmenu.FacesLeftMenuDescription;
import com.jmoordb.core.annotation.faces.enumerations.FacesIcon;
import com.jmoordb.core.annotation.faces.leftmenu.FacesLeftMenuSeparator;

/**
 *
 * @author avbravo
 */
@FacesLeftMenuDescription()
public interface LeftMenuInterface {
   @FacesLeftMenuCommandLink(facesIcon = FacesIcon.HOME,rolesAllowed = {"ADMIN,ALL"},id = "menuHome",action="dashboard")
   public void home();
   
   @FacesLeftMenu(facesIcon = FacesIcon.AMAZON,rolesAllowed = {"ADMIN,ALL"},id="menuClientes",title = "#{submenu.icono}",trigger = "menuClientesCommandButton")
   public void menuClientes();
   
   @FacesLeftMenuSeparator()
   public void separator1();
   
   @FacesLeftMenu(facesIcon = FacesIcon.BOX,rolesAllowed = {"ADMIN,ALL"},id="menuRegistros",title = "#{submenu.registros}",trigger = "menuRegistrosCommandButton")
   public void menuRegistros();
   
}
