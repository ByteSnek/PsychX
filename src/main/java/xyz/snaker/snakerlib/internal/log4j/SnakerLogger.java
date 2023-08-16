package xyz.snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public interface SnakerLogger
{
    /**
     * Prints a message with the log level of information
     *
     * @param message The message to log
     **/
    <V> void info(V message);

    /**
     * Prints a message with the log level of warning
     *
     * @param message The message to log
     **/
    <V> void warn(V message);

    /**
     * Prints a message with the log level of error
     *
     * @param message The message to log
     **/
    <V> void error(V message);

    /**
     * Prints a message with a custom defined log level
     *
     * @param message The message to log
     * @param level   The console log level
     **/
    <V> void print(V message, ConsoleLogLevel level);

    /**
     * Prints a formatted message with a log level of information
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void infof(String format, Object... args);

    /**
     * Prints a formatted message with a log level of warning
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void warnf(String format, Object... args);

    /**
     * Prints a formatted message with a log level of error
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void errorf(String format, Object... args);

    /**
     * Prints a formatted message with a custom defined log level
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void printf(String format, ConsoleLogLevel level, Object... args);
}