package com.missiondata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a Task. Created from the input file.
 * @author radyog
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
			dependency.setTraversed(true);
			sb.append(targetTask.getDependentTasks());
			sb.append(targetTask.getTaskName());
			sb.append(" ");
		}
		
		return sb.toString();
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Task-" + getTaskName();
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		
		if(arg0 instanceof Task && this.taskName.equals(((Task) arg0).getTaskName())){
			return true;
		}
		
		return false;
	}
	
}
