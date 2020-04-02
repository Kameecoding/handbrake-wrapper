package com.kameecoding.handbrake.runnable;

import com.kameecoding.handbrake.api.HandbrakeProgressUpdate;
import com.kameecoding.handbrake.api.IHandbrakeProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runnable handbrake process
 */
public class HandbrakeConverter implements Runnable {
  private static final Logger LOGGER = LoggerFactory.getLogger(HandbrakeConverter.class);

  private ProcessBuilder processBuilder;
  private Process process;
  private boolean success;
  private boolean finished;
  private double progress;
  private Pattern progressPattern =
      Pattern.compile("([0-9]{1,3}\\.[0-9]{1,2})[\\s]+?%", Pattern.CASE_INSENSITIVE);
  private Pattern etaPattern =
      Pattern.compile("([0-9]{2}h[0-9]{2}m[0-9]{2}s)", Pattern.CASE_INSENSITIVE);
    private Pattern resultPattern =
            Pattern.compile("result = ([0-9]{1,2})", Pattern.CASE_INSENSITIVE);
    private String error;
    private IHandbrakeProgressListener listener;

  private HandbrakeConverter() {
  }

  public static HandbrakeConverter newInstance(String location, List<String> args) {
    return newInstance(location, args, null, false);
  }

  public static HandbrakeConverter newInstance(
      String location,
      List<String> args,
      IHandbrakeProgressListener listener,
      boolean taskset) {
    HandbrakeConverter instance = new HandbrakeConverter();
    instance.listener = listener;
    List<String> arguments = new ArrayList<>(args);
    arguments.add(0, location);

//        if (SystemUtils.IS_OS_LINUX) {
//            arguments.add(0, "0,1,2,3");
//            arguments.add(0, "-c");
//            arguments.add(0, "taskset");
//        }
        instance.processBuilder = new ProcessBuilder(arguments);
        return instance;
    }

    @Override
    public void run() {
        try {
            LOGGER.trace("hanbrake running");
            process = processBuilder.start();

            BufferedReader stdInput =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            StringBuilder errorSb = new StringBuilder();
            char[] buff = new char[76];
            long lastUpdate = System.nanoTime();
            long currTime;
            while (!finished) {
                if (stdInput.ready()) {
                    if (stdInput.read(buff) > 0) {
                        sb.append(buff);
                        currTime = System.nanoTime();
                        if (Math.abs(currTime - lastUpdate) > 1000000000L) {
                            String output = sb.toString();
                            Matcher m = etaPattern.matcher(output);
                            String eta = null;
                            if (m.find()) {
                                eta = m.group(0);
                            }
                            m = progressPattern.matcher(output);
                            if (m.find()) {
                                progress = Double.parseDouble(m.group(1));
                            }
                            listener.handleProgressUpdate(
                                    new HandbrakeProgressUpdate(eta, progress));
                            lastUpdate = System.nanoTime();
                        }
                        sb.setLength(0);
                    } else {
                        finished = true;
                    }
                } else if (stdError.ready()) {
                    errorSb.append(stdError.readLine());
                } else if (!process.isAlive()) {
                    break;
                }
            }
            process.waitFor();
            error = errorSb.toString();
            success = process.exitValue() == 0;

            Matcher fail = resultPattern.matcher(error);
            if (fail.find()) {
                int errorCode = Integer.parseInt(fail.group(1));
                if (errorCode > 0) {
                    success = false;
                    if (errorCode == 3) {
                        LOGGER.error(
                                "HandbrakeConvert Output error. Make sure you create the directory tree for the output");
                    }
                }
            }

            finished = true;
            LOGGER.trace("handbrake finished");
        } catch (Exception e) {
            LOGGER.error("HandbrakeConvert Convert Failed", e);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
