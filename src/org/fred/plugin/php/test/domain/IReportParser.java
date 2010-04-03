package org.fred.plugin.php.test.domain;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface IReportParser {

	public List<TestSuite> parse(File resultFile) throws SAXException, IOException, ParserConfigurationException;
	
}
