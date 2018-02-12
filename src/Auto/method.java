package Auto;

import static java.time.Duration.ofSeconds;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Stopwatch;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;

public class method {
	int port = 4723;// Appium port
	int device_timeout = 120;// 120sec
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
	static String switchWiFi;// 啟動wifi或關閉wifi
	static int CurrentCaseNumber = -1;// 目前執行到第幾個測試案列
	static Boolean CommandError;// 判定執行的指令是否出現錯誤；ture為正確；false為錯誤
	static Boolean VerifiedResult;// Verified判斷結果；ture為正確；false為錯誤
	static int CurrentCaseStep;
	static long totaltime;// 統計所有案例測試時間
	static int CurrentCase;

	public ArrayList<ArrayList> method() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		Object obj = new method();
		Class c = obj.getClass();
		String methodName = null;
		CeateAppiumSession();// 啟動Appium Session
		for (CurrentCase = 0; CurrentCase < TestCase.StepList.size(); CurrentCase++) {
			Stopwatch timer = Stopwatch.createStarted();// 開始計時
			System.out.println("[info] CaseName:|" + TestCase.CaseList.get(CurrentCase).toString() + "|");
			ResultList = new ArrayList();
			ResultList.add(TestCase.CaseList.get(CurrentCase).toString());
			CommandError = true;// 預設CommandError為True
			for (int CurrentCaseStep = 0; CurrentCaseStep < TestCase.StepList.get(CurrentCase)
					.size(); CurrentCaseStep++) {
				if (!CommandError) {
					System.out.print("[Result] " + TestCase.CaseList.get(CurrentCase).toString() + ":ERROR!");
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

				case "Byid_VerifyRadioButton":// 僅適用於能區分checked屬性的元件
					methodName = "Byid_VerifyRadioButton";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
					break;

				case "ByXpath_VerifyRadioButton":// 僅適用於能區分checked屬性的元件
					methodName = "ByXpath_VerifyRadioButton";
					appElemnt = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					appInput = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 2);
					CurrentCaseStep = CurrentCaseStep + 2;
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

				case "WiFi":
					methodName = "WiFi";
					switchWiFi = TestCase.StepList.get(CurrentCase).get(CurrentCaseStep + 1);
					CurrentCaseStep = CurrentCaseStep + 1;
					break;

				case "ResetAPP":
					methodName = "ResetAPP";
					break;

				case "QuitAPP":
					methodName = "QuitAPP";
					break;

				case "Customized":
					methodName = "Customized";
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
		EndAppiumSession();// 關閉Appium Session
		System.out.println("測試結束!!!" + "(" + totaltime + " s)");
		return AllResultList;
	}

	public void ErrorCheck(Object... elements) throws IOException {
		DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);

		if (elements.length > 1) {
			// APP畫面上找不到指定元件
			String APPElement = "";
			int i = 0;
			for (Object element : elements) {
				APPElement = APPElement + element;
				if (i != (elements.length - 1)) {// 判斷是否處理到最後一個element
					APPElement = APPElement + " or ";// 組成" A元件 or B元件 or
														// C元件"字串
				}
				i++;
			}
			System.err.print("[Error] Can not found " + APPElement + " on screen.");
		} else {
			for (Object element : elements) {
				if (element.equals("On")) {
					System.err.print("[Error] Can't turn on WiFi.");
				} else if (element.equals("Off")) {
					System.err.print("[Error] Can't turn off WiFi.");
				} else if (element.equals("HideKeyboard")) {
					System.err.print("[Error] Can't hide keyboard.");
				} else if (element.equals("Sleep")) {
					System.err.print("[Error] Fail to sleep.");
				} else if (element.equals("ScreenShot")) {
					System.err.print("[Error] Fail to ScreenShot.");
				} else if (element.equals("Portrait")) {
					System.err.print("[Error] Can't rotate to portrait.");
				} else if (element.equals("Landscape")) {
					System.err.print("[Error] Can't rotate to landscape.");
				} else if (element.equals("EndAppiumSession")) {
					System.err.print("[Error] Can't end session.");
				} else if (element.equals("QuitAPP")) {
					System.err.print("[Error] Can't close APP.");
				} else if (element.equals("ResetAPP")) {
					System.err.print("[Error] Can't reset APP.");
				} else if (element.equals("CeateAppiumSession")) {
					System.err.print("[Error] Can't create new session.");
				} else if (element.equals("LaunchAPP")) {
					System.err.print("[Error] Can't launch APP.");
				} else if (element.equals("BACK") || element.equals("Home") || element.equals("Power")
						|| element.equals("Recent")) {
					System.err.print("[Error] Can't execute " + element + " button.");
				} else if (element.equals("Customized")) {
					System.err.print("[Error] Can't execute Customized_Method.");
				} else {
					System.err.print("[Error] Can not found " + element + " on screen.");
				}
			}
		}
		System.err.println(" " + reportDate);
		// System.err.println("[Error] APP quit unexpectedly.");
		String FilePath = MakeErrorFolder();// 建立各案例資料夾存放log資訊及Screenshot資訊
		ErrorScreenShot(FilePath);// Screenshot Error畫面
		logcat(FilePath);// 收集閃退logcat
		CommandError = false;// 設定CommandError=false
		ResultList.add("error");
		AllResultList.add(ResultList);
	}

