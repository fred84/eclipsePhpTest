package me.galkin.eclipse.php.domain;

interface IReportParser {
	
	IResultsComposite parse(String xml) throws ResultsNotFoundException;

}