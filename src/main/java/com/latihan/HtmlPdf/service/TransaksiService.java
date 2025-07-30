package com.latihan.HtmlPdf.service;

import com.latihan.HtmlPdf.entity.Detail;
import com.latihan.HtmlPdf.entity.Header;
import com.latihan.HtmlPdf.model.Transaksi;
import com.latihan.HtmlPdf.repository.TransaksiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepo transaksiRepo;

    public List<Transaksi> getAllTransaksi() {
        return transaksiRepo.findAll();
    }

    public Header getHeader(String actNumber) {
        List<Object[]> result = transaksiRepo.getDataHeader(actNumber);

        if(result != null && !result.isEmpty()){
            for (Object[] row : result) {
                Header header = new Header();
                header.setProductDesc(defaultNull(row[0]));
                header.setShortName(defaultNull(row[1]));
                header.setAcctNumber(defaultNull(row[2]));
                header.setCurrency(defaultNull(row[3]));
                header.setAcctOpenDate(defaultNull(row[4]));
                header.setAvailBalance(defaultNull(row[5]));
                header.setAvgBalance(defaultNull(row[6]));
                header.setAccruedIntAmt(defaultNull(row[7]));
                header.setBranchOwnerName(defaultNull(row[8]));
                header.setCifNumber(defaultNull(row[9]));


//                System.out.println("header: " + header.toString());

                return header;
            }


        }


        return null;


    }


    private String defaultNull(Object f){
        if (f == null){
            return "";
        }

        return (String) f;
    }

    public List<Detail> getDetails(int limit) {
        List<Object[]> result = transaksiRepo.getDataDetails(limit);
        List<Detail> details = new ArrayList<Detail>();

        if(result != null && !result.isEmpty()){
            for (Object[] row : result) {
                Detail dt = new Detail();
                dt.setTransactionDate(defaultNull(row[0]));
                dt.setValueDate(defaultNull(row[1]));
                dt.setTransDesc(defaultNull(row[2]));
                dt.setDebit(defaultNull(row[3]));
                dt.setCredit(defaultNull(row[4]));
                dt.setBalance(defaultNull(row[5]));
                details.add(dt);
//                System.out.println("Detailssss: " + dt.toString());
            }
        }



        return details;


    }


    public List<Detail> getDetailsJasper(int limit) {
        List<Object[]> result = transaksiRepo.getDataDetails(limit);
        List<Detail> details = new ArrayList<Detail>();

        if(result != null && !result.isEmpty()){
            for (Object[] row : result) {
                Detail dt = new Detail();
                dt.setTransactionDate(defaultNull(row[0]));
                dt.setValueDate(defaultNull(row[1]));
                dt.setTransDesc(defaultNull(row[2]));
                dt.setDebit(defaultNull(row[3]));
                dt.setCredit(defaultNull(row[4]));
                dt.setBalance(defaultNull(row[5]));
                details.add(dt);
//                System.out.println("Detailssss: " + dt.toString());
            }
        }



        return details;


    }





}
