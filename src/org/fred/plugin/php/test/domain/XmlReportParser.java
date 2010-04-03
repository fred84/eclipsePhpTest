package org.fred.plugin.php.test.domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class XmlReportParser implements IReportParser {

	@Override
	public List<TestSuite> parse(File resultFile) throws SAXException, IOException, ParserConfigurationException {
        NodeList suitesNodes = getDocument(resultFile).getElementsByTagName("testsuite");
 
        List<TestSuite> suites =  new ArrayList<TestSuite>();  
        
        for(int i = 0; i < suitesNodes.getLength(); i++) {
        	TestSuite suite = createTestSuite(suitesNodes.item(i));
        	suites.add(suite);
        	
        	NodeList cases = suitesNodes.item(i).getChildNodes();
        	
        	for(int j = 0; j < cases.getLength(); j++) {
        		suite.addCase(createTestCase(cases.item(j)));
        	}	
        }
		
		return suites;		
	}

	private Document getDocument(File resultFile) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resultFile);
	}
	
	private TestSuite createTestSuite(Node node) {
		return new TestSuite(
        	node.getAttributes().getNamedItem("name").getNodeValue(),
        	node.getAttributes().getNamedItem("file").getNodeValue()
       	);
	}
	
	private TestCase createTestCase(Node node) {
		if (node.hasChildNodes()) {
			return new FailedTestCase(
				node.getAttributes().getNamedItem("name").getNodeValue(),
				Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue()), 
				node.getFirstChild().getTextContent()
			);
		} else {
    		return new TestCase(
    			node.getAttributes().getNamedItem("name").getNodeValue(),
    			Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue())
    		);
		}
	}
}
