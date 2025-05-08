package com.latihan.HtmlPdf.service;

import com.latihan.HtmlPdf.model.Transaksi;
import com.latihan.HtmlPdf.model.User;
import com.latihan.HtmlPdf.repository.TransaksiRepo;
import com.latihan.HtmlPdf.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepo transaksiRepo;

    public Transaksi createTransaksi(Transaksi transaksi) {
        return transaksiRepo.save(transaksi);
    }

    public List<Transaksi> getAllTransaksi() {
        return transaksiRepo.findAll();
    }

    public Transaksi getTransID(Long id) {
        return transaksiRepo.findById(id).orElse(null);
    }

    public Transaksi updateUser(Long id, Transaksi transaksi) {
        transaksi.setId(id);
        return transaksiRepo.save(transaksi);
    }

    public void deleteTransaksi(Long id) {
        transaksiRepo.deleteById(id);
    }
}
