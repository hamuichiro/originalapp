package com.example.originalapp.entity;



	import java.io.Serializable;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	import javax.persistence.SequenceGenerator;
	import javax.persistence.Table;

	import lombok.Data;
	
	//起動時にエンティティクラスTransactionDataからTransactionDataテーブルが自動生成される

	@Entity //エンティティクラスである事を示す
	@Table(name = "transactionData")
	@Data //Lombokのアノテーション
	public class TransactionData implements Serializable {
	    private static final long serialVersionUID = 1L;

	    @Id //エンティティの主キーを示す
	    @SequenceGenerator(name = "transactionData_id_seq")
	    @GeneratedValue(strategy = GenerationType.IDENTITY) //主キーが自動採番される事、strategy=GenerationType.IDENTITYは採番方法を示す
	    private Long id;

	   // @Column(nullable = false) //フィールドに対する設定や制約
	    //private Long userId;

	    //@Column(nullable = false)
	    //private String path;

	    @Column(nullable = false)
	    private String transactionNewDate;
	    
	    @Column(nullable = false)
	    private String transactionNewTime;
	    
	    @Column(nullable = false)
	    private String rateNew;
	    
	    @Column(nullable = false)
	    private String transactionSettlementDate;
	    
	    @Column(nullable = false)
	    private String transactionSettlementTime;
	    
	    @Column(nullable = false)
	    private String rateSettlement;

	    @Column(nullable = false)
	    private String currencyPair;
	    
	    @Column(nullable = false)
	    private String transactionType;

	    @Column(nullable = false)
	    private String transactionLot;	    

	    @Column(nullable = false)
	    private String profitLoss;
	    
	    @Column(nullable = false)
	    private String swap;
	    
	    @Column(nullable = false)
	    private String profitLossConfirm;
	    
	    @Column(nullable = false)
	    private Double rateDifference;
	    
	    @Column(nullable = false)
	    private Double profitlossParseint;


	    @ManyToOne
	    @JoinColumn(name = "userId", insertable = false, updatable = false)
	    private Account account;
	}