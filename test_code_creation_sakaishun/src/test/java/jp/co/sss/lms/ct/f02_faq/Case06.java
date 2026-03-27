package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		//ログイン画面へ遷移後、ページタイトルを検証
		goTo("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		//初回ログイン済みの「ID：StudentAA02、password：studentAA02」の値を代入
		String userId = "StudentAA02";
		String password = "studentAA02";

		//ID入力欄クリア後、入力
		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		loginIdInput.clear();
		loginIdInput.sendKeys(userId);

		//password入力欄クリア後、入力
		WebElement passwordInput = webDriver.findElement(By.id("password"));
		passwordInput.clear();
		passwordInput.sendKeys(password);

		//ID、password入力後、ログインボタンを押下
		WebElement loginCrick = webDriver.findElement(By.cssSelector("input[value='ログイン']"));
		loginCrick.click();

		//現在のアドレス欄のURLとページタイトルを取得し、コース詳細画面のURL・ページタイトルと比較
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

		//上部メニューの「機能」ボタンを押下
		WebElement dropDownToggleCrick = webDriver.findElement(By.className("dropdown-toggle"));
		dropDownToggleCrick.click();

		//「機能」メニュー内の「ヘルプ」リンクをクリック
		WebElement dropDownToggleHelpCrick = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));
		dropDownToggleHelpCrick.click();

		//現在のアドレス欄のURLとページタイトルを取得し、ヘルプ画面のURL・ページタイトルと比較
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

		//ヘルプ画面内の「よくある質問」リンクをクリック
		WebElement lmsFaqCrick = webDriver.findElement(By.cssSelector("a[href='/lms/faq']"));
		lmsFaqCrick.click();

		//「よくある質問」リンククリック後の新規タブへフォーカス
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		//現在のアドレス欄のURLとページタイトルを取得し、よくある質問画面のURL・ページタイトルと比較
		String currentUrl = webDriver.getCurrentUrl();
		assertTrue(currentUrl.contains("/lms/faq"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		//よくある質問画面内の「【研修関係】」リンクをクリック
		WebElement trainingCrick = webDriver
				.findElement(By.cssSelector("a[href='/lms/faq?frequentlyAskedQuestionCategoryId=1']"));
		trainingCrick.click();

		//よくある質問画面から遷移していないことを確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//検索結果が見えるようにスクロールしてキャプチャ
		scrollTo("260");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		//検索結果の回答が表示されるように取得したタグの数だけクリック
		List<WebElement> answerCricks = webDriver.findElements(By.tagName("dl"));
		for (WebElement answerCrick : answerCricks) {
			answerCrick.click();
		}

		//よくある質問画面から遷移していないことを確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//検索結果が見えるようにスクロールしてキャプチャ
		scrollTo("450");
		getEvidence(new Object() {
		});
	}
}
