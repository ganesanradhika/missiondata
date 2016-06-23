package com.missiondata.helper;

/**
 * Exception throw in case of invalid inputs 
 * @author Radhika Ganesan
 *
 */
public class DependencyException extends Throwable{

	private static final long serialVersionUID = 1L;

	public static String INCORRECT_ARG = "Please pass following run-time arguments in the same order - {input file} , {output file}";
	
	public static String FILE_NOT_FOUND = "Input file not found !";
	
	public static String NO_DEPENDENCIES = "Tasks defined in input file has no dependencies ! ";
	
	private String errorMsg = null;

	public DependencyException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
	@Override
	public String toString() {
		return errorMsg;
	}
	
}
