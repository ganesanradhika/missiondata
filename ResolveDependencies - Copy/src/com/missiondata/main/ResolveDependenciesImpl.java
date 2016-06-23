package com.missiondata.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.missiondata.helper.DependencyValidationException;
import com.missiondata.helper.FileHandler;
import com.missiondata.model.Dependency;
import com.missiondata.model.Task;

/**
 * Main method with logic to resolve transitive dependencies based on 
 * data given in the input file.
 * 
 * This program takes runtime arguments.
 * 		args[0] - Input file name.
 * 		args[1] - Output file name.
 * @author Radhika Ganesan
 *
 */
public class ResolveDependenciesImpl {

	// Store all the dependencies between two Task objects
	private Map<String, Dependency> dependencies = new HashMap<>();
	
	// Stores all the task objects.
	private Map<String, Task> tasks = new HashMap<>();
	
	private static FileHandler ifr = null;
	
	public static void main(String args[]) throws DependencyValidationException{
		
		ResolveDependenciesImpl rdi = new ResolveDependenciesImpl();
		
		// Instantiate FileHandler object
		try {
			ifr = new FileHandler(args[0], args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new DependencyValidationException(DependencyValidationException.INCORRECT_ARG);
		}
		
		
		
		// Read file and instantiate objects
		rdi.initializeTasksAndDependencies();
		
		// calculate dependencies
		String finalStr = rdi.calculateTransitiveDependencies();
		
		// write output file.
		ifr.writeOutput(finalStr);
		
	}

	/**
	 * @param rdi
	 * @param tasks
	 * @param dependencies
	 * @param inputLines
	 * @throws DependencyValidationException 
	 */
	private void initializeTasksAndDependencies() throws DependencyValidationException {
		
		List<String> inputLines = ifr.readInput(ifr.getInputFileName());
		for (String line : inputLines) {
			
			String[] split = line.split(" ");
			initTasks(split);
			assignDependencies(split);
			
		}
	}
    
	/**
	 * Method to calculate transitive dependencies.
	 * 
	 * @param uniqueTasks
	 * @param tasks
	 */
	private String calculateTransitiveDependencies() {
		
		StringBuffer finalResult = new StringBuffer();
		
		for (String taskName : tasks.keySet()) {
			String result = tasks.get(taskName).getDependentTasks();
			
			if(result.trim().isEmpty()){
				continue;
			}
			
			String formattedStr = formatResult(taskName, result);
			finalResult.append(formattedStr);
			tasks.get(taskName).resetTraversalFlag();
		}

		return finalResult.toString();
		
	}
	
	/**
	 * Process result to the required format.
	 * 
	 * @param dependentTask
	 * @param result
	 * @return
	 */
	private String formatResult(String dependentTask , String result) {
		
		List<String> lst = Arrays.asList(result.split(" "));
		// sort the data and remove duplicates
		Set<String> set = new TreeSet<>(lst);
		StringBuffer dependenciesStrBuf = new StringBuffer(dependentTask + "   ");
		for (String dependsOnTask : set) {
			
			// This check is for cyclic-dependency. Formatting the output so that the result doesn't show self dependency
			if(dependentTask.equals(dependsOnTask)){
				continue;
			}
			dependenciesStrBuf.append(dependsOnTask);
			dependenciesStrBuf.append(" ");
		}
		dependenciesStrBuf.append("\n");
		return dependenciesStrBuf.toString();
	}
	
	/**
	 * Create Tasks based on the input file.
	 * 
	 * @param tasks
	 * @param uniqueTasks
	 * @param split
	 */
	private void initTasks(String[] split) {
		for (int i = 0; i < split.length; i++) {
			
			String trimmedEntry = split[i].trim();
			if(trimmedEntry.isEmpty()){
				continue;
			}
			
			Task task = new Task(trimmedEntry);
			// If the task is already present in the hash map, do not overwrite
			if(tasks.containsKey(task.getTaskName())){
				continue;
			}
			
			tasks.put(task.getTaskName(), task);
		}
	}
	
	/**
	 * Create Dependencies objects and link tasks to it.
	 * 
	 * @param dependencies
	 * @param tasks
	 * @param split
	 */
	private void assignDependencies(String[] split) {
		Task assignDependency = null;
		for (int i = 0; i < split.length; i++) {
			
			String trimmedEntry = split[i].trim();
			if(trimmedEntry.isEmpty()){
				continue;
			}else if(assignDependency == null){
				assignDependency = tasks.get(trimmedEntry);
				continue;
			}
			
			Task targetTask = tasks.get(trimmedEntry);
			Dependency d = new Dependency(assignDependency, targetTask);
			dependencies.put(d.toString(), d);
			assignDependency.getDependencies().add(d);
		}
	}


	
}
