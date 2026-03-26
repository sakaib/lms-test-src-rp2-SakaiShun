package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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
	void test01() {
		goTo("http://localhost:8080/lms/");
		//assertEquals("http://localhost:" + port + "/lms/", driver.getCurrentUrl());
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	/*void test01() throws Exception {
		driver.get("http://localhost:" + port + "/lms/");
		assertEquals("http://localhost:" + port + "/lms/", driver.getCurrentUrl());
		assertEquals("ログイン | LMS", driver.getTitle());
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case01_01_capture.png"));
	}*/

}