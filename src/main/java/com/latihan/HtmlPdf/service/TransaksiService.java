package com.latihan.HtmlPdf.service;

import com.latihan.HtmlPdf.model.Transaksi;
import com.latihan.HtmlPdf.repository.TransaksiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepo transaksiRepo;

    public List<Transaksi> getAllTransaksi() {
        return transaksiRepo.findAll();
    }



}
