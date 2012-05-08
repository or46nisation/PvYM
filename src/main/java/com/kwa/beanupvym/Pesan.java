/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.beanupvym;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ibung
 */
@Entity
@Table(name = "pesan", catalog = "PvYM", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ymid", "tanggal", "waktu"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pesan.findAll", query = "SELECT p FROM Pesan p"),
    @NamedQuery(name = "Pesan.findByYmid", query = "SELECT p FROM Pesan p WHERE p.pesanPK.ymid = :ymid"),
    @NamedQuery(name = "Pesan.findByTanggal", query = "SELECT p FROM Pesan p WHERE p.pesanPK.tanggal = :tanggal"),
    @NamedQuery(name = "Pesan.findByWaktu", query = "SELECT p FROM Pesan p WHERE p.pesanPK.waktu = :waktu"),
    @NamedQuery(name = "Pesan.findByPesan", query = "SELECT p FROM Pesan p WHERE p.pesan = :pesan"),
    @NamedQuery(name = "Pesan.findByTipe", query = "SELECT p FROM Pesan p WHERE p.tipe = :tipe"),
    @NamedQuery(name = "Pesan.findByToBeDelivered", query = "SELECT p FROM Pesan p WHERE p.tipe = 1 and p.status = 1"),
    @NamedQuery(name = "Pesan.findByStatus", query = "SELECT p FROM Pesan p WHERE p.status = :status")})
public class Pesan implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PesanPK pesanPK;
    @Basic(optional = false)
    @Column(name = "pesan", nullable = false, length = 45)
    private String pesan;
    @Basic(optional = false)
    @Column(name = "tipe", nullable = false)
    private int tipe;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;

    public Pesan() {
    }

    public Pesan(PesanPK pesanPK) {
        this.pesanPK = pesanPK;
    }

    public Pesan(PesanPK pesanPK, String pesan, int tipe, int status) {
        this.pesanPK = pesanPK;
        this.pesan = pesan;
        this.tipe = tipe;
        this.status = status;
    }

    public Pesan(String ymid, String tanggal, String waktu) {
        this.pesanPK = new PesanPK(ymid, tanggal, waktu);
    }

    public PesanPK getPesanPK() {
        return pesanPK;
    }

    public void setPesanPK(PesanPK pesanPK) {
        this.pesanPK = pesanPK;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pesanPK != null ? pesanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesan)) {
            return false;
        }
        Pesan other = (Pesan) object;
        if ((this.pesanPK == null && other.pesanPK != null) || (this.pesanPK != null && !this.pesanPK.equals(other.pesanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pesan[ pesanPK=" + pesanPK + " ]";
    }
    
}
