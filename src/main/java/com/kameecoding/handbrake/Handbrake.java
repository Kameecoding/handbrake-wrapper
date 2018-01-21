package com.kameecoding.handbrake;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private double progress;
	private Pattern progressPattern = Pattern.compile("([0-9]{1,2}\\.[0-9]{1,2})[\\s]+?%", Pattern.CASE_INSENSITIVE);
	private Pattern etaPattern = Pattern.compile("([0-9]{2}h[0-9]{2}m[0-9]{2}s)", Pattern.CASE_INSENSITIVE);
	private String output;
	private HandbrakeProgressListener listener;
	
	private Handbrake() {
	}
	
	public static Handbrake newInstance(String location, List<String> args) {
		return newInstance(location, args, null);
	}
	
	public static Handbrake newInstance(String location, List<String> args, HandbrakeProgressListener listener) {
		Handbrake instance =  new Handbrake();
		instance.listener = listener;
		List<String> arguments = new ArrayList<>(args);
		arguments.add(0, location);
		instance.processBuilder = new ProcessBuilder(arguments);
		return instance;
	}

	@Override
	public void run() {
		try {
			processBuilder.redirectError(new File("D:\\projects\\temp\\error.txt"));
			process = processBuilder.start();
			

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			//BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// read the output from the command
			// System.out.println("Here is the standard output of the command:\n");
			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			String error = null;
			char[] buff = new char[76];
			Matcher fail = null;
			boolean outputFinished = false;
			boolean errorFinished = false;
			long lastUpdate = System.nanoTime();
			long currTime = System.nanoTime();
			while (!finished) {
				
				if (stdInput.ready()) {
					if (stdInput.read(buff) != -1) {
						sb.append(buff);
						currTime = System.nanoTime();
						if (Math.abs(currTime - lastUpdate) > 3000000000L) {
							String output = sb.toString();
							Matcher m = etaPattern.matcher(output);
							String eta = null;
							if (m.find()) {
								eta = m.group(0);
							}
							m = progressPattern.matcher(output);
							double progress = 0.0;
							if (m.find()) {
								progress =  Double.parseDouble(m.group(1));
							}
							listener.handleProgressUpdate(new HandbrakeProgressUpdate(eta, progress));
						}
						sb.setLength(0);
					} else {
						finished = true;
					}
				}
				//finished = outputFinished && errorFinished;
			}
			//output = sb.toString();
			//error = sb2.toString();
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
	
	public String getOutput() {
		return output;
	}
}
