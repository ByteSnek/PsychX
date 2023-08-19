package xyz.snaker.snakerlib.concurrent;

import xyz.snaker.snakerlib.SnakerLib;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class UncaughtExceptionThread extends Thread
{
    private final UncaughtExceptionHandler handler;
    private final String message;

    public UncaughtExceptionThread(Exception cause)
    {
        this.message = "Uncaught excpetion detected";
        this.handler = (thread, throwable) -> SnakerLib.LOGGER.error(cause);
    }

    public UncaughtExceptionThread(String message, Exception cause)
    {
        this.message = message;
        this.handler = (thread, throwable) -> SnakerLib.LOGGER.error(cause);
    }

    public static UncaughtExceptionThread createAndRun(Exception cause)
    {
        UncaughtExceptionThread thread = new UncaughtExceptionThread(cause);
        thread.start();
        return thread;
    }

    public static UncaughtExceptionThread createAndRun(String message, Exception cause)
    {
        UncaughtExceptionThread thread = new UncaughtExceptionThread(message, cause);
        thread.start();
        return thread;
    }

    @Override
    public void run()
    {
        throw new RuntimeException(message);
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
    {
        super.setUncaughtExceptionHandler(handler);
    }
}
