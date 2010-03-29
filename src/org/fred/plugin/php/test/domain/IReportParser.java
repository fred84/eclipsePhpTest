package org.fred.plugin.php.test.domain;

import java.util.List;

public interface IReportParser {

	public List<TestCase> parse(String xml);
	
}
