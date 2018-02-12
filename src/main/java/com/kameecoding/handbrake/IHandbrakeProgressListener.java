package com.kameecoding.handbrake;

/**
 * @author Andrej Kovac kameecoding (kamee@kameecoding.com) on 2018-01-23
 *
 */
public interface IHandbrakeProgressListener {
	abstract void handleProgressUpdate(HandbrakeProgressUpdate update);
}
