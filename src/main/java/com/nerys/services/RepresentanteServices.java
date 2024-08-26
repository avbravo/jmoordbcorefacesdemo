/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.nerys.model.Representante;
import java.util.Map;

/**
 *
 * @author avbravo
 */
public interface RepresentanteServices {
      public Map.Entry<Boolean, String> isValid(Representante representante);  
       public Boolean validEmail(String email);
}
