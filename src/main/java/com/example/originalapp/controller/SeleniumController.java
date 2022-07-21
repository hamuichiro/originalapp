package com.example.originalapp.controller;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.example.originalapp.entity.TransactionData;
import com.example.originalapp.repository.TransactionDataRepository;
import com.example.originalapp.service.S3Wrapper;

@Controller
public class SeleniumController {

	@Autowired
	private TransactionDataRepository repository;

	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	S3Wrapper s3;

	public void elememtClickXpath(ChromeDriver driver, String path) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		WebElement element = driver.findElement(By.xpath(path));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();

		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void elememtClickId(ChromeDriver driver, String selecter) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		WebElement element = driver.findElement(By.id(selecter));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();

		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void elememtClickSelector(ChromeDriver driver, String selecter) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		WebElement element = driver.findElement(By.cssSelector(selecter));
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();

		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void elementSendkeys(ChromeDriver driver, String id, String keys) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		WebElement element = driver.findElement(By.id(id));
		element.sendKeys(keys);
	}

	public void elementSendkeysXpath(ChromeDriver driver, String path, String keys) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(4))
				.pollingEvery(Duration.ofMillis(500));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		WebElement element = driver.findElement(By.xpath(path));
		element.sendKeys(keys);
	}

	public ChromeDriver driver() {
		String driver_path = "/app/.chromedriver/bin/chromedriver";
		//String driver_path = "./exe/chromedriver.exe";

		ChromeOptions options = new ChromeOptions();

		// ユーザーエージェントの変更
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

		// options.addArguments("-headless");
		options.addArguments("-disable-gpu");
		options.addArguments("-no-sandbox");
		options.addArguments("--disable-extensions");
		options.addArguments("--proxy-server=\"direct://\"");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("-window-size=2220,1080");

		System.setProperty("webdriver.chrome.driver", driver_path);

		ChromeDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); // 要素が見つかるまでの待ち時間を設定

		return driver;

	}

	@RequestMapping(path = "/selenium")
	public String transaction(ModelMap modelMap, @RequestParam("year") int year, @RequestParam("month") int month)
			throws Exception {
		System.out.println(
				"##############################################################################################");

		String userId = "1318221";
		String password = "hamuichi24";

		String newTime = null;
		String settlementTime = null;
		String currencyPair = null;
		String[] newTimeList = new String[2];
		String[] settlementTimeList = new String[2];

		ChromeDriver driver = this.driver();

		driver.get("https://lionfx.hirose-fx.co.jp/WTChartWeb/"); // 証券会社の表示

		this.elementSendkeys(driver, "login-id", userId); // ユーザーIDの入力
		this.elementSendkeys(driver, "login-pw", password); // パスワードの入力
		this.elememtClickId(driver, "doLogin"); // ログインボタンのクリック
		this.elememtClickXpath(driver, "/html/body/div[16]/div[2]/button[1]");

		this.elememtClickId(driver, "execution-history"); // 約定一覧の選択
		this.elememtClickId(driver, "execution-history-start-yymmdd"); // 開始日付

		// 現在の年月取得
		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("M");
		int formatNowDate = Integer.parseInt(dtf1.format(nowDate));
		int formatNowDate2 = Integer.parseInt(dtf2.format(nowDate));
		int countmonth = formatNowDate2 - month;
		;
		int countYear = formatNowDate - year;

		if (countYear == 0) {
			if (countmonth == 0) {
				driver.findElement(By.linkText("1")).click();
			} else if (countmonth > 0) {
				for (int i = 1; i <= countmonth; i++) {
					this.elememtClickXpath(driver, "//*[@id=\"ui-datepicker-div\"]/div/a[1]/span");
				}
				driver.findElement(By.linkText("1")).click();
			}
		}
		// else if(countYear > 0) {

		// }

		this.elememtClickId(driver, "execution-history-end-yymmdd");
		if (countYear == 0) {
			if (countmonth == 0) {

			} else if (countmonth == 1) {
				driver.findElement(By.linkText("1")).click();
			} else if (countmonth > 0) {
				for (int i = 1; i <= countmonth - 1; i++) {
					this.elememtClickXpath(driver, "//*[@id=\"ui-datepicker-div\"]/div/a[1]/span");
				}
				driver.findElement(By.linkText("1")).click();
			}
		}

		this.elememtClickXpath(driver, "//*[@id=\"execution-history-detail-simple\"]/label/span[2]"); // 詳細表示
		this.elememtClickId(driver, "execution-history-update"); // 更新ボタン
		this.elememtClickId(driver, "page-liftup");
		this.elememtClickId(driver, "time-line");

		
		//List<WebElement> tradeHistoryAlllist = new ArrayList<WebElement>();
		/*for (int i = 1; driver.findElement(By
				.xpath("/html/body/div[1]/div[1]/div/div[5]/div[3]/div[1]/div[1]/div/div[5]/div/div[3]/div[2]/div["
						+ i + "]"))
				.isDisplayed() ; i++) {
			tradeHistoryAlllist.add(driver.findElement(By.xpath(
						"/html/body/div[1]/div[1]/div/div[5]/div[3]/div[1]/div[1]/div/div[5]/div/div[3]/div[2]/div[" + i
								+ "]")));
		}*/
		List<WebElement> tradeHistoryAlllist = driver.findElements(By.className("list-body-row")); // 全約定履歴の取得
		System.out.println("#######################");
		System.out.println(tradeHistoryAlllist.size());
		for (int i = 1; i <= tradeHistoryAlllist.size(); i++) {
			WebElement tradeHistoryList;
			try {
				tradeHistoryList = driver.findElement(By.xpath(
						"/html/body/div[1]/div[1]/div/div[5]/div[3]/div[1]/div[1]/div/div[5]/div/div[3]/div[2]/div[" + i
								+ "]"));

			} catch (NoSuchElementException e) {
				break;
			}
			finally{
				driver.quit();
			}
			if (driver.findElements(By
					.xpath("/html/body/div[1]/div[1]/div/div[5]/div[3]/div[1]/div[1]/div/div[5]/div/div[3]/div[2]/div["
							+ i + "]"))
					.size() == 0) {
				driver.quit();
				return "redirect:/analysistool";
			}

		/*System.out.println(tradeHistoryAlllist);
			  for(WebElement tradeHistoryList : tradeHistoryAlllist) { //個別の履歴の内容をリストに格納
				  if (tradeHistoryList.isDisplayed() == false) {
					  new WebDriverWait(driver, Duration.ofSeconds(1000)).until(ExpectedConditions.textToBePresentInElementLocated(
							     By.xpath("/html/body/div[1]/div[1]/div/div[5]/div[3]/div[1]/div[1]/div/div[5]/div/div[3]/div[2]/div[10]"), "なし"));
 
						  }*/

				  System.out.println(tradeHistoryList);
			  String tradeHistory = tradeHistoryList.getText();
			  System.out.println(tradeHistory);
			ArrayList<String> tradeList = new ArrayList<String>(Arrays.asList(tradeHistory.split("\n")));

			if (tradeList.size() == 21 || tradeList.size() == 20) { // 通貨ペア、約定日時を取り出し、成形、リスト化
				if (tradeList.size() == 21) {
					tradeList.remove(2);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(9);
					tradeList.remove(10);
					tradeList.remove(12);

				} else {
					tradeList.remove(2);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(3);
					tradeList.remove(9);
					tradeList.remove(10);
					tradeList.remove(12);

				}

				double rateDifference = Double.parseDouble(tradeList.get(8).replace(",", "")) / 10;
				double profitlossParseint = 0;
				double newRate = Double.parseDouble(tradeList.get(6));

				currencyPair = tradeList.get(2);
				currencyPair = currencyPair.replace("/", "");
				tradeList.set(2, currencyPair);

				if (newRate <= 2) {
					newRate = newRate * 10000;

				} else {
					newRate = newRate * 100;

				}

				if ((tradeList.get(3)).equals("売")) {

					tradeList.set(3, "Long");
				} else {

					tradeList.set(3, "Short");
				}

				profitlossParseint = rateDifference / newRate * 100;

				settlementTime = tradeList.get(0);
				settlementTimeList = settlementTime.split(" ");
				settlementTimeList[1] = settlementTimeList[1].substring(0, 5);
				tradeList.set(0, settlementTimeList[0]);
				tradeList.add(1, settlementTimeList[1]);

				newTime = tradeList.get(6);
				newTimeList = newTime.split(" ");
				newTimeList[1] = newTimeList[1].substring(0, 5);
				tradeList.set(6, newTimeList[0]);
				tradeList.add(7, newTimeList[1]);

				TransactionData transactionData = new TransactionData();
				transactionData.setTransactionSettlementDate(tradeList.get(0));
				transactionData.setTransactionSettlementTime(tradeList.get(1));
				transactionData.setTransactionNumber(tradeList.get(2));
				transactionData.setCurrencyPair(tradeList.get(3));
				transactionData.setTransactionType(tradeList.get(4));
				transactionData.setTransactionLot(tradeList.get(5));
				transactionData.setTransactionNewDate(tradeList.get(6));
				transactionData.setTransactionNewTime(tradeList.get(7));
				transactionData.setRateNew(tradeList.get(8));
				transactionData.setRateSettlement(tradeList.get(9));
				transactionData.setProfitLoss(tradeList.get(11).replace(",", ""));
				transactionData.setSwap(tradeList.get(12).replace(",", ""));
				transactionData.setProfitLossConfirm(tradeList.get(13));
				transactionData.setRateDifference(rateDifference);
				transactionData.setProfitlossParseint(Math.round(profitlossParseint * 100.0) / 100.0);

				repository.saveAndFlush(transactionData);
			}

		}

		driver.quit();
		return "redirect:/analysistool";

	}

	@RequestMapping(path = "/transactionNumber")
	public String screenshot(ModelMap modelMap, @RequestParam("transactionNumber") String transactionNumber)
			throws Exception {

		String emailChart = "hamuichiro8616@gmail.com";
		String passwordChart = "Kk248616";

		String[] newTimeList = new String[2];
		String[] settlementTimeList = new String[2];
		String currencyPair = null;

		ChromeDriver driver = this.driver();

		driver.get("https://jp.tradingview.com/"); // tradigView表示

		this.elememtClickXpath(driver, "/html/body/div[2]/div[3]/div[2]/div[3]/button[1]"); // アイコンクリック
		this.elememtClickXpath(driver, "/html/body/div[6]/div/span/div[1]/div/div/div/button[1]"); // アイコンクリック

		this.elememtClickXpath(driver,
				"//*[@id=\"overlap-manager-root\"]/div/div[2]/div/div/div/div/div/div/div[1]/div[4]"); // Eメールアイコンクリック
		this.elementSendkeysXpath(driver,
				"/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[1]/div[1]/input", emailChart); // メールアドレス入力
		this.elementSendkeysXpath(driver,
				"/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[2]/div[1]/input", passwordChart); // パスワード入力

		this.elememtClickXpath(driver,
				"/html/body/div[6]/div/div[2]/div/div/div/div/div/div/form/div[5]/div[2]/button"); // ログインボタンクリック
		this.elememtClickXpath(driver, "/html/body/div[3]/div[3]/div[2]/div[2]/nav/ul/li[1]/a");

		TransactionData transactionData = repository.findByTransactionNumber(transactionNumber);

		newTimeList[1] = transactionData.getTransactionNewDate().replace("/", "-");
		newTimeList[0] = transactionData.getTransactionNewTime();
		settlementTimeList[1] = transactionData.getTransactionSettlementDate().replace("/", "-");
		settlementTimeList[0] = transactionData.getTransactionSettlementTime();
		currencyPair = transactionData.getCurrencyPair();

		System.out.println(newTimeList[1]);
		System.out.println(newTimeList[0]);
		System.out.println(currencyPair);

		// チャート画像取得
		// 通貨ペア変更
		this.elememtClickId(driver, "header-toolbar-symbol-search");

		this.elementSendkeysXpath(driver, "//*[@id=\"overlap-manager-root\"]/div/div/div[2]/div/div[2]/div[1]/input",
				currencyPair);
		this.elememtClickXpath(driver, "/html/body/div[5]/div/div/div[2]/div/div[4]/div/div/div[2]/div[2]");

		this.elememtClickXpath(driver, "/html/body/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/span"); // 移動ボタンクリック
		Actions actionProvider = new Actions(driver);
		Action keydownNew = actionProvider.keyDown(Keys.ALT).sendKeys("g").build();
		keydownNew.perform();

		// 新規約定時チャート画像取得
		for (int j = 0; j < 10; j++) { // 日付の入力
			this.elementSendkeysXpath(driver,
					"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input",
					Keys.chord(Keys.BACK_SPACE));

		}
		this.elementSendkeysXpath(driver,
				"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input",
				newTimeList[0]);

		for (int j = 0; j < 5; j++) { // 時間の入力
			this.elementSendkeysXpath(driver,
					"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input",
					Keys.chord(Keys.BACK_SPACE));

		}
		this.elementSendkeysXpath(driver,
				"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input",
				newTimeList[1]);

		this.elememtClickXpath(driver, "//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[4]/div/span/button"); // 移動ボタンクリック

		File screenshotNew = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[1]/div"))
				.getScreenshotAs(OutputType.FILE);
		String filePathNew = screenshotNew.getPath();
		System.out.println(filePathNew);
		s3.upLoad(filePathNew);
		transactionData.setScreenshotFilePathNew(filePathNew);

		this.elememtClickXpath(driver, "/html/body/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/span"); // 移動ボタンクリック

		keydownNew.perform();

		// 決済時チャート画像取得
		for (int j = 0; j < 10; j++) { // 日付の入力
			this.elementSendkeysXpath(driver,
					"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input",
					Keys.chord(Keys.BACK_SPACE));

		}
		this.elementSendkeysXpath(driver,
				"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div/span/span[1]/input",
				settlementTimeList[0]);

		for (int j = 0; j < 5; j++) { // 時間の入力
			this.elementSendkeysXpath(driver,
					"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input",
					Keys.chord(Keys.BACK_SPACE));

		}
		this.elementSendkeysXpath(driver,
				"//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/span/span[1]/input",
				settlementTimeList[1]);

		this.elememtClickXpath(driver, "//*[@id=\"overlap-manager-root\"]/div/div/div[1]/div/div[4]/div/span/button"); // 移動ボタンクリック

		File screenshot = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[1]/div"))
				.getScreenshotAs(OutputType.FILE);
		// screenshot.renameTo(new File("transactionNumber.png"));
		// FileUtils.copyFile(screenshot, new File(“screenshotNew.png”));
		String filePath = screenshot.getPath();
		System.out.println(filePath);
		s3.upLoad(filePath);
		transactionData.setScreenshotFilePath(filePath);

		repository.saveAndFlush(transactionData);

		driver.quit();
		return "redirect:/analysistool";
	}

	private void extracted(ChromeDriver driver) throws Exception {
		File screenshot = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[1]/div"))
				.getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(screenshot, new File(“screenshotNew.png”));
		String filePath = screenshot.getPath();
		System.out.println(filePath);
		s3.upLoad(filePath);

	}
	/*
	 * Actions actionProvider = new Actions(driver); Action keydownNew =
	 * actionProvider.keyDown(Keys.ALT).sendKeys("g").build(); keydownNew.perform();
	 * 
	 * @RequestMapping(path = "/selenium") public String transaction(ModelMap
	 * modelMap, @RequestParam("year") int year, @RequestParam("month") int month)
	 * throws Exception {
	 * 
	 * 
	 * String userId = "1318221"; String password = "hamuichi24";
	 * 
	 * 
	 * 
	 * String newTime = null; String settlementTime = null; String currencyPair =
	 * null; String[] newTimeList = new String[2]; String[] settlementTimeList = new
	 * String[2];
	 * 
	 * 
	 * 
	 * 
	 * ChromeDriver driver = this.driver();
	 * 
	 * 
	 * 
	 * driver.get("https://lionfx.hirose-fx.co.jp/web2/lionfx/#/login"); //証券会社の表示
	 * 
	 * this.elementSendkeys(driver,
	 * "/html/body/p7-app/p20-login/div/div/div/form/div[1]/input[1]", userId);
	 * //ユーザーIDの入力 this.elementSendkeys(driver,
	 * "/html/body/p7-app/p20-login/div/div/div/form/div[2]/input[1]", password);
	 * //パスワードの入力 this.elememtClickSelector(driver, "#lionFxLogin > button");
	 * //ログインボタンのクリック
	 * 
	 * 
	 * for(int i = 0; i < 50; i++) { continue; }
	 * 
	 * this.elememtClickSelector(driver,
	 * "#site-navbar-collapse > ul > li:nth-child(8) > a > gl-switchery > span > small"
	 * ); //約定一覧の選択
	 * 
	 * this.elememtClickSelector(driver,
	 * "#dealing-history-id > div.action > div:nth-child(2) > button.btn.btn-xs.btn-default.zoombutton.not-in-home"
	 * ); //+ボタンクリック this.elememtClickSelector(driver,
	 * "#dealing-history-id > div.action > div:nth-child(1) > div:nth-child(2) > div.time-area > div:nth-child(2) > label"
	 * ); //期間指定クリック this.elememtClickSelector(driver,
	 * "#DealingFromDatePickerCompId"); //開始日付
	 * 
	 * //月初めの選択
	 * 
	 * this.elememtClickXpath(driver,
	 * "//*[@id=\"bodyId\"]/div[3]/div[1]/table/thead/tr[2]/th[2]");
	 * this.elememtClickXpath(driver,
	 * "//*[@id=\"bodyId\"]/div[3]/div[2]/table/tbody/tr/td/span["+month+"]");
	 * boolean breakFlag = false; for(int i = 1; i <= 6; i++) { for(int j = 1; j <=
	 * 7; j++ ) { String date =
	 * driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+
	 * "]/td["+j+"]")).getText(); if( Integer.valueOf(date) == 1) {
	 * driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+
	 * "]/td["+j+"]")).click(); breakFlag = true; break; }
	 * 
	 * } if (breakFlag) { break; } }
	 * 
	 * 
	 * LocalDateTime nowDate = LocalDateTime.now(); DateTimeFormatter dtf1 =
	 * DateTimeFormatter.ofPattern("yyyy"); DateTimeFormatter dtf2 =
	 * DateTimeFormatter.ofPattern("M"); int formatNowDate =
	 * Integer.parseInt(dtf1.format(nowDate)); int formatNowDate2 =
	 * Integer.parseInt(dtf2.format(nowDate));
	 * 
	 * if(formatNowDate == year && formatNowDate2 != month || formatNowDate > year)
	 * { month++; this.elememtClickXpath(driver,
	 * "//*[@id=\"DealingToDatePickerCompId\"]"); this.elememtClickXpath(driver,
	 * "//*[@id=\"bodyId\"]/div[3]/div[1]/table/thead/tr[2]/th[2]");
	 * this.elememtClickXpath(driver,
	 * "//*[@id=\"bodyId\"]/div[3]/div[2]/table/tbody/tr/td/span["+month+"]");
	 * 
	 * breakFlag = false; for(int i = 1; i <= 6; i++) { for(int j = 1; j <= 7; j++ )
	 * { String date =
	 * driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+
	 * "]/td["+j+"]")).getText(); if( Integer.valueOf(date) == 1) {
	 * driver.findElement(By.xpath("/html/body/div[3]/div[1]/table/tbody/tr["+i+
	 * "]/td["+j+"]")).click(); breakFlag = true; break; }
	 * 
	 * } if (breakFlag) { break; } } }
	 * 
	 * 
	 * 
	 * this.elememtClickSelector(driver,
	 * "#dealing-history-id > div.action > div:nth-child(1) > div:nth-child(2) > div.filter-box.display-small-screen > button"
	 * ); //検索ボタンのクリック
	 * 
	 * this.elememtClickSelector(driver,
	 * "#site-navbar-collapse > ul > li:nth-child(2) > a > gl-switchery > span");
	 * //レート一覧の非表示 this.elememtClickSelector(driver,
	 * "#site-navbar-collapse > ul > li:nth-child(3) > a > gl-switchery > span");
	 * //証拠金状況紹介の非表示 this.elememtClickSelector(driver,
	 * "#site-navbar-collapse > ul > li:nth-child(5) > a > gl-switchery > span");
	 * //ポジション一覧の非表示 this.elememtClickSelector(driver,
	 * "#site-navbar-collapse > ul > li:nth-child(6) > a > gl-switchery > span");
	 * //注文一覧の非表示
	 * 
	 * 
	 * 
	 * 
	 * 
	 * this.elememtClickSelector(driver, "#toggleFullscreen > a"); //全画面表示
	 * 
	 * List<WebElement> tradeHistoryAlllist = driver.findElements(By.
	 * cssSelector("#center > div > div.ag-body > div.ag-body-viewport-wrapper > div > div > div"
	 * )); //全約定履歴の取得
	 * 
	 * for(WebElement tradeHistoryList : tradeHistoryAlllist) { //個別の履歴の内容をリストに格納
	 * 
	 * 
	 * String tradeHistory = tradeHistoryList.getText(); ArrayList<String> tradeList
	 * = new ArrayList<String>(Arrays.asList(tradeHistory.split("\n")));
	 * 
	 * 
	 * if(tradeList.size() == 15) { //通貨ペア、約定日時を取り出し、成形、リスト化
	 * 
	 * tradeList.remove(3); tradeList.remove(4); tradeList.remove(8);
	 * tradeList.remove(9);
	 * 
	 * 
	 * double rateDifference = 0; double profitlossParseint = 0; double newRate =
	 * Double.parseDouble(tradeList.get(7)); double settlementRate =
	 * Double.parseDouble(tradeList.get(2)); double newRatePip; double
	 * settlementRatePip;
	 * 
	 * currencyPair = tradeList.get(3); currencyPair = currencyPair.replace("/",
	 * ""); tradeList.set(3, currencyPair);
	 * 
	 * 
	 * 
	 * if(newRate <= 2) { newRatePip = newRate * 10000; settlementRatePip =
	 * settlementRate * 10000; } else { newRatePip = newRate * 100;
	 * settlementRatePip = settlementRate * 100; }
	 * 
	 * 
	 * if((tradeList.get(4)).equals("売")) {
	 * 
	 * rateDifference = settlementRatePip - newRatePip;
	 * 
	 * tradeList.set(4, "Long"); } else {
	 * 
	 * rateDifference = newRatePip - settlementRatePip;
	 * 
	 * tradeList.set(4, "Short"); }
	 * 
	 * profitlossParseint = rateDifference / newRatePip * 100;
	 * 
	 * System.out.println(tradeList);
	 * 
	 * settlementTime = tradeList.get(0); settlementTimeList =
	 * settlementTime.split(" "); settlementTimeList[1] =
	 * settlementTimeList[1].substring(0, 5); tradeList.set(0,
	 * settlementTimeList[0]); tradeList.add(1, settlementTimeList[1]);
	 * settlementTimeList[0] = settlementTimeList[0].replace("/", "-");
	 * 
	 * 
	 * newTime = tradeList.get(7); newTimeList = newTime.split(" "); newTimeList[1]
	 * = newTimeList[1].substring(0, 5); tradeList.set(7, newTimeList[0]);
	 * tradeList.add(8, newTimeList[1]); newTimeList[0] =
	 * newTimeList[0].replace("/", "-");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * TransactionData transactionData = new TransactionData();
	 * transactionData.setTransactionSettlementDate(tradeList.get(0));
	 * transactionData.setTransactionSettlementTime(tradeList.get(1));
	 * transactionData.setTransactionNumber(tradeList.get(2));
	 * transactionData.setRateSettlement(tradeList.get(3));
	 * transactionData.setCurrencyPair(tradeList.get(4));
	 * transactionData.setTransactionType(tradeList.get(5));
	 * transactionData.setTransactionLot(tradeList.get(6));
	 * transactionData.setTransactionNewDate(tradeList.get(7));
	 * transactionData.setTransactionNewTime(tradeList.get(8));
	 * transactionData.setRateNew(tradeList.get(9));
	 * transactionData.setProfitLoss(tradeList.get(10).replace(",", ""));
	 * transactionData.setSwap(tradeList.get(11).replace(",", ""));
	 * transactionData.setProfitLossConfirm(tradeList.get(12).replace(",", ""));
	 * transactionData.setRateDifference((double)
	 * (Math.round(rateDifference*100.0)/100.0));
	 * transactionData.setProfitlossParseint((double)
	 * (Math.round(profitlossParseint*100.0)/100.0));
	 * 
	 * 
	 * 
	 * 
	 * repository.saveAndFlush(transactionData);
	 * 
	 * } } driver.quit(); return "redirect:/analysistool";
	 * 
	 * }
	 */

}
