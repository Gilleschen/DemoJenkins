package Auto;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;

//hard code�����Ű�
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

			/* �H�U�м��g�Ȼs�Ƶ{�� */

			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tutk.kalayapp:id/iv_right"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tutk.kalayapp:id/item_change_pwd"))).click();;

			/* �H�W�м��g�Ȼs�Ƶ{�� */
			CommandError = true;

		} catch (Exception e) {
			System.err.println("[Error] Can't Execute Customized_Method ");// hard
																			// code
			CommandError = false;// hard code �x�s��i�x�]��command���ѵ��G
		}
		return CommandError;// hard code
	}
}
