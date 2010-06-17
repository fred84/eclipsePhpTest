package me.galkin.eclipse.php.domain;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XmlReportParser implements IReportParser {

	@Override
	public TestSuites parse(String xml) throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException, XPathExpressionException {
	    XPathExpression expr = XPathFactory
	    	.newInstance()
	    	.newXPath()
	    	.compile("//testsuite[@file]");
	    
	    NodeList suitesNodes = (NodeList)expr.evaluate(getDocument(xml), XPathConstants.NODESET);
		
	    TestSuites suites = new TestSuites();
		
		for (int i = 0; i < suitesNodes.getLength(); i++) {
			TestSuite suite = createTestSuite(suitesNodes.item(i));
			suites.add(suite);

			NodeList cases = suitesNodes.item(i).getChildNodes();

			for (int j = 0; j < cases.getLength(); j++) {
				if (cases.item(j).getNodeName().equals("testcase")) {
					suite.add(createTestCase(cases.item(j)));
				}
			}
		}

		return suites;
	}

	private Document getDocument(String xml) throws SAXException, IOException,
			ParserConfigurationException, ResultsNotFoundException {
		assertReportCorrect(xml);

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));

		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
				is);
	}

	private void assertReportCorrect(String xml)
			throws ResultsNotFoundException {
		xml = xml.trim();

		if (!xml.startsWith("<?xml")) {
			throw new ResultsNotFoundException("incorrect xml: " + xml.trim());
		}
	}

	private TestSuite createTestSuite(Node node) {
		if (null == node.getAttributes().getNamedItem("file")) {
			return new TestSuite(
				node.getAttributes().getNamedItem("name").getNodeValue()
			);
		}
		
		return new TestSuite(
        	node.getAttributes().getNamedItem("name").getNodeValue(),
        	node.getAttributes().getNamedItem("file").getNodeValue()
       	);
	}

	private TestCase createTestCase(Node node) throws NumberFormatException, DOMException {
		String failure = getFailureText(node);
		
		if (null != failure) {
			return new FailedTestCase(
				node.getAttributes().getNamedItem("name").getNodeValue(), 
				Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue()), 
				failure
			);
		}
		
		String error = getErrorText(node);
		
		if (null != error) {
			return new ErrorTestCase(
					node.getAttributes().getNamedItem("name").getNodeValue(), 
					Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue()), 
					error
				);
		}
		
		return new TestCase(
			node.getAttributes().getNamedItem("name").getNodeValue(), 
			Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue())
		);
		
	}

	private String getFailureText(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		
		NodeList nodes = node.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeName().equals("failure")) {
				return nodes.item(i).getTextContent();
			}
		}
		
		return null;
	}
	
	private String getErrorText(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		
		NodeList nodes = node.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeName().equals("error")) {
				return nodes.item(i).getTextContent();
			}
		}
		
		return null;
	}
}