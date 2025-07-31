package com.latihan.HtmlPdf.repository;

import com.latihan.HtmlPdf.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiRepo extends JpaRepository<Transaksi,Long> {

    @Query(value = "select distinct A.PRODUCT_DESC ,A.SHORT_NAME ,A.ACCT_NUMBER ,'IDR' as CURRENCY , DATE_FORMAT(A.ACCT_OPEN_DATE,'%d-%m-%Y') ACCT_OPEN_DATE , FORMAT(A.AVAIL_BAL,0,'id_ID') AVAIL_BAL ,FORMAT(A.AGGR_CURR_BAL,0,'id_ID') AVG_BALANCE, FORMAT(coalesce(A.ACCRUED_INT_AMT,0),0,'id_ID')  ACCRUED_INT_AMT, \n" +
            "            A.BRANCH_OWNER_NAME, A.CIF_NUMBER , A.BATCH_DATE \n" +
            "             from casa_stmt_header  a\n" +
            "            limit 1 ",nativeQuery = true)
    List<Object[]> getDataHeader (@Param("actNumber") String actNumber);


    @Query(value = "select DATE_FORMAT(DETAIL.eff_date,'%d-%m-%Y') eff_date, DATE_FORMAT( DETAIL.posting_date,'%d-%m-%Y') posting_date,DETAIL.TRANS_DESC,FORMAT(DETAIL.DB_TRANS_AMT,0,'id_ID') DB_TRANS_AMT,FORMAT(DETAIL.CRED_TRANS_AMT,0,'id_ID') CRED_TRANS_AMT, FORMAT(DETAIL.CURR_BAL,0,'id_ID') CURR_BAL\n" +
            "from casa_stmt_detail DETAIL\n" +
            "LIMIT :limit ",nativeQuery = true)
    List<Object[]> getDataDetails (@Param("limit") int limit);
}
