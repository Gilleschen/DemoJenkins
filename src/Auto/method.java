package Auto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class method {
	int port = 4723;// Appium port
	int device_timeout = 60;// 60sec
	int command_timeout = 30;// 30sec
	LoadExpectResult ExpectResult = new LoadExpectResult();
	static LoadTestCase TestCase = new LoadTestCase();
	static ArrayList ResultList = new ArrayList();// 各測試案例的執行結果(一維)
	static ArrayList<ArrayList> AllResultList = new ArrayList<ArrayList>();// 所有測試案例的執行結果(二維)
	static AndroidDriver driver;
	static String appElemnt;// APP元件名稱
	static String appInput;// 輸入值
	static String appInputXpath;// 輸入值的Xpath格式
	static String toElemnt;// APP元件名稱
	static int startx, starty, endx, endy;// Swipe移動座標
	static int iterative;// 畫面滑動次數
	static String scroll;// 畫面捲動方向
	static String appElemntarray;// 搜尋的多筆類似元件
	static String element, appPackage, appActivity, deviceName, platformVersion;
	static int CurrentCaseNumber = -1;// 目前執行到第幾個測試案列
	static Boolean CommandError;// 判定執行的指令是否出現錯誤；ture為正確；false為錯誤
	static Boolean VerifiedResult;// Verified判斷結果；ture為正確；false為錯誤
	static int CurrentCaseStep;
	static long totaltime;// 統計所有案例測試時間

	public ArrayList<ArrayList> method() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		Object obj = new method();
		Class c = obj.getClass();
		String methodName = null;

		for (int CurrentCase = 0; CurrentCase < TestCase.StepList.size(); CurrentCase++) {
			Stopwatch timer = Stopwatch.createStarted();// 開始計時
			System.out.println("[info] CaseName:|" + TestCase.CaseList.get(CurrentCase).toString() + "|");
			ResultList = new ArrayList();
			ResultList.add(TestCase.CaseList.get(CurrentCase).toString());
			CommandError = true;// 預設CommandError為True
			for (int CurrentCaseStep = 0; CurrentCaseStep < TestCase.StepList.get(CurrentCase)
					.size(); CurrentCaseStep++) {
				if (!CommandError) {
					System.out.println("[Result]" + TestCase.CaseList.get(CurrentCase).toString() + ":ERROR!");
					System.out.println("");
					break;// 若目前測試案例出現CommandError=false，則跳出目前案例並執行下一個案例
				}
				switch (TestCase.StepList.get(CurrentCase).get(CurrentCaseStep).toString()) {

				case "LaunchAPP":
					methodName = "LaunchAPP";
					break;

				case "Byid_SendKey":
					methodName = "Byid_SendKey";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "Byid_Click":
					methodName = "Byid_Click";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_Swipe":
					methodName = "Byid_Swipe";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					toElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "ByXpath_SendKey":
					methodName = "ByXpath_SendKey";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "Byid_Clear":
					methodName = "Byid_Clear";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Clear":
					methodName = "ByXpath_Clear";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Click":
					methodName = "ByXpath_Click";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Swipe":
					methodName = "ByXpath_Swipe";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					toElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "Byid_Wait":
					methodName = "Byid_Wait";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_Wait":
					methodName = "ByXpath_Wait";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "HideKeyboard":
					methodName = "HideKeyboard";
					break;

				case "Byid_VerifyText":
					methodName = "Byid_VerifyText";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_VerifyText":
					methodName = "ByXpath_VerifyText";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_VerifyRadioButton":
					methodName = "Byid_VerifyRadioButton";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_VerifyRadioButton":
					methodName = "ByXpath_VerifyRadioButton";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Sleep":
					methodName = "Sleep";
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ScreenShot":
					methodName = "ScreenShot";
					break;

				case "Orientation":
					methodName = "Orientation";
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Swipe":
					methodName = "Swipe";
					startx = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1));
					starty = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2));
					endx = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 3));
					endy = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 4));
					iterative = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 5));
					CurrentCaseStep = CurrentCaseStep + 5;
					break;

				case "ByXpath_Swipe_Vertical":
					methodName = "ByXpath_Swipe_Vertical";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					scroll = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					iterative = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 3));
					CurrentCaseStep = CurrentCaseStep + 3;
					break;

				case "ByXpath_Swipe_Horizontal":
					methodName = "ByXpath_Swipe_Horizontal";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					scroll = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					iterative = Integer.valueOf(TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 3));
					CurrentCaseStep = CurrentCaseStep + 3;
					break;

				case "ByXpath_Swipe_FindText_Click_Android":
					methodName = "ByXpath_Swipe_FindText_Click_Android";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					scroll = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					appElemntarray = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 3);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 4);
					appInputXpath = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 5);
					CurrentCaseStep = CurrentCaseStep + 5;
					break;

				case "Byid_LongPress":
					methodName = "Byid_LongPress";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_LongPress":
					methodName = "ByXpath_LongPress";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Byid_invisibility":
					methodName = "Byid_invisibility";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ByXpath_invisibility":
					methodName = "ByXpath_invisibility";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "Back":
					methodName = "Back";
					break;

				case "Home":
					methodName = "Home";
					break;

				case "Power":
					methodName = "Power";
					break;

				case "Recent":
					methodName = "Recent";
					break;

				case "ResetAPP":
					methodName = "ResetAPP";
					break;

				case "QuitAPP":
					methodName = "QuitAPP";
					break;
				}

				Method method;
				method = c.getMethod(methodName, new Class[] {});
				method.invoke(c.newInstance());

			}
			System.out.println("(" + timer.stop() + ")");
			totaltime += timer.elapsed(TimeUnit.SECONDS);
			System.out.println("");

		}
		System.out.println("測試結束!!!" + "(" + totaltime + " s)");
		return AllResultList;
	}

	public void Byid_VerifyText() {

		try {
			System.out.println("[info] Executing:|Byid_VerifyText|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.getText();
			// 回傳測試案例清單的名稱給ExpectResult.LoadExpectResult，並存放期望結果至ResultList清單
			ExpectResult.LoadExpectResult(TestCase.CaseList.get(CurrentCaseNumber).toString());

			for (int j = 0; j < ExpectResult.ResultList.size(); j++) {
				if (element.equals(ExpectResult.ResultList.get(j))) {
					VerifiedResult = true;
					break;
				} else {
					VerifiedResult = false;
				}
			}
			if (VerifiedResult) {
				System.out.println("[info] Result_Byid_VerifyText:|PASS|");
				ResultList.add(true);
			} else {
				System.out.println("[info] Result_Byid_VerifyText:|FAIL|");
				ResultList.add(false);
			}
			AllResultList.add(ResultList);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);

		}

	}

	public void ByXpath_VerifyText() {
		try {
			System.out.println("[info] Executing:|ByXpath_VerifyText|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).getText();

			// 回傳測試案例清單的名稱給ExpectResult.LoadExpectResult，並存放期望結果至ResultList清單
			ExpectResult.LoadExpectResult(TestCase.CaseList.get(CurrentCaseNumber).toString());

			for (int j = 0; j < ExpectResult.ResultList.size(); j++) {
				if (element.equals(ExpectResult.ResultList.get(j))) {
					VerifiedResult = true;
					break;
				} else {
					VerifiedResult = false;
				}
			}

			if (VerifiedResult) {
				System.out.println("[info] Result_ByXpath_VerifyText:|PASS|");
				ResultList.add(true);
			} else {
				System.out.println("[info] Result_ByXpath_VerifyText:|FAIL|");
				ResultList.add(false);
			}
			AllResultList.add(ResultList);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}

	}

	public void Byid_VerifyRadioButton() {

		try {
			System.out.println("[info] Executing:|Byid_VerifyRadioButton|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.getAttribute("checked");
			if (element.equals("true")) {
				System.out.println("[info] Byid_VerifyRadioButton:|PASS|");
				VerifiedResult = true;
				ResultList.add(VerifiedResult);
			} else {
				System.out.println("[info] Byid_VerifyRadioButton:|FAIL|");
				VerifiedResult = false;
				ResultList.add(VerifiedResult);
			}
			AllResultList.add(ResultList);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_VerifyRadioButton() {

		try {
			System.out.println("[info] Executing:|ByXpath_VerifyRadioButton|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)))
					.getAttribute("checked");
			if (element.equals("true")) {
				System.out.println("[info] Byid_VerifyRadioButton:|PASS|");
				VerifiedResult = true;
				ResultList.add(VerifiedResult);
			} else {
				System.out.println("[info] Byid_VerifyRadioButton:|FAIL|");
				VerifiedResult = false;
				ResultList.add(VerifiedResult);
			}
			AllResultList.add(ResultList);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_Wait() {
		try {
			System.out.println("[info] Executing:|Byid_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)));
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_Wait() {
		try {
			System.out.println("[info] Executing:|ByXpath_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appElemnt)));
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_SendKey() {
		try {
			System.out.println("[info] Executing:|Byid_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.sendKeys(appInput);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_SendKey() {
		try {
			System.out.println("[info] Executing:|ByXpath_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).sendKeys(appInput);
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_Click() {
		try {
			System.out.println("[info] Executing:|Byid_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.click();
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_Click() {
		try {
			System.out.println("[info] Executing:|ByXpath_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).click();
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_Clear() {

		try {
			System.out.println("[info] Executing:|Byid_Clear|" + appElemnt + "|Clear|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.clear();
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_Clear() {

		try {
			System.out.println("[info] Executing:|ByXpath_Clear|" + appElemnt + "|Clear|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).clear();

		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;// 若找不到指定元件，則設定CommandError=false
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void HideKeyboard() {
		try {
			if (driver.isKeyboardShown()) {
				System.out.println("[info] Executing:|HideKeyboard|");
				driver.hideKeyboard();
			}
		} catch (Exception e) {
			System.out.println("[Error] Can't hide keyboard");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Sleep() {
		String NewString = "";// 新字串
		char[] r = { '.' };// 小數點字元
		char[] c = appInput.toCharArray();// 將字串轉成字元陣列
		for (int i = 0; i < c.length; i++) {
			if (c[i] != r[0]) {// 判斷字元是否為小數點
				NewString = NewString + c[i];// 否，將字元組合成新字串
			} else {
				break;// 是，跳出迴圈
			}
		}

		try {

			System.out.println("[info] Executing:|Sleep|" + NewString + " second..." + "|");
			Thread.sleep(Integer.valueOf(NewString) * 1000);// 將字串轉成整數

		} catch (Exception e) {
			;
		}
	}

	public void ScreenShot() {

		Calendar date = Calendar.getInstance();
		String month = Integer.toString(date.get(Calendar.MONTH) + 1);
		String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
		String hour = Integer.toString(date.get(Calendar.HOUR_OF_DAY));
		String min = Integer.toString(date.get(Calendar.MINUTE));
		String sec = Integer.toString(date.get(Calendar.SECOND));
		File screenShotFile = (File) driver.getScreenshotAs(OutputType.FILE);
		System.out.println("[info] Executing:|ScreenShot|");
		try {
			FileUtils.copyFile(screenShotFile,
					new File(TestCase.CaseList.get(CurrentCaseNumber) + "_" + month + day + hour + min + sec + ".jpg"));
		} catch (IOException e) {
			System.out.println("[Error]Fail to ScreenShot");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Orientation() {

		try {
			if (appInput.equals("Landscape")) {
				System.out.println("[info] Executing:|Orientation|Landscape|");
				driver.rotate(ScreenOrientation.LANDSCAPE);
			} else if (appInput.equals("Portrait")) {
				System.out.println("[info] Executing:|Orientation|Portrait|");
				driver.rotate(ScreenOrientation.PORTRAIT);
			}
		} catch (Exception e) {
			if (appInput.equals("Landscape")) {
				System.out.println("[Error] Can't rotate to landscape");
			} else {
				System.out.println("[Error] Can't rotate to portrait");
			}
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void QuitAPP() {
		boolean state = false;
		try {
			System.out.println("[info] Executing:|QuitAPP|");
			driver.quit();
			// 確認Step中是否包含ByXpath_VerifyText或Byid_VerifyText
			for (int i = 0; i < TestCase.StepList.get(CurrentCaseNumber).size(); i++) {
				if (TestCase.StepList.get(CurrentCaseNumber).get(i).equals("Byid_VerifyText")
						|| TestCase.StepList.get(CurrentCaseNumber).get(i).equals("ByXpath_VerifyText")
						|| TestCase.StepList.get(CurrentCaseNumber).get(i).equals("Byid_VerifyRadioButton")
						|| TestCase.StepList.get(CurrentCaseNumber).get(i).equals("ByXpath_VerifyRadioButton")) {
					state = true;// true代表找到ByXpath_VerifyText或Byid_VerifyText或ByXpath_VerifyRadioButton或Byid_VerifyRadioButton
					break;
				}
			}

			if (!state) {
				if (CommandError) {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":PASS");
					ResultList.add(true);
					AllResultList.add(ResultList);
				}
			} else {
				if (CommandError && VerifiedResult) {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":PASS");
				} else {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCaseNumber).toString() + ":FAIL");
				}
			}
			// System.out.println("");
		} catch (Exception e) {
			System.out.println("[Error] Can't quit APP");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ResetAPP() {

		try {
			System.out.println("[info] Executing:|ResetAPP|");
			driver.resetApp();
		} catch (Exception e) {
			System.out.println("[Error] Can't reset APP");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void LaunchAPP() {
		CurrentCaseNumber = CurrentCaseNumber + 1;
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, device_timeout);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, TestCase.DeviceInformation.deviceName.get(0));// 固定index
																											// 0
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, TestCase.DeviceInformation.platformVersion.get(0));// 固定index
																													// 0
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, TestCase.DeviceInformation.appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, TestCase.DeviceInformation.appActivity);
		cap.setCapability(MobileCapabilityType.NO_RESET, TestCase.DeviceInformation.ResetAPP);

		try {
			System.out.println("[info] Executing:|LaunchAPP|" + TestCase.DeviceInformation.appPackage + "|");
			driver = new AndroidDriver<>(new URL("http://127.0.0.1:" + port + "/wd/hub"), cap);
		} catch (Exception e) {
			System.out.print("[Error] Can't find Device UDID:" + deviceName);
			System.out.println(" or can not find appPackage: " + appPackage);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Back() {
		try {
			System.out.println("[info] Executing:|Back|");
			driver.pressKeyCode(AndroidKeyCode.BACK);
		} catch (Exception e) {
			System.out.println("[Error] Can't execute Back button");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}

	}

	public void Home() {
		try {
			System.out.println("[info] Executing:|Home|");
			driver.pressKeyCode(AndroidKeyCode.HOME);
		} catch (Exception e) {
			System.out.println("[Error] Can't execute Home button");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}

	}

	public void Power() {
		try {
			System.out.println("[info] Executing:|Power|");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER);
		} catch (Exception e) {
			System.out.println("[Error] Can't execute Power button");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Recent() {
		try {
			System.out.println("[info] Executing:|Recent|");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		} catch (Exception e) {
			System.err.println("[Error] Can't execute Recent button");
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_invisibility() {
		try {
			System.out.println("[info] Executing:|Byid_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)));
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_invisibility() {
		try {
			System.out.println("[info] Executing:|ByXpath_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(appElemnt)));
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void Byid_LongPress() {
		try {
			System.out.println("[info] Executing:|Byid_LongPress|" + appElemnt + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			t.longPress(wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt))))
					.perform();
		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_LongPress() {
		try {
			System.out.println("[info] Executing:|ByXpath_LongPress|" + appElemnt + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			t.longPress(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)))).perform();

		} catch (Exception e) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	public void ByXpath_Swipe() {
		Point p1, p2;// p1 為起點;p2為終點

		try {
			System.out.println("[info] Executing:|ByXpath_Swipe|" + appElemnt + "|" + toElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			p2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toElemnt))).getLocation();
			p1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).getLocation();
			driver.swipe(p1.x, p1.y, p1.x, p1.y - (p1.y - p2.y), 1000);

		} catch (Exception e) {
			System.out.print("[Error] Can't find " + appElemnt);
			System.out.println(" or Can't find " + toElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}

	}

	public void Byid_Swipe() {
		Point p1, p2;// p1 為起點;p2為終點

		try {
			System.out.println("[info] Executing:|Byid_Swipe|" + appElemnt + "|" + toElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			p2 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + toElemnt)))
					.getLocation();
			p1 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.getLocation();
			driver.swipe(p1.x, p1.y, p1.x, p1.y - (p1.y - p2.y), 1000);

		} catch (Exception e) {
			System.out.print("[Error] Can't find " + appElemnt);
			System.out.println(" or Can't find " + toElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}

	}

	public void Swipe() {

		for (int j = 0; j < iterative; j++) {
			try {
				System.out.println(
						"[info] Executing:|Swipe|(" + startx + "," + starty + ")|(" + endx + "," + endy + ")|");
				driver.swipe(startx, starty, endx, endy, 500);
			} catch (Exception e) {
				System.out.println(
						"[Error] Can't swipe " + "(" + startx + "," + starty + ")" + " to (" + endx + "," + endy + ")");
				CommandError = false;
				ResultList.add("error");
				AllResultList.add(ResultList);
				break;// 出錯後，離開iterative回圈
			}
		}
	}

	public void ByXpath_Swipe_Vertical() {
		Point p;// 元件座標
		Dimension s;// 元件大小
		WebElement e;

		try {
			System.out.println(
					"[info] Executing:|ByXpath_Swipe_Vertical|" + appElemnt + "|" + scroll + "|" + iterative + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));
			s = e.getSize();
			p = e.getLocation();
			int errorX = (int) Math.round(s.width * 0.01);
			int errorY = (int) Math.round(s.height * 0.01);
			for (int j = 0; j < iterative; j++) {
				if (scroll.equals("DOWN")) {// 畫面向下捲動
					driver.swipe(p.x + errorX, p.y + s.height - errorY, p.x + errorX, p.y + errorY, 1000);
				} else if (scroll.equals("UP")) {// 畫面向上捲動
					driver.swipe(p.x + errorX, p.y + errorY, p.x + errorX, p.y + s.height - errorY, 1000);
				}
			}

		} catch (Exception w) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);

		}
	}

	public void ByXpath_Swipe_Horizontal() {
		Point p;// 元件座標
		Dimension s;// 元件大小
		WebElement e;

		try {
			System.out.println(
					"[info] Executing:|ByXpath_Swipe_Horizontal|" + appElemnt + "|" + scroll + "|" + iterative + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));
			// e = driver.get(i).findElement(By.xpath(appElemnt));
			s = e.getSize();
			p = e.getLocation();
			int errorX = (int) Math.round(s.getWidth() * 0.01);
			int errorY = (int) Math.round(s.getHeight() * 0.01);
			for (int j = 0; j < iterative; j++) {
				if (scroll.equals("RIGHT")) {// 畫面向右捲動 (觀看畫面左方內容)
					driver.swipe(p.x + errorX, p.y + errorY, p.x + s.width - errorX, p.y + errorY, 1000);
				} else if (scroll.equals("LEFT")) {// 畫面向左捲動 (觀看畫面右方內容)
					driver.swipe(p.x + s.width - errorX, p.y + errorY, p.x + errorX, p.y + errorY, 1000);
				}
			}

		} catch (Exception w) {
			System.out.println("[Error] Can't find " + appElemnt);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	// ByXpath_Swipe_FindText_Click_Android缺點：1.搜尋的字串不可重複 2.搜尋5次都沒找到元件，則停止搜尋
	public void ByXpath_Swipe_FindText_Click_Android() {

		int SearchNumber = 0;// 搜尋次數
		Point ScrollBarP;// 卷軸元件座標
		Dimension ScrollBarS;// 卷軸元件之長及寬
		WebElement ScrollBar;// 卷軸元件

		try {
			System.out.println("[info] Executing:|ByXpath_Swipe_FindText_Click_Android|" + appElemnt + "|" + scroll
					+ "|" + appElemntarray + "|" + appInput + "|" + appInputXpath + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			ScrollBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));// 卷軸元件
			ScrollBarS = ScrollBar.getSize();// 卷軸元件的長及寬
			ScrollBarP = ScrollBar.getLocation();// 卷軸的座標
			int errorX = (int) Math.round(ScrollBarS.width * 0.1);
			int errorY = (int) Math.round(ScrollBarS.height * 0.1);
			List<WebElement> targetlist = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(appElemntarray)));// 要搜尋的多筆類似元件清單

			for (int i = 0; i < targetlist.size(); i++) {

				if ((targetlist.get(i).getText().toString()).equals(appInput)) {// 若targetelement在targetlist清單中，則點擊targetelement
					WebElement targetElement;// 準備搜尋的元件
					Point targetElementP;// 準備搜尋的元件之座標
					Dimension targetElementS;// 準備搜尋的元件之長及寬
					targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appInputXpath)));
					targetElementP = targetElement.getLocation();// 準備搜尋元件的座標
					targetElementS = targetElement.getSize();// 準備搜尋元件的長及寬

					switch (scroll.toString()) {

					case "DOWN":
						if (targetElementP.y > ScrollBarP.y + ScrollBarS.height) {// 若搜尋元件的y座標大於卷軸範圍，表示搜尋元件全部UI被卷軸遮住
							driver.swipe(targetElementP.x, ScrollBarS.height + ScrollBarP.y - errorY, targetElementP.x,
									ScrollBarP.y + errorY, 1000);
						} else if (targetElementP.y + targetElementS.height == ScrollBarP.y + ScrollBarS.height) {// 若搜尋元件的y座標與寬度總和等於卷軸長度，表示搜尋元件的部分UI被卷軸遮住
							driver.swipe(targetElementP.x - errorY, targetElementP.y, targetElementP.x,
									ScrollBarP.y + errorY, 1000);
						}
						break;

					case "UP":
						if (targetElementP.y + targetElementS.height < ScrollBarP.y) {// 若搜尋元件的最大y座標小於卷軸y座標，表示搜尋元件全部UI被卷軸遮住
							driver.swipe(targetElementP.x, ScrollBarP.y + errorY, targetElementP.x,
									ScrollBarS.height + ScrollBarP.y - errorY, 1000);
						} else {// 反之，若搜尋元件的最大y座標大於卷軸y座標，表示搜尋元件全部UI被卷軸遮住
							driver.swipe(targetElementP.x, ScrollBarP.y + errorY, targetElementP.x,
									ScrollBarP.y + ScrollBarS.height - errorY, 1000);
						}
						break;

					case "LEFT":// 畫面向左捲動(觀看畫面右方內容)
						if (targetElementP.x > ScrollBarP.x + ScrollBarS.width) {// 若搜尋元件的x座標大於卷軸範圍，表示搜尋元件全部UI被卷軸遮住
							driver.swipe(ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y,
									ScrollBarP.x + errorX, targetElementP.y, 1000);
						} else if (targetElementP.x + targetElementS.width == ScrollBarP.x + ScrollBarS.width) {// 若搜尋元件的x座標與寬度總和等於卷軸寬度，表示搜尋元件的部分UI被卷軸遮住
							driver.swipe(targetElementP.x - errorX, targetElementP.y, ScrollBarP.x + errorX,
									targetElementP.y, 1000);
						}
						break;

					case "RIGHT":// 畫面向右捲動(觀看畫面左方內容)
						if (targetElementP.x + targetElementS.width < ScrollBarP.x) {// 若搜尋元件的最大x座標小於卷軸x座標，表示搜尋元件全部UI被卷軸遮住
							driver.swipe(ScrollBarP.x + errorX, targetElementP.y,
									ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y, 1000);
						} else if (targetElementP.x == ScrollBarP.x) {// 若搜尋元件的x座標等於卷軸x座標，可能表示搜尋元件的部分UI被卷軸遮住
							driver.swipe(targetElementP.x + targetElementS.width + errorX, targetElementP.y,
									ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y, 1000);
						}
						break;
					}

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appInputXpath))).click();
					break;
				}

				if (i == targetlist.size() - 1) {// 若targetlist中最後一筆資料比對完後，則進行Srcoll拖曳

					switch (scroll.toString()) {

					case "DOWN":
						driver.swipe(ScrollBarP.x + errorX, ScrollBarP.y + ScrollBarS.height - errorY,
								ScrollBarP.x + errorX, ScrollBarP.y + errorY, 1000);// 向下捲動
						break;

					case "UP":
						driver.swipe(ScrollBarP.x + errorX, ScrollBarP.y + errorY, ScrollBarP.x + errorX,
								ScrollBarP.y + ScrollBarS.height - errorY, 1000);// 向上捲動
						break;

					case "LEFT":
						driver.swipe(ScrollBarP.x + ScrollBarS.width - errorX, ScrollBarP.y + errorY,
								ScrollBarP.x + errorX, ScrollBarP.y + errorY, 1000);// 畫面向左捲動(觀看畫面右方內容)
						break;

					case "RIGHT":
						driver.swipe(ScrollBarP.x + errorX, ScrollBarP.y + errorY,
								ScrollBarP.x + ScrollBarS.width - errorX, ScrollBarP.y + errorY, 1000);// 畫面向右捲動(觀看畫面左方內容)
						break;

					}
					SearchNumber++;// 累計搜尋次數
					targetlist.clear();// 清除targetlist
					targetlist = wait
							.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(appElemntarray)));// 再次取得新targetlist

					if (SearchNumber == 10) {// 搜尋10次都沒找到元件，則跳出for
						System.out.println("Can't find " + appInput);// 印出找不到
						break;// 跳出for
					} else {
						i = -1;// 若SearchNumber!=10，則令i=-1(目的：再次執行for)
					}
				}
			}

		} catch (Exception w) {
			System.out.print("[Error] Can't find " + appElemnt);
			System.out.print(" or [Error] can not find " + appElemntarray);
			System.out.println(" or [Error] can not find " + appInputXpath);
			CommandError = false;
			ResultList.add("error");
			AllResultList.add(ResultList);
		}
	}

	// /*
	// * 上下隨機滑動n次 public void Swipe() { Random rand = new Random(); boolean
	// * items[] = { true, false }; for (int i = 0; i < driver.size(); i++) {
	// for
	// * (int j = 0; j < iterative; j++) { if
	// (items[rand.nextInt(items.size())])
	// * { driver.get(i).swipe(startx, starty, endx, endy, 500); }else{
	// * driver.get(i).swipe(endx, endy, startx , starty , 500); } } } }
	// *
	// */

}
