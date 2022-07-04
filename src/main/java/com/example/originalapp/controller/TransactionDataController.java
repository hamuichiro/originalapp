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
	import com.example.originalapp.entity.AccountInf;
	import com.example.originalapp.form.TransactionDataForm;
	import com.example.originalapp.form.AccountForm;
	import com.example.originalapp.repository.TransactionDataRepository;
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



	    @RequestMapping("/analysistool")
	    public String index(Model model) throws IOException {

	    	Iterable<TransactionData> transactionDatas = repository.findAll(); //テーブルから全件取得する

	        model.addAttribute("TransactionDatas", transactionDatas); //サーバサイドからフロントエンド（クライアント側）へデータの受け渡し

	        
	        
	        return "/pages/analysistool";

	    }
	    

	}
