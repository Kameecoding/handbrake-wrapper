package com.kameecoding.handbrake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

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
	private boolean success;
	private boolean finished;
	private AtomicInteger progress;
	private String output;
	
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

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// read the output from the command
			// System.out.println("Here is the standard output of the command:\n");
			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			String error = null;
			char[] buff = new char[200];
			Matcher m = null;
			Matcher fail = null;
			boolean outputFinished = false;
			boolean errorFinished = false;
			while (!finished) {
				if (stdInput.ready()) {
					if (stdInput.read(buff) != -1) {
						sb.append(buff);
						System.out.println(buff);
					} else {
						outputFinished = true;
					}
				}
				if (stdError.ready()) {
					if (stdError.read(buff) != -1) {
						sb2.append(buff);
						System.out.println(buff);
					} else {
						errorFinished = true;
					}
				}
				finished = outputFinished && errorFinished;
			}
			output = sb.toString();
			error = sb2.toString();
			finished = true;
			// read any errors from the attempted command
			/*
			 * System.out.println("Here is the standard error of the command (if any):\n");
			 * while ((s = stdError.readLine()) != null) { System.out.println(s); }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFinished() {
		return finished;
	}

	public AtomicInteger getProgress() {
		return progress;
	}

	public String getOutput() {
		return output;
	}
}
