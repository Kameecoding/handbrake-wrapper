package com.kameecoding.handbrake;

/**
 * @author Andrej Kovac kameecoding (kamee@kameecoding.com) on 2018-01-23
 *
 */
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
