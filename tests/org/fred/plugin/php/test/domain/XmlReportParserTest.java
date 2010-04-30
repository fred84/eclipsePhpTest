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
	public void parse() throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException {
		// fixture
		TestSuite suite = new TestSuite("MyTestCase", "/test3/test/MyTestCase.php");
		suite.addCase(new TestCase("testWillPass", 4));
		suite.addCase(new FailedTestCase("testWillFail", 8, "MyTestCase::testWillFail"));	
		List<TestSuite> expected = new ArrayList<TestSuite>();
		expected.add(suite);
		// verify
		assertEquals(expected, new XmlReportParser().parse(getTestResult()));
	}

	@Test(expected = ResultsNotFoundException.class)
	public void parseInvalidFormatXml() throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException {
		new XmlReportParser().parse(getInvalidFormatXml());
	}
	
	@Test(expected = ResultsNotFoundException.class)
	public void parseMalformedXml() throws SAXException, IOException, ParserConfigurationException, ResultsNotFoundException {
		new XmlReportParser().parse(getMalformedXml());
	}
	
	private String getTestResult() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"\n <testsuites>" +
		"\n <testsuite name=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" tests=\"2\" assertions=\"2\" failures=\"1\" errors=\"0\" time=\"0.001648\">" +
		"\n <testcase name=\"testWillPass\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"4\" assertions=\"1\" time=\"0.000827\"/>" +
		"\n <testcase name=\"testWillFail\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"8\" assertions=\"1\" time=\"0.000821\">" +
		"\n <failure type=\"PHPUnit_Framework_ExpectationFailedException\">MyTestCase::testWillFail</failure>" +
		"\n </testcase>" +
		"\n </testsuite>" +
		"\n </testsuites>";
	}
	
	private String getMalformedXml() {
		return "nonXml";
	}
	
	private String getInvalidFormatXml() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result></result>";
	}		
}