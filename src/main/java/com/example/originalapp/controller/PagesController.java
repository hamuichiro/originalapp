package com.example.originalapp.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

	
    @RequestMapping("/selenium")
    public String selenium() {

    	//String  driver_path = "/app/.chromedriver/bin/chromedriver";
    	String  driver_path = "./exe/chromedriver.exe";
    	
    	String userId = "1318221";
    	String password = "hamuichi24";
    	
    	String emailChart = "hamuichiro8616@gmail.com";
    	String passwordChart = "Kk248616";
    	
    	String newTime = null;
    	String settlementTime = null;
    	String[] newTimeList = new String[2];
    	String[] settlementTimeList = new String[2];

    	
    
    	
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
        driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(5,TimeUnit.SECONDS);
        
        driver.get("https://lionfx.hirose-fx.co.jp/web2/lionfx/#/login"); //証券会社の表示   
        
        driver.findElement(By.id("inputName")).sendKeys(userId); //ユーザーIDの入力
        driver.findElement(By.id("inputPass")).sendKeys(password); //パスワードの入力
        driver.findElement(By.xpath("//*[@id=\"lionFxLogin\"]/button")).click(); //ログインボタンのクリック
        driver.findElement(By.xpath("//*[@id=\"site-navbar-collapse\"]/ul/li[8]/a/gl-switchery/span/small")).click(); //約定一覧の選択
        driver.findElement(By.xpath("//*[@id=\"dealing-history-id\"]/div[1]/div[2]/button[1]")).click(); //+ボタンクリック
        driver.findElement(By.id("inputBasicDateTimeDealing")).click(); //期間指定クリック
        driver.findElement(By.id("DealingFromDatePickerCompId")).click(); //開始日付
        
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
        
        driver.findElement(By.xpath("//*[@id=\"dealing-history-id\"]/div[1]/div[1]/div[2]/div[2]/button")).click();  //検索ボタンのクリック
        
        driver.findElement(By.xpath("//*[@id=\"site-navbar-collapse\"]/ul/li[2]/a/gl-switchery/span/small")).click(); //レート一覧の非表示
        driver.findElement(By.xpath("//*[@id=\"site-navbar-collapse\"]/ul/li[3]/a/gl-switchery/span/small")).click(); //証拠金状況紹介の非表示
        driver.findElement(By.xpath("//*[@id=\"site-navbar-collapse\"]/ul/li[5]/a/gl-switchery/span/small")).click(); //ポジション一覧の非表示
        driver.findElement(By.xpath("//*[@id=\"site-navbar-collapse\"]/ul/li[6]/a/gl-switchery/span/small")).click(); //注文一覧の非表示
        
        List<WebElement> tradeHistoryAlllist = driver.findElements(By.xpath("//*[@id=\"center\"]/div/div[4]/div[3]/div/div/div")); //全約定履歴の取得
        System.out.println(tradeHistoryAlllist.size());
        for(WebElement tradeHistoryList : tradeHistoryAlllist) { //個別の履歴の内容をリストに格納
        	String tradeHistory = tradeHistoryList.getText();
        	List<String> tradeList = Arrays.asList(tradeHistory.split("\n"));
        	System.out.println(tradeList);
        	
        	if(tradeList.size() == 13 || tradeList.size() == 15) { //約定日時を取り出し、成形
        		newTime = tradeList.get(8);
        		newTimeList = newTime.split(" ");
        		settlementTime = tradeList.get(0);
        		settlementTimeList = settlementTime.split(" ");
        		newTimeList[0] = newTimeList[0].replace("/", "-");
        		newTimeList[1] = newTimeList[1].substring(0, 5);
        		settlementTimeList[0] = settlementTimeList[0].replace("/", "-");
        		settlementTimeList[1] = settlementTimeList[1].substring(0, 5);
        	}
        		

    		System.out.println(newTimeList[0]);
    		System.out.println(newTimeList[1]);

        }
        
        driver.get("https://jp.tradingview.com/"); //tradigView表示
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div[3]/button[1]")).click(); //アイコンクリック
	    driver.findElement(By.className("label-4TFSfyGO")).click(); //ログインボタンクリック
	    driver.findElement(By.className("tv-signin-dialog__toggle-email")).click(); //Eメールアイコンクリック
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[1]/div[1]/input")).sendKeys(emailChart); //メールアドレス入力
	    driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[2]/div[1]/input")).sendKeys(passwordChart); //パスワード入力
	    driver.findElement(By.className("tv-button__loader")).click(); //ログインボタンクリック
	    driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div[2]/nav/ul/li[1]/a")).click(); //メニューアイコンクリック
	    driver.findElement(By.className("icon-wNyKS1Qc")).click(); //移動ボタンクリック
	   
	    /*for(int i=0; i< 10; i++) { //日付の入力
	    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));
		   
	    }
	    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input")).sendKeys(newTimeList[0]);
	   
	    for(int i=0; i< 5; i++) { //時間の入力
	    	driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(Keys.chord(Keys.BACK_SPACE));
	    }
	    driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input")).sendKeys(newTimeList[1]);
	   
	    driver.findElement(By.className("content-YKkCvwjV")).click(); //移動ボタンのクリック
	    
	    Actions actionProvider = new Actions(driver);
	    Action keydown = actionProvider.keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys("s").build();
	    keydown.perform();*/


        return "pages/analysistool";
    }
}