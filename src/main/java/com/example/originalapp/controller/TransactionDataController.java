package com.example.originalapp.controller;


	import java.io.ByteArrayOutputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStream;
	import java.security.Principal;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

	import org.apache.commons.io.FilenameUtils;
	import org.modelmapper.ModelMapper;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.security.core.Authentication;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.util.Base64Utils;
	import org.springframework.validation.BindingResult;
	import org.springframework.validation.annotation.Validated;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;

	import com.example.originalapp.entity.TransactionData;
import com.amazonaws.services.s3.model.S3Object;
import com.example.originalapp.entity.AccountInf;
	import com.example.originalapp.form.TransactionDataForm;
	import com.example.originalapp.form.AccountForm;
	import com.example.originalapp.repository.TransactionDataRepository;
import com.example.originalapp.service.S3Wrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

	@Controller
	public class TransactionDataController {

	    protected static Logger log = LoggerFactory.getLogger(TransactionDataController.class);

	    @Autowired
	    private ModelMapper modelMapper;

	    @Autowired //TransactionDataRepositoryを@Autowiredを付けて定義してController内で使用できるようにしている
	    private TransactionDataRepository repository;

	    @Autowired
	    private HttpServletRequest request;
	    
	    @Autowired
	    S3Wrapper s3;



	    @RequestMapping("/analysistool")
	    public String index(Model model) throws IOException {

	    	Iterable<TransactionData> transactionDatas = repository.findAll(); //テーブルから全件取得する

	        model.addAttribute("TransactionDatas", transactionDatas); //サーバサイドからフロントエンド（クライアント側）へデータの受け渡し

	        
	        
	        return "pages/analysistool";

	    }
	    
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
	    
	    @RequestMapping(value = "/screenShot")
	    @ResponseBody
	    public String screenShot(String filePath) throws Exception {
	    TransactionData transactionData = repository.findByTransactionNumber(filePath);

	    	return getJsonFile(s3.download(transactionData.getScreenshotFilePathNew()));
	    }
	    
	    private String getJsonFile(S3Object screenShot){
	    	
	        String retVal = null;
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        try{
	            retVal = objectMapper.writeValueAsString(screenShot);
	        } catch (JsonProcessingException e) {
	            System.err.println(e);
	        }
	        
	        return retVal;
	    }

	}
