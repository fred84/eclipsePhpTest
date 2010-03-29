package org.fred.plugin.php.test.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class XmlReportParser implements IReportParser {

	@Override
	public List<TestCase> parse(String xml) {
		// TODO Auto-generated method stub

		try {
			DOMParser parser = new DOMParser();
			parser.parse(xml); //org.xml.sax.SAXException, java.io.IOException;
			Document doc = parser.getDocument();
			
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
