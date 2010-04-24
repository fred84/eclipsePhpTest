package org.fred.plugin.php.test.domain;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

interface IReportParser {
	
	List<TestSuite> parse(String xml) throws SAXException, IOException, ParserConfigurationException;

}