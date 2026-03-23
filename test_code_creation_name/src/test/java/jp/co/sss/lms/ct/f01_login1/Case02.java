package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	@AfterEach
	public void tearDown() {

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

}
