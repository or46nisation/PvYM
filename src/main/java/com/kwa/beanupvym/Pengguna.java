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
@Table(name = "pengguna", catalog = "PvYM", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pengguna.findAll", query = "SELECT p FROM Pengguna p"),
    @NamedQuery(name = "Pengguna.findByYmid", query = "SELECT p FROM Pengguna p WHERE p.ymid = :ymid"),
    @NamedQuery(name = "Pengguna.findByNama", query = "SELECT p FROM Pengguna p WHERE p.nama = :nama"),
    @NamedQuery(name = "Pengguna.findByTipe", query = "SELECT p FROM Pengguna p WHERE p.tipe = :tipe"),
    @NamedQuery(name = "Pengguna.findByPlafon", query = "SELECT p FROM Pengguna p WHERE p.plafon = :plafon"),
    @NamedQuery(name = "Pengguna.findPenggunaAktif", query = "SELECT p FROM Pengguna p WHERE p.ymid = :ymid and p.status = 1"),
    @NamedQuery(name = "Pengguna.findAdminAktif", query = "SELECT p FROM Pengguna p WHERE p.tipe = 2 and p.status = 1"),
    @NamedQuery(name = "Pengguna.findByStatus", query = "SELECT p FROM Pengguna p WHERE p.status = :status")})
public class Pengguna implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ymid", nullable = false, length = 20)
    private String ymid;
    @Basic(optional = false)
    @Column(name = "nama", nullable = false, length = 45)
    private String nama;
    @Basic(optional = false)
    @Column(name = "tipe", nullable = false)
    private int tipe;
    @Basic(optional = false)
    @Column(name = "plafon", nullable = false)
    private double plafon;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;

    public Pengguna() {
    }

    public Pengguna(String ymid) {
        this.ymid = ymid;
    }

    public Pengguna(String ymid, String nama, int tipe, double plafon, int status) {
        this.ymid = ymid;
        this.nama = nama;
        this.tipe = tipe;
        this.plafon = plafon;
        this.status = status;
    }

    public String getYmid() {
        return ymid;
    }

    public void setYmid(String ymid) {
        this.ymid = ymid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public double getPlafon() {
        return plafon;
    }

    public void setPlafon(double plafon) {
        this.plafon = plafon;
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
        hash += (ymid != null ? ymid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pengguna)) {
            return false;
        }
        Pengguna other = (Pengguna) object;
        if ((this.ymid == null && other.ymid != null) || (this.ymid != null && !this.ymid.equals(other.ymid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kwa.beanupvym.Pengguna[ ymid=" + ymid + " ]";
    }
    
}
