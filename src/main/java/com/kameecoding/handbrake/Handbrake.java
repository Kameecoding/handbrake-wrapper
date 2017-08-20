package com.kameecoding.handbrake;

import java.io.IOException;
import java.util.List;

/**
 * Created by Andrej Kovac (kameecoding) <andrej.kovac.ggc@gmail.com> on 2017-06-17.
 */

/**
 * Class for invoking handbrake process
 * 
 * @author KameeCoding
 */
public class Handbrake implements Runnable {
	
	private ProcessBuilder processBuilder;
	private Process process;
	
	private Handbrake() {
	}
	
	public static Handbrake newInstance(String location, List<String> args) {
		Handbrake instance =  new Handbrake();
		args.add(0, location);
		instance.processBuilder = new ProcessBuilder(args);
		return instance;
	}

	@Override
	public void run() {
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
