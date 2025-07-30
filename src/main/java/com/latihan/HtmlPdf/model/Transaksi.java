package com.latihan.HtmlPdf.model;

import jakarta.persistence.*;


@Entity
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tanggalTrx;
    private String tanggalValuta;
    private String uraian;
    private String debet;
    private String kredit;
    private String saldo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTanggalTrx() {
        return tanggalTrx;
    }

    public void setTanggalTrx(String tanggalTrx) {
        this.tanggalTrx = tanggalTrx;
    }

    public String getTanggalValuta() {
        return tanggalValuta;
    }

    public void setTanggalValuta(String tanggalValuta) {
        this.tanggalValuta = tanggalValuta;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public String getDebet() {
        return debet;
    }

    public void setDebet(String debet) {
        this.debet = debet;
    }

    public String getKredit() {
        return kredit;
    }

    public void setKredit(String kredit) {
        this.kredit = kredit;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
