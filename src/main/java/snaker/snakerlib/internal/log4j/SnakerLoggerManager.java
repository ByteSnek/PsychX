package snaker.snakerlib.internal.log4j;

import snaker.snakerlib.SnakerLib;

import java.util.Formatter;
import java.util.function.Function;

/**
 * Created by SnakerBone on 9/07/2023
 **/
public class SnakerLoggerManager implements SnakerLogger
{
    public static Function<String, SnakerLoggerManager> INSTANCE = SnakerLoggerManager::new;
    private final String name;

    private SnakerLoggerManager(String name)
    {
        this.name = SnakerLib.i18nt(name);
    }

    @Override
    public <V> void info(V message)
    {
        print(message, ConsoleLogLevel.INFO);
    }

    @Override
    public <V> void warn(V message)
    {
        print(message, ConsoleLogLevel.WARN);
    }

    @Override
    public <V> void error(V message)
    {
        print(message, ConsoleLogLevel.ERROR);
    }

    @Override
    public <V> void print(V message, ConsoleLogLevel level)
    {
        System.out.println(level.colourValue() + "[" + name + "/" + level.markerValue() + "]: " + message);
    }

    @Override
    public void infof(String format, Object... args)
    {
        printf(format, ConsoleLogLevel.INFO, args);
    }

    @Override
    public void warnf(String format, Object... args)
    {
        printf(format, ConsoleLogLevel.WARN, args);
    }

    @Override
    public void errorf(String format, Object... args)
    {
        printf(format, ConsoleLogLevel.ERROR, args);
    }

    @Override
    public void printf(String format, ConsoleLogLevel level, Object... args)
    {
        System.out.println(level.colourValue() + "[" + name + "/" + level.markerValue() + "]: " + new Formatter().format(format, args));
    }
}
