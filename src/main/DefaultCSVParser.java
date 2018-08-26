package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DefaultCSVParser implements CSVParser {
	
	private final static String DELIMITER = ";";

	public CSVParsingResult parse(File aFile) throws IOException {
		checkFile(aFile);
		
		List<Map<String, String>> theContents = new LinkedList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(aFile))) {
			String theLine = br.readLine();
			String[] theHeaderValues = parseHeader(theLine);
			
			int theNumberOfHeaderValues = theHeaderValues.length;
			int theLineIndex = 0;
			
		    while ((theLine = br.readLine()) != null) {
		    	String[] theValues = theLine.split(DELIMITER);
		    	if (theValues.length > theNumberOfHeaderValues) {
		    		throw new IOException("Line " + theLineIndex + " has too many values.");
		    	}
		    	
		    	Map<String, String> theLineContents = new HashMap<>();
		    	for (int i = 0; i < theNumberOfHeaderValues; i++) {
		    		theLineContents.put(theHeaderValues[i], theValues[i]);
		    	}
		    	theContents.add(theLineContents);
		    	theLineIndex++;
		    }
		    
		    return new CSVParsingResult(Arrays.asList(theHeaderValues), theContents);
		}
	}
	
	private String[] parseHeader(String aLine) throws IOException {
		if (aLine == null) {
			throw new IOException("Header line is empty.");
		}
		return aLine.split(DELIMITER);
	}
	
	private void checkFile(File aFile) throws IOException {
		if (aFile == null) {
			throw new IOException("File is null.");
		}
		if (!aFile.exists()) {
			throw new IOException("File " + aFile.getName() + " does not exist.");
		}
		if (!aFile.canRead()) {
			throw new IOException("File " + aFile.getAbsolutePath() + " is not readable.");
		}
	}
}
