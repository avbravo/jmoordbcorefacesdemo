/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.faces.menu;

import com.jmoordb.core.annotation.faces.enumerations.FacesIcon;
import com.jmoordb.core.annotation.faces.leftmenu.FacesMenuCommandLink;
import com.jmoordb.core.annotation.faces.leftmenu.FacesMenuBar;
import com.jmoordb.core.annotation.faces.leftmenu.FacesMenuItem;
import com.jmoordb.core.annotation.faces.leftmenu.FacesMenuSeparator;
import com.jmoordb.core.annotation.faces.leftmenu.FacesSubMenu;
import com.jmoordb.core.annotation.faces.template.FacesLeftMenuTemplate;

/**
 *
 * @author avbravo
 */
@FacesLeftMenuTemplate()
public interface LeftMenuInterface {

    @FacesMenuCommandLink(facesIcon = FacesIcon.HOME, rolesAllowed = {"ADMIN,ALL"}, action = "dashboard")
    public void home();

    @FacesMenuBar(facesIcon = FacesIcon.AMAZON, rolesAllowed = {"ADMIN,ALL"}, id = "menuClientes", title = "#{submenu.icono}", trigger = "menuClientesCommandButton")
    public void menuClientes();

    @FacesMenuSeparator()
    public void separator1();
    
    @FacesMenuBar(facesIcon = FacesIcon.BOX, rolesAllowed = {"ADMIN,ALL"}, title = "#{submenu.registros}")
    public void menuRegistros();
    
    @FacesSubMenu(menuBarFather = "menuRegistros", title = "#{submenu.general}")
    public void subMenuGeneral();
    
    
     @FacesMenuSeparator()
    public void separator2();
    
    @FacesMenuBar(facesIcon = FacesIcon.BOX, rolesAllowed = {"ADMIN,ALL"}, title = "#{submenu.reportes}")
    public void menuReportes();

    @FacesSubMenu(menuBarFather = "menuReportes", title = "#{submenu.proyecto}",rolesAllowed = {"ALL"})
    public void subMenuProyecto();

    @FacesMenuItem(action = "misproyectos",title = "#{menuitem.misproyectos}",facesIcon = FacesIcon.FOLDER,rolesAllowed = {"ADMIN,COLABORADOR"}, subMenuFather = "subMenuProyecto")
    public void menuItemMisProyectos();

    @FacesSubMenu(menuBarFather = "menuReportes",title = "#{submenu.otros}", rolesAllowed = {"ALL"})
    public void subMenuOtros();

}
