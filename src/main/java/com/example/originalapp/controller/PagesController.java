package com.example.originalapp.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
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

    public void elememtClickXpath(ChromeDriver  driver, String path) {
    	
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(500));
        WebElement element = driver.findElement(By.xpath(path)); 
       
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    public void elememtClickSelector(ChromeDriver  driver, String selecter) {
    	
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(500));
        WebElement element = driver.findElement(By.cssSelector(selecter)); 
       
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    
    public void elementSendkeys(ChromeDriver  driver, String selecter, String keys) {
    	
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(4))
                .pollingEvery(Duration.ofMillis(500));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selecter)));
        WebElement element = driver.findElement(By.cssSelector(selecter)); 
        element.sendKeys(keys); 
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
        //this.elementSendkeys(driver, "#inputName", userId); //ユーザーIDの入力
        //this.elementSendkeys(driver, "#inputPass", password); //パスワードの入力
        this.elememtClickSelector(driver, "#lionFxLogin > button"); //ログインボタンのクリック
        
        this.elememtClickSelector(driver, "#toggleFullscreen"); //全画面表示
        this.elememtClickSelector(driver, "#site-navbar-collapse > ul > li:nth-child(2) > a > gl-switchery > span"); //レート一覧の非表示
        this.elememtClickSelector(driver, "#site-navbar-collapse > ul > li:nth-child(3) > a > gl-switchery > span"); //証拠金状況紹介の非表示
        this.elememtClickSelector(driver, "#site-navbar-collapse > ul > li:nth-child(5) > a > gl-switchery > span"); //ポジション一覧の非表示
        this.elememtClickSelector(driver, "#site-navbar-collapse > ul > li:nth-child(6) > a > gl-switchery > span"); //注文一覧の非表示
        this.elememtClickSelector(driver, "#site-navbar-collapse > ul > li:nth-child(8) > a > gl-switchery > span > small"); //約定一覧の選択
        
        this.elememtClickSelector(driver, "#dealing-history-id > div.action > div:nth-child(2) > button.btn.btn-xs.btn-default.zoombutton.not-in-home"); //+ボタンクリック
        this.elememtClickSelector(driver, "#dealing-history-id > div.action > div:nth-child(1) > div:nth-child(2) > div.time-area > div:nth-child(2) > label"); //期間指定クリック
        this.elememtClickSelector(driver, "#DealingFromDatePickerCompId"); //開始日付
        
        //月初めの選択
        boolean breakFlag = false;
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
        
        this.elememtClickSelector(driver, "#dealing-history-id > div.action > div:nth-child(1) > div:nth-child(2) > div.filter-box.display-small-screen > button");  //検索ボタンのクリック
        
        List<WebElement> tradeHistoryAlllist = driver.findElements(By.xpath("//*[@id=\"center\"]/div/div[4]/div[3]/div/div/div")); //全約定履歴の取得
        
        for(WebElement tradeHistoryList : tradeHistoryAlllist) { //個別の履歴の内容をリストに格納
        	String tradeHistory = tradeHistoryList.getText();
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

        
        //driver.get("https://jp.tradingview.com/"); //tradigView表示
        driver.quit();
        /*this.elememtClickSelector(driver, "body > div.tv-main > div.tv-header.tv-header__top.js-site-header-container.tv-header--sticky.tv-header--promo.tv-header--animated > div.tv-header__inner > div.tv-header__area.tv-header__area--user > button.tv-header__user-menu-button.tv-header__user-menu-button--anonymous.js-header-user-menu-button > svg"); //アイコンクリック
        this.elememtClickSelector(driver, "#overlap-manager-root > div > span > div.menu-xamafYNf.menuWrap-8MKeZifP > div > div > div.item-4TFSfyGO.item-ykcJIrqq.item-50IqnBef.withIcon-4TFSfyGO.withIcon-ykcJIrqq > div.labelRow-4TFSfyGO.labelRow-ykcJIrqq > div");  //ログインボタンクリック
        this.elememtClickSelector(driver, "#overlap-manager-root > div > div.tv-dialog__modal-wrap.tv-dialog__modal-wrap--contain-size > div > div > div > div > div > div > div:nth-child(1) > div.i-clearfix > div > span > span"); //Eメールアイコンクリック
        //this.elementSendkeys(driver, "#email-signin__user-name-input__ca74da8f-0b36-4a3f-8c47-51735232ca76", emailChart); //メールアドレス入力
        //this.elementSendkeys(driver, "#email-signin__password-input__ca74da8f-0b36-4a3f-8c47-51735232ca76", passwordChart); //パスワード入力
      
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[1]/div[1]/input")).sendKeys(emailChart); //メールアドレス入力
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[2]/div[1]/input")).sendKeys(passwordChart); //パスワード入力
	    this.elememtClickXpath(driver, "/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[5]/div[2]/button"); //ログインボタンクリック
	    this.elememtClickXpath(driver, "/html/body/div[3]/div[3]/div[2]/div[2]/nav/ul/li[1]/a");
	    driver.quit();
        
	    
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