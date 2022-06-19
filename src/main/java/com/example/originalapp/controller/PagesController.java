package com.example.originalapp.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;





@Controller
public class PagesController {

    @RequestMapping("/")
    public String index() {  
    	 return "pages/index";
    }
    
    @RequestMapping(path = "/analysistool")
    public String tool() {  
    	 return "pages/analysistool";
    }

	
    @RequestMapping(path = "/selenium")
    public String selenium() {

    	String  driver_path = "/app/.chromedriver/bin/chromedriver";
    	//String  driver_path = "./exe/chromedriver.exe";
    	
    	String userId = "1318221";
    	String password = "hamuichi24";
    	
    	String emailChart = "hamuichiro8616@gmail.com";
    	String passwordChart = "Kk248616";
    	
    	String newTime = null;
    	String settlementTime = null;
    	String currencyPair = null;
    	String[] newTimeList = new String[2];
    	String[] settlementTimeList = new String[2];
    	 ArrayList<ArrayList<String>> tradeAllList = new ArrayList<ArrayList<String>>();

    	
    
    	
        ChromeOptions options = new ChromeOptions();
        
        //ヘッドレスモード
        //options.addArguments("--headless");
        //String path = "D:\\tmp\\driver\\chromedriver_win32\\chromedriver.exe";
        
        // ユーザーエージェントの変更
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        
        //options.addArguments("-headless");
        options.addArguments("-disable-gpu");
        options.addArguments("-no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server=\"direct://\"");
        options.addArguments("--proxy-bypass-list=*");
        //options.addArguments("--start-maximized");
        options.addArguments("-window-size=1920,1080");
        
        
        System.setProperty("webdriver.chrome.driver", driver_path);
        ChromeDriver  driver = new ChromeDriver(options);
        
        //WebDriverManager.chromedriver().setup();
        
        driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS); //要素が見つかるまでの待ち時間を設定
        
       
        
        driver.get("https://lionfx.hirose-fx.co.jp/web2/lionfx/#/login"); //証券会社の表示   
        
