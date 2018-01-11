package Auto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;

//hard code部分勿動
public class Customized {
	AndroidDriver driver;// hard code
	WebDriverWait wait;// hard code
	boolean CommandError;// hard code

	public Customized(AndroidDriver driver) {
		this.driver = driver;// hard code
	}

	public boolean Customized_Method() {

		try {
			System.out.println("[info] Executing:|Customized_Method|");// hard
																		// code

			/* 以下請撰寫客製化程式 */

			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tutk.kalayapp:id/iv_right"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tutk.kalayapp:id/item_change_pwd"))).click();;

			/* 以上請撰寫客製化程式 */
			CommandError = true;

		} catch (Exception e) {
			System.err.println("[Error] Can't Execute Customized_Method ");// hard
																			// code
			CommandError = false;// hard code 儲存第i台設備command失敗結果
		}
		return CommandError;// hard code
	}
}
