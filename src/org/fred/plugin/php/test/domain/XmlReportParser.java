package org.fred.plugin.php.test.domain;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XmlReportParser implements IReportParser {

	@Override
	public List<TestSuite> parse(String xml) throws SAXException, IOException,
			ParserConfigurationException, ResultsNotFoundException {
		NodeList suitesNodes = getDocument(xml).getElementsByTagName(
				"testsuite");

		if (0 == suitesNodes.getLength()) {
			throw new ResultsNotFoundException("no suites found in " + xml);
		}

		List<TestSuite> suites = new ArrayList<TestSuite>();

		for (int i = 0; i < suitesNodes.getLength(); i++) {
			TestSuite suite = createTestSuite(suitesNodes.item(i));
			suites.add(suite);

			NodeList cases = suitesNodes.item(i).getChildNodes();

			for (int j = 0; j < cases.getLength(); j++) {
				if (cases.item(j).getNodeName().equals("testcase")) {
					suite.addCase(createTestCase(cases.item(j)));
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

	private TestCase createTestCase(Node node) throws NumberFormatException,
			DOMException, ResultsNotFoundException {
		if (node.hasChildNodes()) {
			return new FailedTestCase(node.getAttributes().getNamedItem("name")
					.getNodeValue(), Integer.parseInt(node.getAttributes()
					.getNamedItem("line").getNodeValue()), getFailureText(node));
		} else {
			return new TestCase(node.getAttributes().getNamedItem("name")
					.getNodeValue(), Integer.parseInt(node.getAttributes()
					.getNamedItem("line").getNodeValue()));
		}
	}

	private String getFailureText(Node node) throws ResultsNotFoundException {
		NodeList nodes = node.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeName().equals("failure")) {
				return nodes.item(i).getTextContent();
			}
		}

		throw new ResultsNotFoundException("failure node not found");
	}
}