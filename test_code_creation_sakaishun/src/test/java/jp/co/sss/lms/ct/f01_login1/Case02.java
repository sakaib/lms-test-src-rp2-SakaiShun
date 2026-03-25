package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	@LocalServerPort
	private int port;

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
	}

	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() throws Exception {
		driver.get("http://localhost:" + port + "/lms/");
		assertEquals("http://localhost:" + port + "/lms/", driver.getCurrentUrl());
		assertEquals("ログイン | LMS", driver.getTitle());
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case02_01_capture.png"));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws Exception {
		driver.get("http://localhost:" + port + "/lms/");
		assertEquals("http://localhost:" + port + "/lms/", driver.getCurrentUrl());
		assertEquals("ログイン | LMS", driver.getTitle());

		String userId = "StudentAA100";
		String password = "StudentAA01";

		WebElement loginIdInput = driver.findElement(By.id("loginId"));
		loginIdInput.clear();
		loginIdInput.sendKeys(userId);

		WebElement passwordInput = driver.findElement(By.id("password"));
		passwordInput.clear();
		passwordInput.sendKeys(password);

		WebElement loginCrick = driver.findElement(By.cssSelector("input[value='ログイン']"));
		loginCrick.click();

		String currentUrl = driver.getCurrentUrl();
		assertTrue(currentUrl.contains("/lms/login"));
		assertEquals("ログイン | LMS", driver.getTitle());

		WebElement cssElement = driver.findElement(By.cssSelector("span[class='help-inline error']"));
		assertEquals("* ログインに失敗しました。", cssElement.getText());

		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case02_02_capture.png"));
	}
}