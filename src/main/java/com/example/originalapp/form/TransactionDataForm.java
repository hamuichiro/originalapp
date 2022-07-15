package com.example.originalapp.form;


import lombok.Data;

@Data
public class TransactionDataForm {

    private Long id;

    //private Long userId;

    //private String path;
    
    private String transactionNumber;

    private String transactionNewDate;

    private String transactionNewTime;

    private Long rateNew;

    private String transactionSettlementDate;

    private String transactionSettlementTime;

    private Long rateSettlement;

    private String currencyPair;

    private String transactionType;

    private String transactionLot;

    private Long rateDifference;

    private Long yenConversionRate;

    private Long profitLoss;

    private Long swap;

    private AccountForm user;
    
    private String screenshotFilePathNew;
    
    private String screenshotFilePath;

}