package Auto;

import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadDeviceInformation {

	public String deviceName;// �s����UDID
	public String platformVersion;// �s����OS����
	public ArrayList<String> ScriptList = new ArrayList<String>();// �s��ݰ��檺�}���W��
	public ArrayList<String> CasetList = new ArrayList<String>();// �s����w���ݰ���
	public String appPackage;// APP Packagename
	public String appActivity;// APP Activity
	public boolean UIAutomator2;
	public Boolean ResetAPP; // �C����@Case����e�A�O�_�M��APP�֨����;�O��true;�_��false

	public LoadDeviceInformation() {
		XSSFWorkbook workBook;
		XSSFSheet Sheet;

		try {

			workBook = new XSSFWorkbook(new FileInputStream("C:\\TUTK_QA_TestTool\\TestTool\\TestScript.xlsm"));// hard
			// code
			Sheet = workBook.getSheet("APP&Device");// hard code
			ScriptList = new ArrayList<String>();
			CasetList = new ArrayList<String>();

			appPackage = Sheet.getRow(1).getCell(0).toString();// hard code
			appActivity = Sheet.getRow(1).getCell(1).toString();// hard code

			if (Sheet.getRow(1).getCell(6).toString() == "TRUE") {
				ResetAPP = false;
			} else {
				ResetAPP = true;
			}

			int m = 1;
			try {

				do {
					// �NOS Version�ন�r���x�}
					char[] c = Sheet.getRow(m).getCell(3).toString().toCharArray();
					// �P�_c[1]�O�_���r��.
					if (c[1] != '.') {
						UIAutomator2 = true;// True���ե�UIAutomator2
					} else if (c[1] == '.') {
						// ���o�Ĥ@�Ӧr��
						int num = Character.getNumericValue(c[0]);
						// �P�_�O�_�j��7
						if (num >= 7) {
							UIAutomator2 = true;// True���ե�UIAutomator2
						} else if (num < 7) {
							UIAutomator2 = false;// True�����ե�UIAutomator2
						}
					}

					m++;
				} while (!Sheet.getRow(m).getCell(3).toString().equals(""));

			} catch (Exception e) {
				;
			}

			deviceName = Sheet.getRow(1).getCell(2).toString();// ���UDID
			platformVersion = Sheet.getRow(1).getCell(3).toString();// ���OS����

			int k = 1;
			try {
				do {
					ScriptList.add(Sheet.getRow(k).getCell(4).toString());// �ǳƭnRun���}��
					k++;
				} while (!Sheet.getRow(k).getCell(4).toString().equals(""));
			} catch (Exception e) {
				;
			}

			System.out.println("���ճ]��(UDID/Android Version)");

			System.out.print(deviceName + "/");
			System.out.println(platformVersion);

			workBook.close();

		} catch (Exception e) {
			System.out.println("Can not found C:\\TUTK_QA_TestTool\\TestTool\\TestScript.xlsm");
		}
	}

}
