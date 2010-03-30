package org.fred.plugin.php.test.domain;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlReportParser implements IReportParser {

	@Override
	public List<TestSuite> parse(String xml) throws SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub

	
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        NodeList suitesNodes = doc.getElementsByTagName("testsuite");
        
        List<TestSuite> suites =  new ArrayList<TestSuite>();
        
        for(int i = 0; i < suitesNodes.getLength(); i++) {
        	TestSuite suite = new TestSuite(
        		suitesNodes.item(i).getAttributes().getNamedItem("name").toString(),
        		suitesNodes.item(i).getAttributes().getNamedItem("file").toString()
        	);
        	
        	suites.add(suite);
        	
        	NodeList cases = suitesNodes.item(i).getChildNodes();
        	
        	for(int j = 0; j < cases.getLength(); j++) {
        		if (cases.item(j).hasChildNodes()) {
        			TestCase testCase = new FailedTestCase(
        				cases.item(j).getAttributes().getNamedItem("name").toString(), 
        				cases.item(j).getAttributes().getNamedItem("class").toString(), 
        				cases.item(j).getAttributes().getNamedItem("file").toString(), 
        				Integer.parseInt(cases.item(j).getAttributes().getNamedItem("line").toString()), 
        				cases.item(j).getFirstChild().toString()
        			);
        			suite.addCase(testCase);
        		} else {
	        		TestCase testCase = new TestCase(
	        			cases.item(j).getAttributes().getNamedItem("name").toString(), 
	        			cases.item(j).getAttributes().getNamedItem("class").toString(), 
	        			cases.item(j).getAttributes().getNamedItem("file").toString(), 
	        			Integer.parseInt(cases.item(j).getAttributes().getNamedItem("line").toString())
	        		);
	        		suite.addCase(testCase);
        		}
        	}	
        }
		
		return suites;		
	}
}
