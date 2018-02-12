package com.kameecoding.handbrake;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runnable handbrake process
 */
public class Handbrake implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handbrake.class);

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
    private String output;
    private IHandbrakeProgressListener listener;

    private Handbrake() {}

    public static Handbrake newInstance(String location, List<String> args) {
        return newInstance(location, args, null, false);
    }

    public static Handbrake newInstance(
            String location,
            List<String> args,
            IHandbrakeProgressListener listener,
            boolean taskset) {
        Handbrake instance = new Handbrake();
        instance.listener = listener;
        List<String> arguments = new ArrayList<>(args);
        arguments.add(0, location);

        if (SystemUtils.IS_OS_LINUX) {
            arguments.add(0, "0,1,2,3,4,5");
            arguments.add(0, "-c");
            arguments.add(0, "taskset");
        }
        instance.processBuilder = new ProcessBuilder(arguments);
        return instance;
    }

    @Override
    public void run() {
        try {
            LOGGER.trace("hanbrake running");
            File error = new File("error.txt");
            processBuilder.redirectError(error);
            process = processBuilder.start();

            BufferedReader stdInput =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            /*String encoding = "UTF-8";
            if (SystemUtils.IS_OS_LINUX) {
                encoding = "UTF-8";
            }
            LineIterator lineIterator = IOUtils.lineIterator(process.getInputStream(), encoding);*/
            // BufferedReader stdError = new BufferedReader(new
            // InputStreamReader(process.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            StringBuilder sb = new StringBuilder();
            // String s = null;
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
                } else if (!process.isAlive()) {
                    break;
                }
            }
            process.waitFor();
            success = true;

            String errorOuput = FileUtils.readFileToString(error, Charset.defaultCharset());
            Matcher fail = resultPattern.matcher(errorOuput);
            if (fail.find()) {
                int errorCode = Integer.parseInt(fail.group(1));
                if (errorCode > 0) {
                    success = false;
                    if (errorCode == 3) {
                        LOGGER.error(
                                "Handbrake Output error. Make sure you create the directory tree for the output");
                    }
                }
            }

            finished = true;
            LOGGER.trace("hanbrake finished");
        } catch (Exception e) {
            LOGGER.error("Handbrake Convert Failed", e);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOutput() {
        return output;
    }
}
