package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVDuplicateFinder {
	
	private final static CSVParser CSV_PARSER = new DefaultCSVParser();
	
	private Map<String, Double> similarityThresholds;
	private boolean checkAllValues;
	
	public CSVDuplicateFinder(List<String> someCheckedHeaderValues) {
		this(someCheckedHeaderValues.stream().collect(Collectors.toMap(headerValue -> headerValue, a -> 1.0)));
	}
	
	public CSVDuplicateFinder(Map<String, Double> someSimilarityThresholds) {
		similarityThresholds = someSimilarityThresholds;
		checkAllValues = false;
	}
	
	public CSVDuplicateFinder() {
		similarityThresholds = null;
		checkAllValues = true;
	}	
	
	public DuplicateFinderResult search(File aCsvFile) throws IOException {
		if (!checkAllValues && (similarityThresholds == null || similarityThresholds.keySet().isEmpty())) {
			return null;
		}
		List<Map<String, String>> theUniqueEntries = new LinkedList<>();
		Map<Map<String, String>, List<Map<String, String>>> theDuplicates = new HashMap<>();
		
		CSVParsingResult theParsedCsvFile = CSV_PARSER.parse(aCsvFile);
		for (int i = 0; i < theParsedCsvFile.getContents().size() - 1; i++) {
			Map<String, String> theCheckedLine = theParsedCsvFile.getContents().get(i); // TODO typyaliases to avoid mixing + improve readability
			Map<String, String> theCheckedValues = getCheckedValuesFor(theCheckedLine);
			
			List<Map<String, String>> theCheckedLineDuplicates = new LinkedList<>();
			for (int j = i + 1; j < theParsedCsvFile.getContents().size(); j++) {
				Map<String, String> theOtherLine = theParsedCsvFile.getContents().get(j);
				Map<String, String> theOtherCheckedValues = getCheckedValuesFor(theOtherLine);
				
				boolean theDuplicateCheck = true;
				for (String eachCheckedHeaderValue : similarityThresholds.keySet()) {
					String theCheckedValue = theCheckedValues.get(eachCheckedHeaderValue);
					String theOtherCheckedValue = theOtherCheckedValues.get(eachCheckedHeaderValue);
					Double theSimilarityThreshold = getSimilarityThresholdFor(eachCheckedHeaderValue);
					theDuplicateCheck = theDuplicateCheck && Utility.similarity(theCheckedValue, theOtherCheckedValue) >= theSimilarityThreshold;
					if (!theDuplicateCheck) {
						break;
					}
				}
				if (theDuplicateCheck) {
					theCheckedLineDuplicates.add(theOtherLine);
				}
			}
			if (theCheckedLineDuplicates.isEmpty()) {
				theUniqueEntries.add(theCheckedLine);
			} else {
				theDuplicates.put(theCheckedLine, theCheckedLineDuplicates);
			}
		}
		return new DuplicateFinderResult(theUniqueEntries, theDuplicates);
	}
	
	private Map<String, Double> getSimilarityThresholds() {
		return similarityThresholds;
	}
	
	private Double getSimilarityThresholdFor(String aHeaderValue) {
		return getSimilarityThresholds().get(aHeaderValue);
	}
	
	private Map<String, String> getCheckedValuesFor(Map<String, String> someLineContents) {
		if (checkAllValues) {
			return someLineContents;
		}
		Map<String, String> theResult = new HashMap<>();
		if (similarityThresholds == null || similarityThresholds.keySet().isEmpty()) {
			return theResult;
		}
		for (String eachCheckedHeaderValue : similarityThresholds.keySet()) {
			String theCheckedLineValue = someLineContents.get(eachCheckedHeaderValue);
			if (theCheckedLineValue != null) {
				theResult.put(eachCheckedHeaderValue, theCheckedLineValue);
			}
		}
		return theResult;
	}

}
