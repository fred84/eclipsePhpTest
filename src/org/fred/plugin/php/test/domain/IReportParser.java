package org.fred.plugin.php.test.domain;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IReportParser {

	public List<TestSuite> parse(String xml) throws SAXException, IOException, ParserConfigurationException;
	
}
