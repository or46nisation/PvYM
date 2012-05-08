/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.core;

/**
 *
 * @author ibung
 */
public class KWAMesg {
    private String mesg;
    private boolean isError;
    private String fieldname;
    private boolean isOK;
    private boolean isWarning;

    public boolean isIsOK() {
        return isOK;
    }

    public void setIsOK(boolean isOK) {
        this.isOK = isOK;
    }

    public boolean isIsWarning() {
        return isWarning;
    }

    public void setIsWarning(boolean isWarning) {
        this.isWarning = isWarning;
    }
    
    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean iserror) {
        this.isError = iserror;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    
    public KWAMesg(){
        mesg = "";
        isError = false;
        fieldname = "";
        isOK = false;
        isWarning = false;
    }
    
    public void setError(String fname, String mesg){
        isError = true;
        isOK = false;
        isWarning = false;
        this.mesg = mesg;
        this.fieldname = fname;
    }
    
    public void setOK(String mesg){
        isError = false;
        isWarning = false;
        isOK = true;
        this.mesg = mesg;
        this.fieldname = "";
        
    }
}
