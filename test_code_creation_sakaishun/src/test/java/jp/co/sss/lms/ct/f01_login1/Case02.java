package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.sss.lms.controller.LoginController;
import jp.co.sss.lms.dto.InfoDto;
import jp.co.sss.lms.service.InfoService;
import jp.co.sss.lms.service.LoginService;
import jp.co.sss.lms.util.LoginUserUtil;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	public MockMvc mockMvc;

	public MockHttpServletRequestBuilder getRequest;

	@Mock
	private LoginUserUtil loginUserUtil;

	@Mock
	private LoginService loginService;

	@Mock
	private InfoService infoService;

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

	@InjectMocks
	private LoginController loginController;

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
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
		MockHttpSession session = new MockHttpSession();
		ReflectionTestUtils.setField(loginController, "session", session);

		when(loginUserUtil.isLogin()).thenReturn(false);
		when(infoService.getInfo()).thenReturn(new InfoDto());

		getRequest = MockMvcRequestBuilders.get("/");

		try {
			mockMvc.perform(getRequest)
					.andExpect(status().isOk())
					.andExpect(view().name("login/index"))
					.andReturn();

		} catch (Exception e) {
			e.printStackTrace();
			fail(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test
	@Order(2)
	@DisplayName("Case02_01_トップページの画像取得")
	public void testSeleniumCase02_01() throws Exception {
		driver.get("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", driver.getTitle());
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case02_01_capture.png"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		String userId = "StudentAA100";
		String password = "StudentAA01";

		when(loginService.getLoginInfo(userId, password)).thenReturn("ログインに失敗しました");

		getRequest = MockMvcRequestBuilders.post("/login").param("loginId", userId).param("password", password);

		try {
			mockMvc.perform(getRequest)
					.andExpect(status().isOk())
					.andExpect(view().name("login/index"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test
	@Order(4)
	@DisplayName("Case02_02_トップページの画像取得")
	public void testSeleniumCase02_02() throws Exception {
		driver.get("http://localhost:8080/lms/");
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement loginIdInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginId")));
		loginIdInput.clear();
		loginIdInput.sendKeys("StudentAA100");

		WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		passwordInput.clear();
		passwordInput.sendKeys("StudentAA01");

		WebElement loginCrick = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[value='ログイン']")));
		loginCrick.click();

		assertEquals("ログイン | LMS", driver.getTitle());
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(file.toPath(), Paths.get("./evidence/Case02_02_capture.png"));
	}

}