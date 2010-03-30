package org.fred.plugin.php.test.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class XmlReportParserTest {

	@Test
	public void parse() {
		String xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<testsuites>" +
			"<testsuite name=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" tests=\"2\" assertions=\"2\" failures=\"1\" errors=\"0\" time=\"0.001648\">" +
			"<testcase name=\"testWillPass\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"4\" assertions=\"1\" time=\"0.000827\"/>" +
			"<testcase name=\"testWillFail\" class=\"MyTestCase\" file=\"/test3/test/MyTestCase.php\" line=\"8\" assertions=\"1\" time=\"0.000821\">" +
			"<failure type=\"PHPUnit_Framework_ExpectationFailedException\">MyTestCase::testWillFail" +
			"should fail" +
			"Failed asserting that &lt;boolean:false&gt; is true." +
			"/home/sgalkin/runtime-EclipseApplication/test3/test/MyTestCase.php:9" +
			"</failure>" +
			"</testcase>" +
			"</testsuite>" +
			"</testsuites>";

		List<TestCase> expectedResult = new ArrayList<TestCase>();
		
		expectedResult.add(new TestCase("testWillPass", "MyTestCase", "/test3/test/MyTestCase.php", 4));
		expectedResult.add(
				new FailedTestCase(
						"testWillFail", 
						"MyTestCase", 
						"/test3/test/MyTestCase.php", 
						8,
						"MyTestCase::testWillFail"
						+ "should fail"
						+ "Failed asserting that &lt;boolean:false&gt; is true."
						+ "/home/sgalkin/runtime-EclipseApplication/test3/test/MyTestCase.php:9"
				)
		);
		
		
		XmlReportParser parser = new XmlReportParser();
		
		assertEquals(expectedResult, parser.parse(xml));
	}
	
}