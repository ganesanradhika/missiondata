package com.missiondata.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Performs read-write operation on the input/output files.
 * @author Radhika Ganesan
 *
 */
public class FileHandler {
	
	
	private String inputFileName;
	
	private String outputFileName;
	
	public FileHandler(String inputFileName, String outputFileName) {
		super();
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOuputFileName() {
		return outputFileName;
	}

	public void setOuputFileName(String ouputFileName) {
		this.outputFileName = ouputFileName;
	}

	/**
	 * Reads from the input file in the class path.
	 * @return 
	 * @throws DependencyValidationException 
	 */
	public List<String> readInput(String inputFile) throws DependencyValidationException{
		
		File file = new File(getInputFileName());
		if(!file.exists()){
			throw new DependencyValidationException(DependencyValidationException.FILE_NOT_FOUND);
		}
		
		List<String> inputLines = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				inputLines.add(line);
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inputLines;
	}
	
	/**
	 * Writes into the output file in the class path.
	 * 
	 * @param uniqueTasks
	 * @param tasks
	 */
	public void writeOutput(String resultStr){
		
		File file = new File(getOuputFileName());
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
			writer.write(resultStr);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
	}




	
	
}
