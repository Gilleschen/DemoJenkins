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

	ArrayList<ArrayList<String>> StepList = new ArrayList<ArrayList<String>>();// �Ҧ����ծרҪ��ʧ@�M��(2���}�C)
	ArrayList<String> StepListData = new ArrayList<String>();// ��@���ծרҪ��ʧ@�M��
	ArrayList<String> CaseList = new ArrayList<String>();// �Ҧ����ծרҪ��W�ٲM��
	

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

					CaseList.add(eElement.getElementsByTagName("testSummary").item(0).getTextContent());// �T�witem(0)

					for (int j = 0; j < eElement.getElementsByTagName("step").getLength(); j++) {
						StepListData.add(eElement.getElementsByTagName("step").item(j).getTextContent());
						if (!eElement.getElementsByTagName("testData").item(j).getTextContent().equals("")) {

							String testData = eElement.getElementsByTagName("testData").item(j).getTextContent();
							testData = testData.replaceAll("\\s+", "");// �����r�ꤺ�Ҧ��Ů�
							String[] testDataList = testData.split(",");// �N���ծרҥ[�JTargetCaseList�x�}
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
