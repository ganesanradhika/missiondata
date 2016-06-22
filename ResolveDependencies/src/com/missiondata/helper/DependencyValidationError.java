package com.missiondata.helper;

public class DependencyValidationError extends Throwable{

	private static final long serialVersionUID = 1L;

	public static String CYCLIC_DEPENDENCY = "Task defined in input file contain cyclic dependencies ! ";
	
	public static String NO_DEPENDENCIES = "Task defined in input file has no dependencies ! ";
	
	private String errorMsg = null;

	public DependencyValidationError(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		System.out.println(errorMsg);
		super.printStackTrace();
	}
	
}
