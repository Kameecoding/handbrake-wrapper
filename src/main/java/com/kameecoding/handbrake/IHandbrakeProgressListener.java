package com.kameecoding.handbrake;

public interface IHandbrakeProgressListener {
	abstract void handleProgressUpdate(HandbrakeProgressUpdate update);
}
