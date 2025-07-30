package com.latihan.HtmlPdf.entity;

public class Detail {

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

    private String transDesc;
    private String transactionDate;
    private String valueDate;
    private String debit;
    private String credit;
    private String balance;
    private String reportingPeriod;

    public String getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(String reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Detail{" +
                "transDesc='" + transDesc + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", valueDate='" + valueDate + '\'' +
                ", debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
