package com.kameecoding.handbrake.api;

public interface IHandbrakeProgressListener {
	void handleProgressUpdate(HandbrakeProgressUpdate update);
}
