/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.beanupvym.Controller;

import com.kwa.beanupvym.Pesan;
import com.kwa.beanupvym.PesanPK;
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
public class PesanJpaController extends GenericController {

    
    public PesanJpaController(EntityManagerFactory emf, EntityManager em) throws Exception{
        super(emf,em );
    } 


    private boolean isStatusValid(int i){
        //if(i==null) return false;
        if(i==0 || i==1 ) return true;
        return false;
    }
    
    public KWAMesg create(Pesan pesan) throws Exception {
        checkConnection();
        setError("unknown","unknownError");
        if(pesan == null) return setError("pesan","Entity is null");
        
        PesanPK pPK = pesan.getPesanPK();
        if(pPK==null) return setError("pesanPK","Entity is null");
        
        if(Util.isNullOrSpaces(pPK.getYmid())) return setError("YmID","Entity is null or spaces");
        if(Util.isNullOrSpaces(pPK.getTanggal())) return setError("Tanggal","Entity is null or spaces");
        if(Util.isNullOrSpaces(pPK.getWaktu())) return setError("Waktu","Entity is null or spaces");
        
        if(findPesan(pPK)!=null) return setError("Primary Key","Entry already exist");
        
        if(Util.isNullOrSpaces(pesan.getPesan())) return setError("Pesan","Entity is null or spaces");
        if(!isStatusValid(pesan.getTipe())) return setError("Tipe","tipe invalid");
        if(!isStatusValid(pesan.getStatus())) return setError("Status","Status invalid");
        getEm().persist(pesan);
        return setOK("Entry Created");
    }

    public KWAMesg edit(Pesan pesan) throws Exception {
        checkConnection();
        setError("unknown","unknownError");
        if(pesan == null) return setError("pesan","Entity is null");
        
        PesanPK pPK = pesan.getPesanPK();
        if(pPK==null) return setError("pesanPK","Entity is null");
        
        if(Util.isNullOrSpaces(pPK.getYmid())) return setError("YmID","Entity is null or spaces");
        if(Util.isNullOrSpaces(pPK.getTanggal())) return setError("Tanggal","Entity is null or spaces");
        if(Util.isNullOrSpaces(pPK.getWaktu())) return setError("Waktu","Entity is null or spaces");
        
        if(findPesan(pPK)==null) return setError("Primary Key","Entry doesn't exist");
        
        if(Util.isNullOrSpaces(pesan.getPesan())) return setError("Pesan","Entity is null or spaces");
        if(!isStatusValid(pesan.getTipe())) return setError("Tipe","tipe invalid");
        if(!isStatusValid(pesan.getStatus())) return setError("Status","Status invalid");
        getEm().merge(pesan);
        return setOK("Entry Modified");
    }

    public KWAMesg destroy(PesanPK id) throws Exception {
        checkConnection();
        setError("unknown","unknownError");

        if(id==null) return setError("pesanPK","Entity is null");
        
        if(Util.isNullOrSpaces(id.getYmid())) return setError("YmID","Entity is null or spaces");
        if(Util.isNullOrSpaces(id.getTanggal())) return setError("Tanggal","Entity is null or spaces");
        if(Util.isNullOrSpaces(id.getWaktu())) return setError("Waktu","Entity is null or spaces");
        
        Pesan pesan = findPesan(id);
        if(pesan==null) return setError("Primary Key","Entry doesn't exist");
        getEm().remove(pesan);
        return setOK("Entry Deleted");
    }

    public List<Pesan> getUndeliveredMesg(){
        checkConnection();
        Query q = getEm().createNamedQuery("Pesan.findByToBeDelivered", Pesan.class);
        return q.getResultList();
    }
    public List<Pesan> findPesanEntities() {
        return findPesanEntities(true, -1, -1);
    }

    public List<Pesan> findPesanEntities(int maxResults, int firstResult) {
        return findPesanEntities(false, maxResults, firstResult);
    }

    private List<Pesan> findPesanEntities(boolean all, int maxResults, int firstResult) {
        checkConnection();
            CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pesan.class));
            Query q = getEm().createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Pesan findPesan(PesanPK id) {
checkConnection();
            return getEm().find(Pesan.class, id);

    }

    public int getPesanCount() {
checkConnection();
            CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
            Root<Pesan> rt = cq.from(Pesan.class);
            cq.select(getEm().getCriteriaBuilder().count(rt));
            Query q = getEm().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();

    }
    
}
