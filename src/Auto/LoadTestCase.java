package Auto;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadTestCase {

	ArrayList<ArrayList<String>> StepList = new ArrayList<ArrayList<String>>();// 所有測試案例的動作清單(2維陣列)
	ArrayList<String> StepListData = new ArrayList<String>();// 單一測試案例的動作清單
	ArrayList<String> CaseList = new ArrayList<String>();// 所有測試案例的名稱清單
	

	public LoadTestCase() {

		try {
			File fXmlFile = new File("ZFJ.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("execution");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				StepListData = new ArrayList<String>();
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					CaseList.add(eElement.getElementsByTagName("testSummary").item(0).getTextContent());// 固定item(0)

					for (int j = 0; j < eElement.getElementsByTagName("step").getLength(); j++) {
						StepListData.add(eElement.getElementsByTagName("step").item(j).getTextContent());
						if (!eElement.getElementsByTagName("testData").item(j).getTextContent().equals("")) {

							String testData = eElement.getElementsByTagName("testData").item(j).getTextContent();
							testData = testData.replaceAll("\\s+", "");// 移除字串內所有空格
							String[] testDataList = testData.split(",");// 將測試案例加入TargetCaseList矩陣
							for (int k = 0; k < testDataList.length; k++) {
								StepListData.add(testDataList[k]);
							}
						}
					}
					StepList.add(StepListData);
				}

			}
		} catch (Exception e) {
			;
		}
		 //System.out.println(StepList);
		 //System.out.println(CaseList);

	}

	public static void main(String[] args) {
	}

}
