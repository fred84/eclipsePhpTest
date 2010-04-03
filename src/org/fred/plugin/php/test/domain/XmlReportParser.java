package org.fred.plugin.php.test.domain;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XmlReportParser implements IReportParser {

	@Override
	public List<TestSuite> parse(String xml) throws SAXException, IOException, ParserConfigurationException {
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        NodeList suitesNodes = doc.getElementsByTagName("testsuite");
        
        List<TestSuite> suites =  new ArrayList<TestSuite>();
        
        for(int i = 0; i < suitesNodes.getLength(); i++) {
        	TestSuite suite = new TestSuite(
        		suitesNodes.item(i).getAttributes().getNamedItem("name").getNodeValue(),
        		suitesNodes.item(i).getAttributes().getNamedItem("file").getNodeValue()
        	);
        	
        	suites.add(suite);
        	
        	NodeList cases = suitesNodes.item(i).getChildNodes();
        	
        	for(int j = 0; j < cases.getLength(); j++) {
        		if (cases.item(j).hasChildNodes()) {
        			TestCase testCase = new FailedTestCase(
        				cases.item(j).getAttributes().getNamedItem("name").getNodeValue(), 
        				cases.item(j).getAttributes().getNamedItem("file").getNodeValue(), 
        				Integer.parseInt(cases.item(j).getAttributes().getNamedItem("line").getNodeValue()), 
        				cases.item(j).getFirstChild().getTextContent()
        			);
        			suite.addCase(testCase);
        		} else {
	        		TestCase testCase = new TestCase(
	        			cases.item(j).getAttributes().getNamedItem("name").getNodeValue(), 
	        			cases.item(j).getAttributes().getNamedItem("file").getNodeValue(), 
	        			Integer.parseInt(cases.item(j).getAttributes().getNamedItem("line").getNodeValue())
	        		);
	        		suite.addCase(testCase);
        		}
        	}	
        }
		
		return suites;		
	}
}
