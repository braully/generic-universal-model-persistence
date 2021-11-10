/*
Copyright 2019 Braully Rocha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.github.braully.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author strike
 */
public class logutil {

    public static Logger log = LogManager.getLogger("com.github.braully");

    public static void info(String strmsg) {
        log.info(strmsg);
    }

    public static void info(String strmsg, Throwable e) {
        log.info(strmsg, e);
    }

    public static void error(String strmsg) {
        log.error(strmsg);
    }

    public static void error(String strmsg, Throwable ex) {
        log.error(strmsg, ex);
    }

    public static void error(Throwable ex) {
        log.error(ex);
    }

    public static void debug(String fail, Exception e) {
        log.debug(fail, e);
    }

    public static void debug(String msg) {
        log.debug(msg);
    }

    public static void warn(String msg) {
        log.warn(msg);
    }

    public static void warn(String msg, Exception e) {
        log.warn(msg, e);
    }
}
