package com.latihan.HtmlPdf.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transaksi")
@Data
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
}
