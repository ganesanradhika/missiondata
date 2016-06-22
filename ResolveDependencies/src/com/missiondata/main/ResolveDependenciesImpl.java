package com.missiondata.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.missiondata.helper.DependencyValidationError;
import com.missiondata.helper.FileHandler;
import com.missiondata.model.Dependency;
import com.missiondata.model.Task;

/**
 * Main method with logic to resolve the dependencies based on 
 * data given in the input file.
 * @author radyog
 *
 */
public class ResolveDependenciesImpl {

	public static void main(String args[]){
		
		ResolveDependenciesImpl rdi = new ResolveDependenciesImpl();
		
		// Stores all the task objects.
		Map<String, Task> tasks = new HashMap<>();
		// This set keeps track of all the tasks created so that 
		// duplicate tasks are not created for repeated entries in the input file
		Set<String> uniqueTasks = new HashSet<>();
		// Store all the dependencies between two Task objects
		Map<String, Dependency> dependencies = new HashMap<>();
		FileHandler ifr = new FileHandler();
		
		List<String> inputLines = ifr.readInput();
		for (String line : inputLines) {
			
			String[] split = line.split(" ");
			rdi.initTasks(tasks, uniqueTasks, split);
			rdi.assignDependencies(dependencies, tasks, split);
			
		}
		
		// check for cyclic dependencies
		try {
			validateDependencies(dependencies);
			ifr.writeOutput(uniqueTasks, tasks);
		} catch (DependencyValidationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
	// create tasks based on input files
	private void initTasks(Map<String, Task> tasks, Set<String> uniqueTasks, String[] split) {
		for (int i = 0; i < split.length; i++) {
			Task task = new Task(split[i]);
			// If the task is already present in the hash map, do not overwrite
			if(uniqueTasks.contains(task.getTaskName())){
				continue;
			}
			
			tasks.put(task.getTaskName(), task);
			uniqueTasks.add(task.getTaskName());
		}
	}
	
	// create dependencies
	private void assignDependencies(Map<String, Dependency> dependencies, Map<String, Task> tasks,
			String[] split) {
		Task assignDependency = tasks.get(split[0]);
		for (int i = 1; i < split.length; i++) {
			Task targetTask = tasks.get(split[i]);
			Dependency d = new Dependency(assignDependency, targetTask);
			dependencies.put(d.toString(), d);
			assignDependency.getDependencies().add(d);
		}
	}


	private static void validateDependencies(Map<String, Dependency> dependencies) throws DependencyValidationError {
		// if there are no entries in dependencies map ...
		if(dependencies.isEmpty()){
			throw new DependencyValidationError(DependencyValidationError.NO_DEPENDENCIES);
		}
		
		//
		for (Entry<String, Dependency> e : dependencies.entrySet()) {
			
			StringBuilder key = new StringBuilder(e.getKey());
			if(dependencies.containsKey(key.reverse().toString())){
				throw new DependencyValidationError(DependencyValidationError.CYCLIC_DEPENDENCY);
			}
			
		}
	}

	
}
