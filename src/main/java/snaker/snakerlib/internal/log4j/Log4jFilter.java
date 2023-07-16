package snaker.snakerlib.internal.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.config.SnakerConfig;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by SnakerBone on 8/07/2023
 **/
public class Log4jFilter extends AbstractFilter implements Filter
{
    public boolean isLoggable(@NotNull LogRecord record)
    {
        return !Log4jFilter.shouldFilter(record.getMessage());
    }

    public Result filter(@NotNull LogEvent event)
    {
        String name = event.getLoggerName();
        return Log4jFilter.shouldFilter("[" + name + "]: " + event.getMessage().getFormattedMessage()) ? Result.DENY : Result.NEUTRAL;
    }

    public static <PS extends PrintStream> boolean applicate(PS stream, Log4jFilter filter)
    {
        System.setOut(stream);
        java.util.logging.Logger.getLogger("").setFilter(filter);
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(filter);
        ArrayList<LoggerConfig> checks = new ArrayList<>();
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> map = context.getConfiguration().getLoggers();
        for (LoggerConfig config : map.values()) {
            if (!checks.contains(config)) {
                config.addFilter(filter);
                checks.add(config);
            }
        }
        return filter.equalsImpl(filter);
    }

    public static boolean applicate(Log4jFilter filter)
    {
        System.setOut(new JavaPrintStreamFilter(System.out));
        java.util.logging.Logger.getLogger("").setFilter(filter);
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(filter);
        ArrayList<LoggerConfig> checks = new ArrayList<>();
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> map = context.getConfiguration().getLoggers();
        for (LoggerConfig config : map.values()) {
            if (!checks.contains(config)) {
                config.addFilter(filter);
                checks.add(config);
            }
        }
        return filter.equalsImpl(filter);
    }

    public static boolean shouldFilter(String message)
    {
        Iterator<? extends String> regexIterator = SnakerConfig.COMMON.regexLogFilter.get().iterator();
        Iterator<? extends String> textIterator = SnakerConfig.COMMON.textLogFilter.get().iterator();
        String phrase;
        if (message != null) {
            do {
                if (!textIterator.hasNext()) {
                    while (regexIterator.hasNext()) {
                        String regex = regexIterator.next();
                        if (message.matches(regex)) {
                            return true;
                        }
                    }
                    return false;
                }
                phrase = textIterator.next();
            } while (!message.contains(phrase));
        }
        return true;
    }

    public static class JavaPrintStreamFilter extends PrintStream
    {
        public JavaPrintStreamFilter(PrintStream stream)
        {
            super(stream);
        }

        public void println(String x)
        {
            if (!Log4jFilter.shouldFilter(x)) {
                super.println(x);
            }

        }

        public void print(String s)
        {
            if (!Log4jFilter.shouldFilter(s)) {
                super.print(s);
            }

        }
    }
}
