package com.lti.droplets.transactions.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.droplets.transactions.model.TransactionsModel;


@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsModel, Long>{

    public Optional<TransactionsModel> findByTransactionId(Long transactionId);
    public Optional<TransactionsModel> findByTrxRefNumber(String trxRefNumber);
    public List<TransactionsModel> findByCustomerIdAndBeneficiaryId(Long customerId,String beneficiaryId);
    public List<TransactionsModel> findByCustomerId(Long customerId);
//    @Query("SELECT model from Model model where\n" +
//            "(transaction_date BETWEEN :fromDate AND :toDate) ")
    @Query("select t from TransactionsModel t where \n"+
            "(transactionDate BETWEEN :fromDate AND :toDate) AND customerId= :customerId ")
    public List<TransactionsModel> findByCustomerIdAndDate(@Param("customerId") Long customerId,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
    @Query("select t from TransactionsModel t where \n"+
            "(transactionDate BETWEEN :fromDate and :toDate) AND beneficiaryId = :beneficiaryId AND customerId = :customerId ")
    public List<TransactionsModel> findByCustomerIdAndDateAndBeneficiaryId(@Param("customerId") Long customerId,@Param("beneficiaryId") String beneficiaryId,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);
}