	public void logcat(String FilePath) throws IOException {
		// 收集閃退log
		// System.out.println("[info] Saving device log...");
		DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);

		List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
		try {
			FileWriter fw = new FileWriter(FilePath + TestCase.CaseList.get(CurrentCaseNumber).toString() + "_"
					+ reportDate + "_log" + ".txt");
			for (int i = 0; i < logEntries.size(); i++) {
				fw.write(logEntries.get(i).toString() + "\n");
			}
			fw.flush();
			fw.close();
			System.out.println("[info] Saving device log - Done.");
		} catch (Exception e) {
			System.err.println("[Error] Fail to save device log.");
		}
	}

	public void ErrorScreenShot(String FilePath) {
		try {
			System.out.println("[info] Taking a screenshot of error.");
			DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			File screenShotFile = (File) driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShotFile,
					new File(FilePath + TestCase.CaseList.get(CurrentCaseNumber) + "_" + reportDate + ".jpg"));
		} catch (IOException e) {
			System.err.println("[Error] Fail to ErrorScreenShot.");
		}
	}

	public String MakeErrorFolder() {
		// 資料夾結構 C:\TUTK_QA_TestTool\TestReport\appPackage\CaseName\DeviceUdid
		String filePath = "C:\\TUTK_QA_TestTool\\TestReport\\" + TestCase.DeviceInformation.appPackage.toString() + "\\"
				+ TestCase.CaseList.get(CurrentCase).toString() + "\\" + TestCase.DeviceInformation.deviceName.get(0)
				+ "\\";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return filePath;
	}

	public void Customized() throws IOException {
		Customized custom = new Customized(driver);
		if (!custom.Customized_Method()) {
			ErrorCheck("Customized");
		}
	}

	public void Byid_VerifyText() throws IOException {
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
			ErrorCheck(appElemnt);
		}

	}

	public void ByXpath_VerifyText() throws IOException {
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
			ErrorCheck(appElemnt);
		}

	}

	// 僅適用於能區分checked屬性的元件
	public void Byid_VerifyRadioButton() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_VerifyRadioButton|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.getAttribute("checked");
			if (element.equalsIgnoreCase(appInput)) {
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
			ErrorCheck(appElemnt);
		}
	}

	// 僅適用於能區分checked屬性的元件
	public void ByXpath_VerifyRadioButton() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_VerifyRadioButton|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)))
					.getAttribute("checked");
			if (element.equalsIgnoreCase(appInput)) {
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
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Wait() throws IOException {
		try {
			System.out.println("[info] Executing:|Byid_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Wait() throws IOException {
		try {
			System.out.println("[info] Executing:|ByXpath_Wait|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_SendKey() throws IOException {
		try {
			System.out.println("[info] Executing:|Byid_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.sendKeys(appInput);
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_SendKey() throws IOException {
		try {
			System.out.println("[info] Executing:|ByXpath_SendKey|" + appElemnt + "|" + appInput + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).sendKeys(appInput);
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Click() throws IOException {
		try {
			System.out.println("[info] Executing:|Byid_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.click();
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tutk.kalayapp:id/preview"))).click();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Click() throws IOException {
		try {
			System.out.println("[info] Executing:|ByXpath_Click|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).click();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_Clear() throws IOException {

		try {
			System.out.println("[info] Executing:|Byid_Clear|" + appElemnt + "|Clear|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)))
					.clear();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Clear() throws IOException {

		try {
			System.out.println("[info] Executing:|ByXpath_Clear|" + appElemnt + "|Clear|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt))).clear();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void HideKeyboard() throws IOException {
		try {
			if (driver.isKeyboardShown()) {
				System.out.println("[info] Executing:|HideKeyboard|");
				driver.hideKeyboard();
			}
		} catch (Exception e) {
			ErrorCheck("HideKeyboard");
		}
	}

	public void Sleep() throws IOException {
		// String NewString = "";// 新字串
		// char[] r = { '.' };// 小數點字元
		// char[] c = appInput.toCharArray();// 將字串轉成字元陣列
		// for (int i = 0; i < c.length; i++) {
		// if (c[i] != r[0]) {// 判斷字元是否為小數點
		// NewString = NewString + c[i];// 否，將字元組合成新字串
		// } else {
		// break;// 是，跳出迴圈
		// }
		// }

		try {
			System.out.println("[info] Executing:|Sleep|" + appInput + " second..." + "|");
			Thread.sleep((long) (Float.valueOf(appInput) * 1000));
			// Thread.sleep(Integer.valueOf(NewString) * 1000);// 將字串轉成整數
		} catch (Exception e) {
			ErrorCheck("Sleep");
		}
	}

	public void ScreenShot() throws IOException {

		try {
			System.out.println("[info] Executing:|ScreenShot|");
			DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date today = Calendar.getInstance().getTime();
			String reportDate = df.format(today);
			File screenShotFile = (File) driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShotFile, new File("C:\\TUTK_QA_TestTool\\TestReport\\"
					+ TestCase.CaseList.get(CurrentCaseNumber) + "_" + reportDate + ".jpg"));
		} catch (IOException e) {
			ErrorCheck("ScreenShot");
		}
	}

	public void Orientation() throws IOException {
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
				ErrorCheck("Landscape");
			} else {
				ErrorCheck("Portrait");
			}
		}
	}

	public void EndAppiumSession() throws IOException {
		try {
			System.out.println("[info] Executing:|End Session|");
			driver.quit();
		} catch (Exception e) {
			ErrorCheck("EndAppiumSession");
		}
	}

	public void QuitAPP() throws IOException {
		boolean state = false;
		try {
			System.out.println("[info] Executing:|QuitAPP|");
			driver.closeApp();
			// 確認Step中是否包含ByXpath_VerifyText或Byid_VerifyText或Byid_VerifyRadioButton或ByXpath_VerifyRadioButton
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
		} catch (Exception e) {
			ErrorCheck("QuitAPP");
		}

	}

	public void ResetAPP() throws IOException {

		try {
			System.out.println("[info] Executing:|ResetAPP|");
			driver.resetApp();
		} catch (Exception e) {
			ErrorCheck("ResetAPP");
		}
	}

	public void CeateAppiumSession() throws IOException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, device_timeout);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, TestCase.DeviceInformation.deviceName.get(0));// 固定index
																											// 0
		cap.setCapability(MobileCapabilityType.UDID, TestCase.DeviceInformation.deviceName.get(0));
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, TestCase.DeviceInformation.platformVersion.get(0));// 固定index
																													// 0
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, TestCase.DeviceInformation.appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, TestCase.DeviceInformation.appActivity);
		cap.setCapability(MobileCapabilityType.NO_RESET, TestCase.DeviceInformation.ResetAPP);
		cap.setCapability("autoLaunch", false); // 不啟動APP

		try {
			System.out.println("[info] Executing:|Create New Session|");
			System.out.println("");
			driver = new AndroidDriver<>(new URL("http://127.0.0.1:" + port + "/wd/hub"), cap);
		} catch (Exception e) {
			ErrorCheck("CeateAppiumSession");
		}
	}

	public void LaunchAPP() throws IOException {

		CurrentCaseNumber = CurrentCaseNumber + 1;
		try {
			System.out.println("[info] Executing:|LaunchAPP|" + TestCase.DeviceInformation.appPackage + "|");
			driver.launchApp();
		} catch (Exception e) {
			ErrorCheck("LaunchAPP");
		}
	}

	public void Back() throws IOException {
		try {
			System.out.println("[info] Executing:|Back|");
			driver.pressKeyCode(AndroidKeyCode.BACK);
		} catch (Exception e) {
			ErrorCheck("Back");
		}
	}

	public void Home() throws IOException {
		try {
			System.out.println("[info] Executing:|Home|");
			driver.pressKeyCode(AndroidKeyCode.HOME);
		} catch (Exception e) {
			ErrorCheck("Home");
		}
	}

	public void Power() throws IOException {
		try {
			System.out.println("[info] Executing:|Power|");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER);
		} catch (Exception e) {
			ErrorCheck("Power");
		}
	}

	public void Recent() throws IOException {
		try {
			System.out.println("[info] Executing:|Recent|");
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		} catch (Exception e) {
			ErrorCheck("Recent");
		}
	}

	public void WiFi() throws IOException {

		try {
			System.out.println("[info] Executing:|WiFi|" + switchWiFi + "|");
			// if邏輯說明:(目的避免已開啟wifi或已關閉wif了，又再次執行令啟動wifi或關閉wif(皆去除if判斷僅跑switch流程)，如此可節省測試時間)
			// [判斷手機連線狀態為WiFi off及data off(皆NONE) &&
			// Excel指令為On時，才執行switch之Case"On"]
			// ||[[判斷手機連線狀態為WiFi on || WiFi及Data都啟動(皆ALL)] &&
			// Excel指令為Off時，才執行switch之Case"Off"]

			if ((driver.getConnection().toString().equals("NONE") && switchWiFi.equals("On"))
					|| ((driver.getConnection().toString().equals("ALL")
							|| driver.getConnection().toString().equals("WIFI")) && switchWiFi.equals("Off"))) {
				switch (switchWiFi) {
				case "On":
					driver.setConnection(Connection.WIFI);
					break;
				case "Off":
					driver.setConnection(Connection.NONE);
					break;
				}
			}
		} catch (Exception e) {
			switch (switchWiFi) {
			case "On":
				ErrorCheck("On");
				break;
			case "Off":
				ErrorCheck("Off");
				break;
			}
		}

	}

	public void Byid_invisibility() throws IOException {
		try {
			System.out.println("[info] Executing:|Byid_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_invisibility() throws IOException {
		try {
			System.out.println("[info] Executing:|ByXpath_invisibility|" + appElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(appElemnt)));
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void Byid_LongPress() throws IOException {
		try {
			System.out.println("[info] Executing:|Byid_LongPress|" + appElemnt + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			t.longPress(wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt))))
					.perform();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_LongPress() throws IOException {
		try {
			System.out.println("[info] Executing:|ByXpath_LongPress|" + appElemnt + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			t.longPress(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)))).perform();
		} catch (Exception e) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Swipe() throws IOException {
		Point p1, p2;// p1 為起點;p2為終點

		try {
			System.out.println("[info] Executing:|ByXpath_Swipe|" + appElemnt + "|" + toElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			TouchAction t = new TouchAction(driver);
			WebElement ele2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toElemnt)));
			WebElement ele1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));
			t.press(ele1).waitAction(WaitOptions.waitOptions(ofSeconds(1))).moveTo(ele2).release().perform();

		} catch (Exception e) {
			ErrorCheck(appElemnt, toElemnt);
		}
	}

	public void Byid_Swipe() throws IOException {
		Point p1, p2;// p1 為起點;p2為終點

		try {
			System.out.println("[info] Executing:|Byid_Swipe|" + appElemnt + "|" + toElemnt + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			TouchAction t = new TouchAction(driver);
			WebElement ele2 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + toElemnt)));
			WebElement ele1 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id(TestCase.DeviceInformation.appPackage + ":id/" + appElemnt)));
			t.press(ele1).waitAction(WaitOptions.waitOptions(ofSeconds(1))).moveTo(ele2).release().perform();
		} catch (Exception e) {
			ErrorCheck(appElemnt, toElemnt);
		}
	}

	public void Swipe() throws IOException {

		for (int j = 0; j < iterative; j++) {
			try {
				System.out.println(
						"[info] Executing:|Swipe|(" + startx + "," + starty + ")|(" + endx + "," + endy + ")|");
				TouchAction t = new TouchAction(driver);
				t.press(startx, starty).waitAction(WaitOptions.waitOptions(ofSeconds(1))).moveTo(endx, endy).release()
						.perform();
			} catch (Exception e) {
				ErrorCheck(startx, starty, endx, endy);
				break;// 出錯後，離開iterative回圈
			}
		}
	}

	public void ByXpath_Swipe_Vertical() throws IOException {
		Point p;// 元件座標
		Dimension s;// 元件大小
		WebElement e;

		try {
			System.out.println(
					"[info] Executing:|ByXpath_Swipe_Vertical|" + appElemnt + "|" + scroll + "|" + iterative + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			//
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));
			// e =
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appElemnt)));
			s = e.getSize();
			p = e.getLocation();
			int errorX = (int) Math.round(s.width * 0.01);
			int errorY = (int) Math.round(s.height * 0.01);
			for (int j = 0; j < iterative; j++) {

				if (scroll.equals("DOWN")) {// 畫面向下捲動
					t.press(p.x + errorX, p.y + s.height - errorY).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
							.moveTo(p.x + errorX, p.y + errorY).release().perform();

				} else if (scroll.equals("UP")) {// 畫面向上捲動
					t.press(p.x + errorX, p.y + errorY).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
							.moveTo(p.x + errorX, p.y + s.height - errorY).release().perform();
				}
			}

		} catch (Exception w) {
			ErrorCheck(appElemnt);
		}
	}

	public void ByXpath_Swipe_Horizontal() throws IOException {
		Point p;// 元件座標
		Dimension s;// 元件大小
		WebElement e;

		try {
			System.out.println(
					"[info] Executing:|ByXpath_Swipe_Horizontal|" + appElemnt + "|" + scroll + "|" + iterative + "|");
			TouchAction t = new TouchAction(driver);
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appElemnt)));
			s = e.getSize();
			p = e.getLocation();
			int errorX = (int) Math.round(s.getWidth() * 0.01);
			int errorY = (int) Math.round(s.getHeight() * 0.01);
			for (int j = 0; j < iterative; j++) {

				if (scroll.equals("RIGHT")) {// 畫面向右捲動 (觀看畫面左方內容)
					t.press(p.x + errorX, p.y + errorY).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
							.moveTo(p.x + s.width - errorX, p.y + errorY).release().perform();
				} else if (scroll.equals("LEFT")) {// 畫面向左捲動 (觀看畫面右方內容)
					t.press(p.x + s.width - errorX, p.y + errorY).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
							.moveTo(p.x + errorX, p.y + errorY).release().perform();
				}
			}
		} catch (Exception w) {
			ErrorCheck(appElemnt);
		}
	}

	// ByXpath_Swipe_FindText_Click_Android缺點：1.搜尋的字串不可重複 2.搜尋5次都沒找到元件，則停止搜尋
	public void ByXpath_Swipe_FindText_Click_Android() throws IOException {

		int SearchNumber = 0;// 搜尋次數
		Point ScrollBarP;// 卷軸元件座標
		Dimension ScrollBarS;// 卷軸元件之長及寬
		WebElement ScrollBar;// 卷軸元件

		try {
			System.out.println("[info] Executing:|ByXpath_Swipe_FindText_Click_Android|" + appElemnt + "|" + scroll
					+ "|" + appElemntarray + "|" + appInput + "|" + appInputXpath + "|");
			WebDriverWait wait = new WebDriverWait(driver, command_timeout);
			TouchAction t = new TouchAction(driver);
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
							t.press(targetElementP.x, ScrollBarS.height + ScrollBarP.y - errorY)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(targetElementP.x, ScrollBarP.y + errorY).release().perform();

						} else if (targetElementP.y + targetElementS.height == ScrollBarP.y + ScrollBarS.height) {// 若搜尋元件的y座標與寬度總和等於卷軸長度，表示搜尋元件的部分UI被卷軸遮住

							t.press(targetElementP.x - errorY, targetElementP.y)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(targetElementP.x, ScrollBarP.y + errorY).release().perform();

						}
						break;

					case "UP":
						if (targetElementP.y + targetElementS.height < ScrollBarP.y) {// 若搜尋元件的最大y座標小於卷軸y座標，表示搜尋元件全部UI被卷軸遮住
							t.press(targetElementP.x, ScrollBarP.y + errorY)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(targetElementP.x, ScrollBarS.height + ScrollBarP.y - errorY).release()
									.perform();

						} else {// 反之，若搜尋元件的最大y座標大於卷軸y座標，表示搜尋元件全部UI被卷軸遮住
							t.press(targetElementP.x, ScrollBarP.y + errorY)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(targetElementP.x, ScrollBarP.y + ScrollBarS.height - errorY).release()
									.perform();

						}
						break;

					case "LEFT":// 畫面向左捲動(觀看畫面右方內容)
						if (targetElementP.x > ScrollBarP.x + ScrollBarS.width) {// 若搜尋元件的x座標大於卷軸範圍，表示搜尋元件全部UI被卷軸遮住
							t.press(ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(ScrollBarP.x + errorX, targetElementP.y).release().perform();

						} else if (targetElementP.x + targetElementS.width == ScrollBarP.x + ScrollBarS.width) {// 若搜尋元件的x座標與寬度總和等於卷軸寬度，表示搜尋元件的部分UI被卷軸遮住
							t.press(targetElementP.x - errorX, targetElementP.y)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(ScrollBarP.x + errorX, targetElementP.y).release().perform();

						}
						break;

					case "RIGHT":// 畫面向右捲動(觀看畫面左方內容)
						if (targetElementP.x + targetElementS.width < ScrollBarP.x) {// 若搜尋元件的最大x座標小於卷軸x座標，表示搜尋元件全部UI被卷軸遮住
							t.press(ScrollBarP.x + errorX, targetElementP.y)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y).release()
									.perform();
						} else if (targetElementP.x == ScrollBarP.x) {// 若搜尋元件的x座標等於卷軸x座標，可能表示搜尋元件的部分UI被卷軸遮住
							t.press(targetElementP.x + targetElementS.width + errorX, targetElementP.y)
									.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
									.moveTo(ScrollBarP.x + ScrollBarS.width - errorX, targetElementP.y).release()
									.perform();
						}
						break;
					}

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(appInputXpath))).click();
					break;
				}

				if (i == targetlist.size() - 1) {// 若targetlist中最後一筆資料比對完後，則進行Srcoll拖曳

					switch (scroll.toString()) {

					case "DOWN":
						t.press(ScrollBarP.x + errorX, ScrollBarP.y + ScrollBarS.height - errorY)
								.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
								.moveTo(ScrollBarP.x + errorX, ScrollBarP.y + errorY).release().perform();// 向下捲動
						break;

					case "UP":
						t.press(ScrollBarP.x + errorX, ScrollBarP.y + errorY)
								.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
								.moveTo(ScrollBarP.x + errorX, ScrollBarP.y + ScrollBarS.height - errorY).release()
								.perform();// 向上捲動
						break;

					case "LEFT":
						t.press(ScrollBarP.x + ScrollBarS.width - errorX, ScrollBarP.y + errorY)
								.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
								.moveTo(ScrollBarP.x + errorX, ScrollBarP.y + errorY).release().perform();// 畫面向左捲動(觀看畫面右方內容)
						break;

					case "RIGHT":
						t.press(ScrollBarP.x + errorX, ScrollBarP.y + errorY)
								.waitAction(WaitOptions.waitOptions(ofSeconds(1)))
								.moveTo(ScrollBarP.x + ScrollBarS.width - errorX, ScrollBarP.y + errorY).release()
								.perform();// 畫面向右捲動(觀看畫面左方內容)
						break;

					}
					SearchNumber++;// 累計搜尋次數
					targetlist.clear();// 清除targetlist
					targetlist = wait
							.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(appElemntarray)));// 再次取得新targetlist

					if (SearchNumber == 10) {// 搜尋10次都沒找到元件，則跳出for
						System.out.println("[Error] Search 10 time can not find " + appInput);// 印出找不到
						break;// 跳出for
					} else {
						i = -1;// 若SearchNumber!=10，則令i=-1(目的：再次執行for)
					}
				}
			}

		} catch (Exception w) {
			ErrorCheck(appElemnt, appInput, appElemntarray, appInputXpath);
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
