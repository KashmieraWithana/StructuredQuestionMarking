package datasetparser.parsers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import datasetparser.models.SentenceEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocsentParser {

	private final String sentenceXPath = "/DOCSENT/BODY/TEXT/S | /DOCSENT/BODY/HEADLINE/S";
	private final String dIDXPath = "/DOCSENT";

	/**
	 * Parses all the files in the given folder returns all sentence entries
	 * including the HEADLINE sentence
	 *
	 * @param folderName
	 * @return arraylist of sentence entries
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws XPathExpressionException
	 */
	public ArrayList<SentenceEntry> parseFolder(String folderName)
			throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

		String folderPath = new File("").getAbsolutePath();
		folderPath += "/src/main/resources/docsent/" + folderName;

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		XPath xPath = XPathFactory.newInstance().newXPath();

		File[] files = new File(folderPath).listFiles();

		ArrayList<SentenceEntry> sentenceEntries = new ArrayList<SentenceEntry>();
		for (File file : files) {

			Document xmlDocument = builder.parse(file);

			Node docsentNode = (Node)xPath.compile(dIDXPath).evaluate(xmlDocument, XPathConstants.NODE);
			String dID =((Element)docsentNode).getAttribute("DID");

			NodeList nodeList = (NodeList)xPath.compile(sentenceXPath).evaluate(xmlDocument, XPathConstants.NODESET);

			SentenceEntry sentenceEntry = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				sentenceEntry = new SentenceEntry();
				sentenceEntry.setSentence(element.getFirstChild().getNodeValue());
				sentenceEntry.setSentenceNo(Integer.parseInt(element.getAttribute("SNO")));
				sentenceEntry.setDocumentId(dID);
				sentenceEntry.setSource(folderName);
				sentenceEntries.add(sentenceEntry);
			}
		}

		return sentenceEntries;
	}

}
