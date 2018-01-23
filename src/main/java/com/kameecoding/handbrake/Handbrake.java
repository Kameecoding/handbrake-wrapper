/** 
 * MIT License 
 * 
 * Copyright (c) 2018 Andrej Kovac (Kameecoding) 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions: 
 *  
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE. 
 */ 
package com.kameecoding.handbrake;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * Class for invoking handbrake process
 * 
 * @author Andrej Kovac (kameecoding) <kamee@kameecoding.com> on 2017-06-17.
 */
public class Handbrake implements Runnable {
	
	private ProcessBuilder processBuilder;
	private Process process;
	private boolean success;
	private boolean finished;
	private double progress;
	private Pattern progressPattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,2})[\\s]+?%", Pattern.CASE_INSENSITIVE);
	private Pattern etaPattern = Pattern.compile("([0-9]{2}h[0-9]{2}m[0-9]{2}s)", Pattern.CASE_INSENSITIVE);
	private Pattern resultPattern = Pattern.compile("result = ([0-9]{1,2})", Pattern.CASE_INSENSITIVE);
	private String output;
	private IHandbrakeProgressListener listener;
	
	private Handbrake() {
	}
	
	public static Handbrake newInstance(String location, List<String> args) {
		return newInstance(location, args, null);
	}
	
	public static Handbrake newInstance(String location, List<String> args, IHandbrakeProgressListener listener) {
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
			File error = new File("error.txt");
			processBuilder.redirectError(error);
			process = processBuilder.start();
			

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			//BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// read the output from the command
			// System.out.println("Here is the standard output of the command:\n");
			StringBuilder sb = new StringBuilder();
			char[] buff = new char[76];
			long lastUpdate = System.nanoTime();
			long currTime = System.nanoTime();
			int readyLimit = 10;
			int ready = 0;
			boolean checkForReadyLimit = false;
			while (!finished) {
				if (checkForReadyLimit && ready > readyLimit) {
					finished = true;
				}
				if (stdInput.ready()) {
					ready = 0;
					if (stdInput.read(buff) > 0) {
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
							if (m.find()) {
								double progress = 0.0;
								progress =  Double.parseDouble(m.group(1));
								if (progress == 100.0) {
									checkForReadyLimit = true;
								}
							} else {
								checkForReadyLimit = true;
							}
							listener.handleProgressUpdate(new HandbrakeProgressUpdate(eta, progress));
							lastUpdate = System.nanoTime();
						}
						sb.setLength(0);
					} else {
						finished = true;
					}
				} else {
					ready++;
				}
			}
			success = true;

			String errorOuput = FileUtils.readFileToString(error, Charset.defaultCharset());
			Matcher fail = resultPattern.matcher(errorOuput);
			if (fail.find()) {
				int errorCode = Integer.parseInt(fail.group(1));
				if (errorCode > 0) {
					success = false;
				}
			}
			
			finished = true;
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
