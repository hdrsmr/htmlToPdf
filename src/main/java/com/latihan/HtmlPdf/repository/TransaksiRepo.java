package com.latihan.HtmlPdf.repository;

import com.latihan.HtmlPdf.model.Transaksi;
import com.latihan.HtmlPdf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiRepo extends JpaRepository<Transaksi,Long> {
}
