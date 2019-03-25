/*
 * Some open source application
 *
 * Copyright 2018 by it's authors.
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See LICENSE, AUTHORS.
 *
 * @license GPL-3.0+ <https://opensource.org/licenses/GPL-3.0>
 */
package com.kameecoding.handbrake;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Handbrake implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Handbrake.class);

    private ProcessBuilder processBuilder;
    private String output;
    private String error;

    private Handbrake() {}

    public static Handbrake newInstance(String location, List<String> args) {
        Handbrake handbrake = new Handbrake();

        List<String> arguments = new ArrayList<>(args);
        arguments.add(0, location);
        handbrake.processBuilder = new ProcessBuilder(arguments);

        return handbrake;
    }

    @Override
    public void run() {
        LOGGER.trace("hanbrake running");
        try {
            Process process = processBuilder.start();

            BufferedReader stdInput =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            StringBuilder errorSb = new StringBuilder();
            output = String.join("\n", IOUtils.readLines(stdInput));
            error = String.join("\n", IOUtils.readLines(stdError));
        } catch (Exception e) {
            LOGGER.error("Failed to execute handbrake", e);
        }
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }
}
