package snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public interface SnakerLogger
{
    <V> void info(V message);

    <V> void warn(V message);

    <V> void error(V message);

    <V> void print(V message, ConsoleLogLevel level);

    void infof(String format, Object... args);

    void warnf(String format, Object... args);

    void errorf(String format, Object... args);

    void printf(String format, ConsoleLogLevel level, Object... args);
}