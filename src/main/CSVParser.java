package main;

import java.io.File;
import java.io.IOException;

interface CSVParser {
	
	// TODO add encoding option
	// TODO add delimiter option
	// TODO add header option
	// TODO test large files
	// TODO test files with empty value(s) in a line
	// TODO test files with empty lines
	
	CSVParsingResult parse(File aFile) throws IOException;

}
