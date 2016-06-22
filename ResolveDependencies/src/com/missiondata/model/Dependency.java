package com.missiondata.model;

/**
 * Define the dependency between two Tasks.
 * sourceTask - task that is DEPENDENT on other tasks
 * targetTask - task that sourceTask DEPENDS on.
 * 
 * @author radyog
 *
 */
public class Dependency {

	private Task sourceTask;
	
	private Task targetTask;
	
	private boolean traversed = false;
	
	
	public Dependency(Task sourceTask, Task targetTask) {
		super();
		this.sourceTask = sourceTask;
		this.targetTask = targetTask;
	}

	public Task getSourceTask() {
		return sourceTask;
	}

	public void setSourceTask(Task sourceTask) {
		this.sourceTask = sourceTask;
	}

	public Task getTargetTask() {
		return targetTask;
	}

	public void setTargetTask(Task targetTask) {
		this.targetTask = targetTask;
	}

	public boolean isTraversed() {
		return traversed;
	}

	public void setTraversed(boolean traversed) {
		this.traversed = traversed;
	}
	
	/**
	 * returns the relation in string format.
	 * 
	 */
	@Override
	public String toString() {
		return sourceTask.getTaskName()+"-"+targetTask.getTaskName();
	}
	
	
	
}
