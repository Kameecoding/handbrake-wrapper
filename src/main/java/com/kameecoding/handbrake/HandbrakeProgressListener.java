package com.kameecoding.handbrake;

public interface HandbrakeProgressListener {
	abstract void handleProgressUpdate(HandbrakeProgressUpdate update);
}
