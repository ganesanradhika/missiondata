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

public class ResolveDependenciesImpl {

	public static void main(String args[]){
		
		Map<String, Dependency> dependencies = new HashMap<>();
		FileHandler ifr =new FileHandler();
		List<String> inputLines = ifr.readInput();
		
		Map<String, Task> tasks = new HashMap<>();
		Set<String> uniqueTasks = new HashSet<>();
		for (String line : inputLines) {
			
			String[] split = line.split(" ");

			for (int i = 0; i < split.length; i++) {
				Task task = new Task(split[i]);
				// If the task is already present in the hash map, do not overwrite
				if(uniqueTasks.contains(task.getTaskName())){
					continue;
				}
				
				tasks.put(task.getTaskName(), task);
				uniqueTasks.add(task.getTaskName());
			}
			
			Task assignDependency = tasks.get(split[0]);
			for (int i = 1; i < split.length; i++) {
				Task targetTask = tasks.get(split[i]);
				Dependency d = new Dependency(assignDependency, targetTask);
				dependencies.put(assignDependency.getTaskName() +"-"+targetTask.getTaskName(), d);
				assignDependency.getDependencies().add(d);
			}
			
			
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

	private static void validateDependencies(Map<String, Dependency> dependencies) throws DependencyValidationError {
		
		if(dependencies.isEmpty()){
			throw new DependencyValidationError(DependencyValidationError.NO_DEPENDENCIES);
		}
		
		Set<Entry<String, Dependency>> entry = dependencies.entrySet();
		
		for (Entry<String, Dependency> e : entry) {
			
			StringBuilder reverse = new StringBuilder(e.getKey());
			reverse.reverse();
			if(dependencies.containsKey(reverse.toString())){
				throw new DependencyValidationError(DependencyValidationError.CYCLIC_DEPENDENCY);
			}
			
		}
	}

	
}
