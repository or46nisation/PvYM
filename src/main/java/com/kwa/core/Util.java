/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.core;

/**
 *
 * @author ibung
 */
public class Util {
    public static boolean isNullOrSpaces(String str){
        if(str==null) return true;
        if(str.trim().equalsIgnoreCase("")) return true;
        
        return false;
    }
    
}
