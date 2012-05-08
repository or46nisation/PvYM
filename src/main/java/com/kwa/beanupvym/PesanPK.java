/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.beanupvym;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ibung
 */
@Embeddable
public class PesanPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ymid", nullable = false, length = 20)
    private String ymid;
    @Basic(optional = false)
    @Column(name = "tanggal", nullable = false, length = 6)
    private String tanggal;
    @Basic(optional = false)
    @Column(name = "waktu", nullable = false, length = 9)
    private String waktu;

    public PesanPK() {
    }

    public PesanPK(String ymid, String tanggal, String waktu) {
        this.ymid = ymid;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getYmid() {
        return ymid;
    }

    public void setYmid(String ymid) {
        this.ymid = ymid;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ymid != null ? ymid.hashCode() : 0);
        hash += (tanggal != null ? tanggal.hashCode() : 0);
        hash += (waktu != null ? waktu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PesanPK)) {
            return false;
        }
        PesanPK other = (PesanPK) object;
        if ((this.ymid == null && other.ymid != null) || (this.ymid != null && !this.ymid.equals(other.ymid))) {
            return false;
        }
        if ((this.tanggal == null && other.tanggal != null) || (this.tanggal != null && !this.tanggal.equals(other.tanggal))) {
            return false;
        }
        if ((this.waktu == null && other.waktu != null) || (this.waktu != null && !this.waktu.equals(other.waktu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PesanPK[ ymid=" + ymid + ", tanggal=" + tanggal + ", waktu=" + waktu + " ]";
    }
    
}
