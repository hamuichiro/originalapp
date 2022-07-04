package com.example.originalapp.repository;


	import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	import com.example.originalapp.entity.TransactionData;

	@Repository
	public interface TransactionDataRepository extends JpaRepository<TransactionData, Long> {

	    Iterable<TransactionData> findAllByOrderByTransactionSettlementDate();
	    //全件取得のソート findAllByOrderBy○○();○○にはソートのキーとなるフィールド名
	  
	}