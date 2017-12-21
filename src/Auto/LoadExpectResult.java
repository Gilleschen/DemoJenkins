package Auto;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadExpectResult {

	ArrayList<String> ResultList = new ArrayList<String>();// Expected
	// Result清單

	public void LoadExpectResult(String casename) {

		try {
			File fXmlFile = new File("ZFJ.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("execution");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					if (eElement.getElementsByTagName("testSummary").item(0).getTextContent().equals(casename)) {
						for (int j = 0; j < eElement.getElementsByTagName("teststep").getLength(); j++) {
							if (!eElement.getElementsByTagName("expectedResult").item(j).getTextContent().equals("")) {
								String testData = eElement.getElementsByTagName("expectedResult").item(j)
										.getTextContent();
								testData = testData.replaceAll("\\s+", "");// 移除字串內所有空格
								String[] expectedResult = testData.split(",");// 將測試案例加入TargetCaseList矩陣
								for (int k = 0; k < expectedResult.length; k++) {
									ResultList.add(expectedResult[k]);
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			;
		}
	}

}
