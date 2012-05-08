/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.pvym;

import com.kwa.beanupvym.Controller.PenggunaJpaController;
import com.kwa.beanupvym.Controller.PesanJpaController;
import com.kwa.beanupvym.Pesan;
import com.kwa.beanupvym.PesanPK;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.FlushModeType;
import org.apache.log4j.Logger;
import org.openymsg.network.FireEvent;
import org.openymsg.network.ServiceType;
import org.openymsg.network.Session;
import org.openymsg.network.YahooProtocol;
import org.openymsg.network.event.SessionAuthorizationEvent;
import org.openymsg.network.event.SessionEvent;
import org.openymsg.network.event.SessionListener;

/**
 *
 * @author ibung
 */
public class YMBatch implements SessionListener {

    private Logger logger = Logger.getLogger(YMBatch.class);
    private Session session = new Session();
    private DateFormat dateFormat = new SimpleDateFormat("yyMMdd hhmmssSSS");

    public static void main(String[] args) {
        try {
            YMBatch yb = new YMBatch(args[0], args[1]);
            yb.initYM();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(YMBatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public YMBatch(String id, String pwd) throws Exception {
        session.login(id, pwd);

    }

    public void initYM() throws Exception {

        session.addSessionListener(this);
        //get admin
        PenggunaJpaController user = new PenggunaJpaController(null,null);
        String[] adminList = user.findAdminAktif();
        
        while (true) {


            PesanJpaController pesan = new PesanJpaController(user.getEmf(), user.getEm());
            List<Pesan> result = pesan.getUndeliveredMesg();
            if (result.isEmpty()) {
                continue;
            }

            
            //pesan = new PesanJpaController(null);
           // pesan = new PesanJpaController(null);
            pesan.initTrx();
            for (int i = 0; i < result.size(); i++) {
                Pesan pes = result.get(i);
                PesanPK ppk = pes.getPesanPK();

                //user.setEm(pesan.getEm());
                //PenggunaJpaController usr = new PenggunaJpaController(pesan.getEm());

                //cek pengguna masih valid gak
                //delete message if pengguna gak valid
                if (!user.isPenggunaValid(ppk.getYmid())) {
                    pesan.destroy(ppk);
                    continue;
                }


                Date dt = dateFormat.parse(ppk.getTanggal() + " " + ppk.getWaktu());
                Date date = new Date();
                if (date.before(dt)) {
                    //gak perlu proses pesan ini
                    continue;
                }

                try {
                    session.sendMessage(ppk.getYmid(), pes.getPesan());
                    //if ym is not admin broadcast the message to admin

                    if (!user.isPenggunaAdmin(adminList, ppk.getYmid())) {
                        for (int j = 0; j < adminList.length; j++) {
                            session.sendMessage(adminList[j], ppk.getYmid() + " : " + pes.getPesan());
                        }
                    }

                                        //pesan = new PesanJpaController(null);
                    
                    pesan.destroy(ppk);

                } catch (Exception e) {

                    e.printStackTrace();

                }


            }// end for i
                                    pesan.commitTrx();
                pesan.getEm().close();


        }
        //session.removeSessionListener(this);

    }

    //pbulic void 
    public void dispatch(FireEvent fe) {
        ServiceType type = fe.getType();

        SessionEvent sessionEvent = fe.getEvent();
        //Session ses = fe.getEvent();


        //TODO: if user not valid ignore
        if(type == ServiceType.Y7_AUTHORIZATION){
            //SessionAuthorizationEvent sae = (SessionAuthorizationEvent) fe.getEvent();
           // Session ses = new Session();
            //ses.acceptFriendAuthorization(null, YahooProtocol.YAHOO);
            try{
                PenggunaJpaController user = new PenggunaJpaController(null,null);
                if(user.isPenggunaValid(sessionEvent.getFrom())){
                    session.acceptFriendAuthorization(sessionEvent.getFrom(), YahooProtocol.YAHOO);
                }else{
                    //SessionAuthorizationEvent sae = (SessionAuthorizationEvent) session.;
                    session.rejectFriendAuthorization((SessionAuthorizationEvent) sessionEvent, sessionEvent.getFrom(), "Please contact admin!");
                    System.out.println(sessionEvent.getFrom() + "rejected!");
                }
            }catch(Exception e){
                logger.error(e, e);
            }
            
            return;
        }
        
        if (type == ServiceType.MESSAGE) {
            try {
                PenggunaJpaController user = new PenggunaJpaController(null,null);
                if (!user.isPenggunaValid(sessionEvent.getFrom())) {
                    System.out.println(sessionEvent.getFrom() + " : " + sessionEvent.getMessage());
                    user.getEm().close();
                    user.getEmf().close();
                    return;
                }

                //PesanJpaController meth = new PesanJpaController(null);


                DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
                DateFormat timeFormat = new SimpleDateFormat("hhmmssSSS");
                //get current date time with Date()
                Date date = new Date();

                //System.out.println(dateFormat.format(date));

                PesanPK ppk = new PesanPK(sessionEvent.getFrom(), dateFormat.format(date), timeFormat.format(date));
                Pesan pes = new Pesan(ppk, sessionEvent.getMessage(), 0, 1);



                String[] adminList = user.findAdminAktif();

                for (int j = 0; j < adminList.length; j++) {
                    if (adminList[j] == ppk.getYmid()) {
                        continue;
                    }
                    session.sendMessage(adminList[j], ppk.getYmid() + " : " + pes.getPesan());
                }


                PesanJpaController pesan = new PesanJpaController(user.getEmf(),user.getEm());

                pesan.initTrx();
                pesan.create(pes);
                pesan.commitTrx();
                pesan.getEm().close();
                pesan.getEmf().close();
                // log request message
                logger.debug("message from " + sessionEvent.getFrom() + " \nmessage " + sessionEvent.getMessage());

                //System.out.println("message from " + sessionEvent.getFrom() + " \nmessage " + sessionEvent.getMessage());
                // give an automatic response
                // session.sendMessage(sessionEvent.getFrom(), "hi, you are sending " + sessionEvent.getMessage());
            } catch (Exception e) {
                logger.error(e, e);
            }
        }
    }
}
