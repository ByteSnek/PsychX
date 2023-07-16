package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 29/05/2023
 * <p>
 * Log helper
 **/
public interface SnakerLogger
{
    SnakerLogger INSTANCE = make();

    static SnakerLogger make()
    {
        return new SnakerLogger()
        {

        };
    }

    default <E extends Throwable> void logError(E exception)
    {
        error(exception);
        exception.printStackTrace();
    }

    default <Message> void logOnce(Message message)
    {
        if (SnakerLoggerManager.getLogStatus()) {
            info(message);
            SnakerLoggerManager.invertLoggerStatus();
        }
    }

    default <Message> void msg(Message message)
    {
        msg(message, ColourCode.WHITE, MarkerType.MESSAGE);
    }

    default <Message> void msg(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void info(Message message)
    {
        info(message, ColourCode.CYAN, MarkerType.INFO);
    }

    default <Message> void info(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void debug(Message message)
    {
        debug(message, ColourCode.CYAN, MarkerType.DEBUG);
    }

    default <Message> void debug(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void catching(Message message)
    {
        catching(message, ColourCode.YELLOW, MarkerType.CATCHING);
    }

    default <Message> void catching(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void system(Message message)
    {
        system(message, ColourCode.GREEN, MarkerType.SYSTEM);
    }

    default <Message> void system(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void thread(Message message)
    {
        thread(message, ColourCode.GREEN, MarkerType.THREAD);
    }

    default <Message> void thread(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void worker(Message message)
    {
        worker(message, ColourCode.CYAN, MarkerType.WORKER);
    }

    default <Message> void worker(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void trace(Message message)
    {
        trace(message, ColourCode.RED, MarkerType.TRACE);
    }

    default <Message> void trace(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void dev(Message message)
    {
        dev(message, ColourCode.PURPLE, MarkerType.DEV);
    }

    default <Message> void dev(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void warn(Message message)
    {
        warn(message, ColourCode.YELLOW, MarkerType.WARN);
    }

    default <Message> void warn(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void error(Message message)
    {
        error(message, ColourCode.RED, MarkerType.ERROR);
    }

    default <Message> void error(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    default <Message> void fatal(Message message)
    {
        fatal(message, ColourCode.RED, MarkerType.FATAL);
    }

    default <Message> void fatal(Message message, ColourCode colour, MarkerType type)
    {
        print(colour.get() + type.get() + message);
    }

    private static <Message> void print(Message message)
    {
        System.out.println(message + ColourCode.WHITE.get());
    }
}