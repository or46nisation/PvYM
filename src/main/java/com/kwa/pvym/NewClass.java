/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.pvym;

import com.kwa.beanupvym.Controller.PesanJpaController;
import com.kwa.beanupvym.Controller.PesanJpaController1;
import com.kwa.beanupvym.PesanPK;
import java.util.logging.Level;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

/**
 *
 * @author ibung
 */
public class NewClass {
        public static void main(String[] args) {
        try {
            PesanPK ppk = new PesanPK("arinegara","120422","115010843");
            PesanJpaController pesan = new PesanJpaController(null,null);
            //EntityManagerFactory emf = Persistence.createEntityManagerFactory("NBPvYMPU");
            //PesanJpaController1 pepes = new PesanJpaController1(null, emf);
            pesan.initTrx();
            pesan.destroy(ppk);
            pesan.commitTrx();
            pesan.getEm().close();
            //pesan.getEm().setFlushMode(FlushModeType.COMMIT);
            System.out.println("done");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(YMBatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
