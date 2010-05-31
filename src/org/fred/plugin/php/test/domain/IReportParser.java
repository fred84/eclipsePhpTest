package org.fred.plugin.php.test.domain;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

interface IReportParser {
	
	TestSuites parse(String xml) throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException;

}