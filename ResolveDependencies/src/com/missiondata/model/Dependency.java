package com.missiondata.model;

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
	
	
	
}
