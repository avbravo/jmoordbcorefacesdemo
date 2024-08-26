/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nerys.services;

import com.nerys.model.Sede;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author avbravo
 */
public interface SedeServices {
      public Map.Entry<Boolean, String> isValid(Sede sede);  
       public Boolean validEmail(String email);
}
