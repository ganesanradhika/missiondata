package com.missiondata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a Task. Created from the input file.
 * @author Radhika Ganesan
 *
 */
public class Task {

	private String taskName;
	
	private List<Dependency> dependencies = new ArrayList<>();

	public Task(String taskName) {
		super();
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
	public String getDependentTasks(){
		
		StringBuffer sb = new StringBuffer();
		
		for (Dependency dependency : dependencies) {
			Task targetTask = dependency.getTargetTask();
			sb.append(targetTask.getTaskName());
			sb.append(" ");
			if(!dependency.isTraversed()){
				dependency.setTraversed(true);
				sb.append(targetTask.getDependentTasks());
			}
		}
		
		return sb.toString();
	}

	
	@Override
	public String toString() {
		return "Task-" + getTaskName();
	}
	
	@Override
	public boolean equals(Object aTask) {
		if(aTask instanceof Task && this.taskName.equals(((Task) aTask).getTaskName())){
			return true;
		}
		return false;
	}

	public void resetTraversalFlag() {
		for (Dependency dependency : dependencies) {
			Task targetTask = dependency.getTargetTask();
			if(dependency.isTraversed()){
				dependency.setTraversed(false);
				targetTask.resetTraversalFlag();
			}
		}
	}
	
}
