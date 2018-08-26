package main;

import java.util.List;
import java.util.Map;

class CSVParsingResult {
	private List<String> headerValues;
	private List<Map<String, String>> contents;
	
	CSVParsingResult(List<String> someHeaderValues, List<Map<String, String>> someContents) {
		headerValues = someHeaderValues;
		contents = someContents;
	}

	public List<String> getHeaderValues() {
		return headerValues;
	}

	public List<Map<String, String>> getContents() {
		return contents;
	}
}
