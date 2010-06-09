package me.galkin.eclipse.php.domain;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

interface IReportParser {
	
	TestSuites parse(String xml) throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException, XPathExpressionException;

}