package com.kameecoding.handbrake;

public class HandbrakeProgressUpdate {
	private String ETA;
	private double progress;
	
	public HandbrakeProgressUpdate(String eTA, double progress) {
		super();
		ETA = eTA;
		this.progress = progress;
	}

	public String getETA() {
		return ETA;
	}

	public double getProgress() {
		return progress;
	}
}
