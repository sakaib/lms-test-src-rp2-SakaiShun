package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		String userId = "StudentAA02";
		String password = "studentAA02";

		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		loginIdInput.clear();
		loginIdInput.sendKeys(userId);

		WebElement passwordInput = webDriver.findElement(By.id("password"));
		passwordInput.clear();
		passwordInput.sendKeys(password);

		WebElement loginCrick = webDriver.findElement(By.cssSelector("input[value='ログイン']"));
		loginCrick.click();

		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("/lms/course/detail"));
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		WebElement dropDownToggleCrick = webDriver.findElement(By.className("dropdown-toggle"));
		dropDownToggleCrick.click();

		WebElement dropDownToggleHelpCrick = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));
		dropDownToggleHelpCrick.click();

		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("/lms/help"));
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		WebElement lmsFaqCrick = webDriver.findElement(By.cssSelector("a[href='/lms/faq']"));
		lmsFaqCrick.click();

		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("/lms/faq"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

}
