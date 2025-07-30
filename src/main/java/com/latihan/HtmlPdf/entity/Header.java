package com.latihan.HtmlPdf.entity;


import java.util.ArrayList;
import java.util.List;

public class Header {

    private String productDesc;
    private String shortName;
    private String acctNumber;
    private String currency;

    private String acctOpenDate;
    private String availBalance;
    private String avgBalance;

    private String accruedIntAmt;

    private String branchOwnerName;

    private String cifNumber;

    private List<Detail> detailList = new ArrayList<Detail>();


    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAcctOpenDate() {
        return acctOpenDate;
    }

    public void setAcctOpenDate(String acctOpenDate) {
        this.acctOpenDate = acctOpenDate;
    }

    public String getAvailBalance() {
        return availBalance;
    }

    public void setAvailBalance(String availBalance) {
        this.availBalance = availBalance;
    }

    public String getAvgBalance() {
        return avgBalance;
    }

    public void setAvgBalance(String avgBalance) {
        this.avgBalance = avgBalance;
    }

    public String getAccruedIntAmt() {
        return accruedIntAmt;
    }

    public void setAccruedIntAmt(String accruedIntAmt) {
        this.accruedIntAmt = accruedIntAmt;
    }

    public String getBranchOwnerName() {
        return branchOwnerName;
    }

    public void setBranchOwnerName(String branchOwnerName) {
        this.branchOwnerName = branchOwnerName;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    @Override
    public String toString() {
        return "Header{" +
                "productDesc='" + productDesc + '\'' +
                ", shortName='" + shortName + '\'' +
                ", acctNumber='" + acctNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", acctOpenDate='" + acctOpenDate + '\'' +
                ", availBalance='" + availBalance + '\'' +
                ", avgBalance='" + avgBalance + '\'' +
                ", accruedIntAmt='" + accruedIntAmt + '\'' +
                ", branchOwnerName='" + branchOwnerName + '\'' +
                ", cifNumber='" + cifNumber + '\'' +
                '}';
    }
}
