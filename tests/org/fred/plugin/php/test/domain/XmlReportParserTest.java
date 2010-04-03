package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XmlReportParserTest {

	private File resultFile;
	
	@Before
	public void createFile() throws IOException {
		resultFile = File.createTempFile("test", "xml");
	}
	
	@After
	public void deleteFile() {
		resultFile.delete();
	}
	
	@Test
	public void parse() throws SAXException, IOException, ParserConfigurationException {
		// fixture
		TestSuite suite = new TestSuite("MyTestCase", "/test3/test/MyTestCase.php");
		suite.addCase(new TestCase("testWillPass", 4));
		suite.addCase(new FailedTestCase("testWillFail", 8, "MyTestCase::testWillFail"));	
		List<TestSuite> expected = new ArrayList<TestSuite>();
		expected.add(suite);
		
		writeTestResultsToFile(getTestResult());
		// verify
		assertEquals(expected, new XmlReportParser().parse(resultFile));
	}
	
	public void parseInvalidFormatXml() throws SAXException, IOException, ParserConfigurationException {
		// fixture
		writeTestResultsToFile(getInvalidFormatXml());
		// verify
		new XmlReportParser().parse(resultFile);
	}
	
	public void parseMalformedXml() throws SAXException, IOException, ParserConfigurationException {
		// fixture
		writeTestResultsToFile(getMalformedXml());
		// verify
		new XmlReportParser().parse(resultFile);
	}
	
	private void writeTestResultsToFile(String result) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(resultFile));
	    out.write(result);
	    out.close();
	}
	
	private String getTestResult() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<testsuites>" +
		"<testsuite name=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" tests=\"2\" assertions=\"2\" failures=\"1\" errors=\"0\" time=\"0.001648\">" +
		"<testcase name=\"testWillPass\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"4\" assertions=\"1\" time=\"0.000827\"/>" +
		"<testcase name=\"testWillFail\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"8\" assertions=\"1\" time=\"0.000821\">" +
		"<failure type=\"PHPUnit_Framework_ExpectationFailedException\">MyTestCase::testWillFail</failure>" +
		"</testcase>" +
		"</testsuite>" +
		"</testsuites>";
	}
	
	private String getMalformedXml() {
		return "nonXml";
	}
	
	private String getInvalidFormatXml() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result></result>";
	}	
	
}