        driver.findElement(By.xpath("/html/body/p7-app/p20-login/div/div/div/form/div[1]/input[1]")).sendKeys(userId); //ユーザーIDの入力
        driver.findElement(By.xpath("/html/body/p7-app/p20-login/div/div/div/form/div[2]/input[1]")).sendKeys(password); //パスワードの入力
        driver.findElement(By.xpath("/html/body/p7-app/p20-login/div/div/div/form/button")).click(); //ログインボタンのクリック
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[1]/div/div/ul/li[4]")).click(); //全画面表示
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[8]/a/gl-switchery")).click(); //約定一覧の選択
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[2]/a/gl-switchery")).click(); //レート一覧の非表示
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[3]/a/gl-switchery")).click(); //証拠金状況紹介の非表示
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[5]/a/gl-switchery")).click(); //ポジション一覧の非表示
        
        
        WebElement element = driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[6]/a/gl-switchery"));
        element.click(); //注文一覧の非表示
        System.out.println(driver.findElement(By.cssSelector("#dealing-history-id > div.action > div:nth-child(2) > button.btn.btn-xs.btn-primary.btn-in-home.main-button")).getText());
        //driver.findElement(By.cssSelector("#dealing-history-id > div.action > div:nth-child(2) > button.btn.btn-xs.btn-default.zoombutton.not-in-home")).click();
        //System.out.println(driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/p7-topbar/nav[2]/div/div/ul/li[6]/a/gl-switchery")).getLocation());
        
        
       /*Actions action = new Actions(driver);

        action.moveByOffset(element.getLocation().getX()-675,element.getLocation().getY()+65)
              .click()
              .perform();
        //new Actions(driver).moveByOffset(-631, 220).click().build().perform();
        //driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div/div[2]/div/div/p20-dealing-list/div/div[1]/div[2]")).click(); //+ボタンクリック
        /*driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div/div[2]/div/div/p20-dealing-list/div/div[1]/div[1]/div[2]/div[1]/div[2]")).click(); //期間指定クリック
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div/div[2]/div/div/p20-dealing-list/div/div[1]/div[1]/div[2]/div[2]/div[1]/date-picker/input")).click(); //開始日付

        //月初めの選択
        /*boolean breakFlag = false;
        for(int i = 1; i <= 6; i++) {
        	for(int j = 1; j <= 7; j++ ) {
        		String date = driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+"]/td["+j+"]")).getText();
        		if( Integer.valueOf(date) == 1) {
        			driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+"]/td["+j+"]")).click();
        			breakFlag = true;
        			break;
        		}

        	}
        	if (breakFlag) {
        		break;
        	}
        }
        
        driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div[7]/div[2]/div/div/p20-dealing-list/div/div[1]/div[1]/div[2]/div[2]/button")).click();  //検索ボタンのクリック
        

        
        
       

        /*int count = driver.findElements(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div/div[2]/div/div/p20-dealing-list/div/p20-list/div/ag-grid-ng2/div/div/div/div[1]/div/div[4]/div[3]/div/div/div")).size();
        for(int i = 1; i <= 14; i++) {
     	   String tradeHistory = driver.findElement(By.xpath("/html/body/p7-app/p7-home/div/div/div/div/div/div/div/div/div[2]/div/div/p20-dealing-list/div/p20-list/div/ag-grid-ng2/div/div/div/div[1]/div/div[4]/div[3]/div/div/div["+i+"]")).getText();

         	ArrayList<String> tradeList = new ArrayList<String>(Arrays.asList(tradeHistory.split("\n")));
         	
        	
        	if(tradeList.size() == 15) { //通貨ペア、約定日時を取り出し、成形、リスト化

        		currencyPair = tradeList.get(4);
        		currencyPair = currencyPair.replace("/", "");
        		tradeList.set(4, currencyPair);
        		
        		settlementTime = tradeList.get(0);
        		settlementTimeList = settlementTime.split(" ");
        		settlementTimeList[0] = settlementTimeList[0].replace("/", "-");
        		settlementTimeList[1] = settlementTimeList[1].substring(0, 5);
        		tradeList.set(0, settlementTimeList[0]);
        		tradeList.add(1, settlementTimeList[1]);
        		
        		newTime = tradeList.get(9);
        		newTimeList = newTime.split(" ");
        		newTimeList[0] = newTimeList[0].replace("/", "-");
        		newTimeList[1] = newTimeList[1].substring(0, 5);
        		tradeList.set(9, newTimeList[0]);
        		tradeList.add(10, newTimeList[1]);
        		
        		tradeList.remove(2);
        		tradeList.remove(3);
        		
        		tradeAllList.add(tradeList);
        		System.out.println(tradeAllList);
        	}
        }
    	
    	
    	
        /*driver.get("https://jp.tradingview.com/"); //tradigView表示
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div[3]/button[1]")).click(); //アイコンクリック
	    driver.findElement(By.xpath("/html/body/div[6]/div/span/div[1]/div/div/div[1]/div[2]/div")).click(); //ログインボタンクリック
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/div[1]/div[4]/div/span/span")).click(); //Eメールアイコンクリック
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[1]/div[1]/input")).sendKeys(emailChart); //メールアドレス入力
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[2]/div[1]/input")).sendKeys(passwordChart); //パスワード入力
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[5]/div[2]/button/span[2]")).click(); //ログインボタンクリック
	    driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div[2]/nav/ul/li[1]/a")).click(); //メニューアイコンクリック
	    
	    //チャート画像取得
	    for(int i = 0; i < tradeAllList.size(); i++) {
	    	System.out.println(tradeAllList.get(i));
		    //通貨ペア変更
	    	driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[1]/div[1]/div/div/div/div/div[2]/div[1]")).click();
	    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div[1]/input")).sendKeys(tradeAllList.get(i).get(3));
	    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[4]/div/div/div[2]/div[2]")).click();
	    	
		    driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/span")).click(); //移動ボタンクリック
		    
		    
		    //新規約定時チャート画像取得
		    for(int j=0; j< 10; j++) { //日付の入力
		    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));  
		    }
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(tradeAllList.get(i).get(7));
		   
		    for(int j=0; j< 5; j++) { //時間の入力
		    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));
		    }
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(tradeAllList.get(i).get(8));
		   
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[4]/div/span/button")).click(); //移動ボタンのクリック
		    
		    File screenshot = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[1]/div")).getScreenshotAs(OutputType.FILE);
		    System.out.println(screenshot);
		    /*FileUtils.copyFile(screenshot, new File(“path-to-images/elementshot.png”));
		    
		    /*Actions actionProvider = new Actions(driver);
		    Action keydownNew = actionProvider.keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys("s").build();
		    keydownNew.perform();
		    
		    
		    driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/span")).click(); //移動ボタンクリック
		    
		    //決済時チャート画像取得
		    /*for(int j=0; j< 10; j++) { //日付の入力
		    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));  
		    }
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(tradeAllList.get(i).get(0));
		    for(int j=0; j< 5; j++) { //時間の入力
		    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));
		    }
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(tradeAllList.get(i).get(1));
		   
		    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[4]/div/span/button")).click(); //移動ボタンのクリック
		    
		    Action keydownEnd = actionProvider.keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys("s").build();
		    keydownEnd.perform();
		    
		    break;
	    }*/


        return "pages/analysistool";
    }
}