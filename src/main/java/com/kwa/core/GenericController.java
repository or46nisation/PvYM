/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.core;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ibung
 */
public  class GenericController implements Serializable{
    private EntityManagerFactory emf;

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManager em; 
    private KWAMesg pmesg;
    private static final String connName = "NBPvYMPU"; 

    public KWAMesg getPmesg() {
        return pmesg;
    }

    public void checkConnection(){
        if(this.emf==null) this.emf = Persistence.createEntityManagerFactory(connName);
        
        
        if(!this.emf.isOpen())  this.emf = Persistence.createEntityManagerFactory(connName);
        
        if(this.em==null) this.em = this.emf.createEntityManager();
        
        if(!this.em.isOpen()) this.em = this.emf.createEntityManager();
    }
    public void setPmesg(KWAMesg pmesg) {
        this.pmesg = pmesg;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
       
    public GenericController(EntityManagerFactory emf, EntityManager em){
     
        this.emf = emf;
        this.em = em;
        checkConnection();
        
    }
       
    public void initTrx(){
        em.getTransaction().begin();
    }
    
    public void commitTrx(){
        em.getTransaction().commit();
        
    }
    
    public KWAMesg setError(String fname, String mesg){
        pmesg = new KWAMesg();
        pmesg.setError(fname, mesg);
        return pmesg;
    }
    
    public KWAMesg setOK(String mesg){
        pmesg = new KWAMesg();
        pmesg.setOK(mesg);
        return pmesg;
        
    }
    
}
