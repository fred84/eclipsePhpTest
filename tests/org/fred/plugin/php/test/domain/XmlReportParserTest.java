package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class XmlReportParserTest {

	@Test
	public void parse() throws SAXException, IOException, ParserConfigurationException {
		String xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<testsuites>" +
			"<testsuite name=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" tests=\"2\" assertions=\"2\" failures=\"1\" errors=\"0\" time=\"0.001648\">" +
			"<testcase name=\"testWillPass\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"4\" assertions=\"1\" time=\"0.000827\"/>" +
			"<testcase name=\"testWillFail\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"8\" assertions=\"1\" time=\"0.000821\">" +
			"<failure type=\"PHPUnit_Framework_ExpectationFailedException\">MyTestCase::testWillFail</failure>" +
			"</testcase>" +
			"</testsuite>" +
			"</testsuites>";

		
		TestSuite suite = new TestSuite("MyTestCase", "/test3/test/MyTestCase.php");
		suite.addCase(new TestCase("testWillPass", 4));
		suite.addCase(new FailedTestCase("testWillFail", 8, "MyTestCase::testWillFail"));	
		
		XmlReportParser parser = new XmlReportParser();
		
		List<TestSuite> expected = new ArrayList<TestSuite>();
		expected.add(suite);
		
		assertEquals(expected, parser.parse(xml));
	}
	
}