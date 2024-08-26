/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.faces;

import com.avbravo.jmoordbutils.ConsoleUtil;
import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaContext;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaManager;
import com.nerys.commons.emails.EmailSenderEvent;
import com.nerys.faces.services.DashboardFacesServices;
import com.nerys.services.IconoServices;
import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class SetupFaces implements Serializable {

// <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    JmoordbResourcesFiles rf;
    @Inject
    JmoordbCoreMediaManager jmoordbCoreMediaManager;
    @Inject
    JmoordbCoreMediaContext jmoordbCoreMediaContext;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Event">
    @Inject
    Event<EmailSenderEvent> emailSenderEvent;
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Services">
    
    @Inject
    DashboardFacesServices dashboardFacesServices;
    @Inject
    IconoServices iconoServices;

    

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String version1_0_b_38() ">
    public String version1_0_b_38() {
        try {
            ConsoleUtil.test("\t iniciando proceso....");
           
            FacesUtil.successMessage(rf.fromMessage("info.procesofinalizado"));
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";

    }
    // </editor-fold>


}
