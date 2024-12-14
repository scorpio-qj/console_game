package org.nico.ratel.commons.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EventHandlerUtils {

    public static final ExecutorService  SINGLE_EXECUTOR = Executors.newSingleThreadExecutor();
}
