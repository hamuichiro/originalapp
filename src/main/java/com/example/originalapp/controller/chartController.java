package com.example.originalapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.originalapp.entity.TransactionData;
import com.example.originalapp.repository.TransactionDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class chartController {
	
    @Autowired 
    private TransactionDataRepository repository;
    
    //取引データ一覧
    @RequestMapping(value = "/dateList")
    @ResponseBody
    public String tableChart() throws IOException {
    	
    	List<TransactionData> transactionDatas = repository.findAll();
    	
    	return getJson(transactionDatas);
    }
    
    
    
    private String getJson(List<TransactionData> transactionDatas){
    	
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
        try{
            retVal = objectMapper.writeValueAsString(transactionDatas);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        
        return retVal;
    }
    

}
