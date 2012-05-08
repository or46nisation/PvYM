/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.beanupvym.Controller;

import com.kwa.beanupvym.Pengguna;
import com.kwa.core.GenericController;
import com.kwa.core.KWAMesg;
import com.kwa.core.Util;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ibung
 */
public class PenggunaJpaController extends GenericController {

    public PenggunaJpaController(EntityManagerFactory emf, EntityManager em) throws Exception {
        super(emf,em);
    }

    private boolean isTipeValid(int i) {
        //if(i==null) return false;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
            return true;
        }
        return false;
    }

    private boolean isStatusValid(int i) {
        //if(i==null) return false;
        if (i == 0 || i == 1) {
            return true;
        }
        return false;
    }
    
    public boolean isPenggunaValid(String ymid){
        Pengguna p = findPengguna(ymid);
        if(p==null) return false;
        if(p.getStatus()==0) return false;
        return true;
    }

    public KWAMesg create(Pengguna pengguna) throws Exception {
        checkConnection();
        setError("unknown", "unknownError");
        if (Util.isNullOrSpaces(pengguna.getYmid())) {
            return setError("YmID", "Entity is null or spaces");
        }
        if (Util.isNullOrSpaces(pengguna.getNama())) {
            return setError("Nama", "Entity is null or spaces");
        }
        if (!isTipeValid(pengguna.getTipe())) {
            return setError("Tipe", "tipe invalid");
        }
        if (!isStatusValid(pengguna.getStatus())) {
            return setError("Status", "Status invalid");
        }
        if (pengguna.getPlafon() <= 0) {
            return setError("Plafon", "Plafon kurang dari 0");
        }

        getEm().persist(pengguna);
        return setOK("Entry Created");
    }

    public KWAMesg edit(Pengguna pengguna) throws Exception {
        checkConnection();
        setError("unknown", "unknownError");
        if (Util.isNullOrSpaces(pengguna.getYmid())) {
            return setError("YmID", "Entity is null or spaces");
        }

        if (findPengguna(pengguna.getYmid()) == null) {
            return setError("Primary Key", "Entry doesn't exist");
        }
        if (Util.isNullOrSpaces(pengguna.getNama())) {
            return setError("Nama", "Entity is null or spaces");
        }
        if (!isTipeValid(pengguna.getTipe())) {
            return setError("Tipe", "tipe invalid");
        }
        if (!isStatusValid(pengguna.getStatus())) {
            return setError("Status", "Status invalid");
        }
        if (pengguna.getPlafon() <= 0) {
            return setError("Plafon", "Plafon kurang dari 0");
        }



        getEm().merge(pengguna);
        return setOK("Entry Modified");

    }

    public KWAMesg destroy(String id) throws Exception {
        checkConnection();
        setError("unknown", "unknownError");
        if (Util.isNullOrSpaces(id)) {
            return setError("YmID", "Entity is null or spaces");
        }
        if (findPengguna(id) == null) {
            return setError("Primary Key", "Entry doesn't exist");
        }
        Pengguna pengguna = getEm().getReference(Pengguna.class, id);
        getEm().remove(pengguna);

        return setOK("Entry Deleted");
    }

    public String[] findAdminAktif(){
        checkConnection();
        Query q = getEm().createNamedQuery("Pengguna.findAdminAktif", Pengguna.class);
        List<Pengguna> result = q.getResultList();
        
        int nbPengguna = 0;
        if(result!=null){
            nbPengguna = result.size();
        }
        
        String[] listAdmin = new String[nbPengguna];
        
        for(int i=0;i<result.size();i++){
            
            listAdmin[i] = result.get(i).getYmid();
        }
        
        return listAdmin;
        
        
        
    }

    public boolean isPenggunaAdmin(String[] ymids, String ymid){

        if(ymids == null) return false;
        boolean ketemu = false;
        int cntr = 0;
        while(!ketemu && cntr<ymids.length){
            if(ymids[cntr].trim().equalsIgnoreCase(ymid)){
                ketemu = true;
            }else{
                cntr=cntr+1;
            }
        }
        
        return ketemu;

    }
    
    public List<Pengguna> findPenggunaEntities() {
        return findPenggunaEntities(true, -1, -1);
    }

    public List<Pengguna> findPenggunaEntities(int maxResults, int firstResult) {
        return findPenggunaEntities(false, maxResults, firstResult);
    }

    private List<Pengguna> findPenggunaEntities(boolean all, int maxResults, int firstResult) {
checkConnection();
        CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Pengguna.class));
        Query q = getEm().createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Pengguna findPengguna(String id) {
checkConnection();
        return getEm().find(Pengguna.class, id);

    }

    public int getPenggunaCount() {
checkConnection();
        CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
        Root<Pengguna> rt = cq.from(Pengguna.class);
        cq.select(getEm().getCriteriaBuilder().count(rt));
        Query q = getEm().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }
}
