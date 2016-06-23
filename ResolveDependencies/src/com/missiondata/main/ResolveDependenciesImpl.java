package com.missiondata.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.missiondata.helper.DependencyException;
import com.missiondata.helper.FileHandler;
import com.missiondata.model.Dependency;
import com.missiondata.model.Task;

/**
 * This class contains logic to resolve transitive dependencies based on 
 * data given in the input file.
 * 
 * This program takes runtime arguments.
 * 		args[0] - Input file name (full path). ex:- c:\results\input.txt
 * 		args[1] - Output file name. (full path). ex:- c:\results\output.txt
 * 
 * Input files accepts characters, words, number and special characters. 
 * Leading and trailing spaces will be trimmed by the program.
 * 
 * @author Radhika Ganesan
 *
 */
public class ResolveDependenciesImpl {

	// Store all the dependencies between two Task objects
	private Map<String, Dependency> dependencies = new HashMap<>();
	
	// Stores all the task objects.
	private Map<String, Task> tasks = new HashMap<>();
	
	private static FileHandler ifr = null;
	
	public static void main(String args[]) throws DependencyException{
		
		ResolveDependenciesImpl rdi = new ResolveDependenciesImpl();
		
		// Instantiate FileHandler object
		try {
			ifr = new FileHandler(args[0], args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new DependencyException(DependencyException.INCORRECT_ARG);
		}
		
		// Read file and instantiate objects
		rdi.initializeTasksAndDependencies();
		
		// calculate dependencies
		String finalStr = rdi.calculateTransitiveDependencies();
		
		// write output file.
		ifr.writeOutput(finalStr);
		
	}

	/**
	 * Reads input file and initializes tasks & dependencies.
	 * @throws DependencyException if no dependencies are defined in the input file.
	 */
	private void initializeTasksAndDependencies() throws DependencyException {
		
		List<String> inputLines = ifr.readInput(ifr.getInputFileName());
		for (String line : inputLines) {
			
			String[] split = line.split(" ");
			initTasks(split);
			assignDependencies(split);
			
		}
		
		if(dependencies.isEmpty()){
			throw new DependencyException(DependencyException.NO_DEPENDENCIES);
		}
	}
    
	/**
	 * Calculates dependencies.
	 * @return output in String format.
	 */
	private String calculateTransitiveDependencies() {
		
		StringBuffer finalResult = new StringBuffer();
		
		for (String taskName : tasks.keySet()) {
			String result = tasks.get(taskName).getDependentTasks();
			
			if(result.trim().isEmpty()){
				continue;
			}
			// format result to get the required formatting
			String formattedStr = formatResult(taskName, result);
			finalResult.append(formattedStr);
			//reset traversed flag in each Task
			tasks.get(taskName).resetTraversalFlag();
		}

		return finalResult.toString();
		
	}
	
	/**
	 * Process result to the required format.
	 * @param dependentTask
	 * @param result
	 * @return formatted String.
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
	 * @param split - each line in input file in String array 
	 */
	private void initTasks(String[] split) {
		for (int i = 0; i < split.length; i++) {
			
			String trimmedValue = split[i].trim();
			if(trimmedValue.isEmpty()){
				continue;
			}
			
			Task task = new Task(trimmedValue);
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
	 * @param split - - each line in input file in String array
	 */
	private void assignDependencies(String[] split) {
		Task assignDependency = null;
		for (int i = 0; i < split.length; i++) {
			
			String trimmedValue = split[i].trim();
			if(trimmedValue.isEmpty()){
				continue;
			}else if(assignDependency == null){
				assignDependency = tasks.get(trimmedValue);
				continue;
			}
			
			Task targetTask = tasks.get(trimmedValue);
			Dependency d = new Dependency(assignDependency, targetTask);
			dependencies.put(d.toString(), d);
			assignDependency.getDependencies().add(d);
		}
	}


	
}
