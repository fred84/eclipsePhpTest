package org.fred.plugin.php.test.domain;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class XmlReportParser implements IReportParser {

	@Override
	public List<TestCase> parse(String xml) {
		// TODO Auto-generated method stub

		try {
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xml));
	        
	        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

	        NodeList suites = doc.getElementsByTagName("testsuite");
	        
	        for(int i = 0; i < suites.getLength(); i++) {
	        	TestSuite suite = new TestSuite(
	        		suites.item(i).getAttributes().getNamedItem("class").toString(),
	        		suites.item(i).getAttributes().getNamedItem("file").toString()
	        	);
	        	
	        	
	        			
	        }
	        
			return new ArrayList<TestCase>();
	/*
			doc.getE
			
			Node n = doc.getDocumentElement().getFirstChild();
			while (n != null && !n.getNodeName().equals("recipe"))
				n = n.getNextSibling();
			PrintStream out = System.out;
			out.println("<?xml version=\"1.0\"?>");
			out.println("<collection>");
			if (n != null)
				print(n, out);
			out.println("</collection>");*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
