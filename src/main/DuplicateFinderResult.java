package main;

import java.util.List;
import java.util.Map;

public class DuplicateFinderResult {
	
	private List<Map<String, String>> uniqueEntries;
	private Map<Map<String, String>, List<Map<String, String>>> duplicateEntries;
	
	DuplicateFinderResult(List<Map<String, String>> someUniqueEntries, Map<Map<String, String>, List<Map<String, String>>> someDuplicateEntries) {
		uniqueEntries = someUniqueEntries;
		duplicateEntries = someDuplicateEntries;
	}

	public List<Map<String, String>> getUniqueEntries() {
		return uniqueEntries;
	}

	public Map<Map<String, String>, List<Map<String, String>>> getDuplicateEntries() {
		return duplicateEntries;
	}

}
