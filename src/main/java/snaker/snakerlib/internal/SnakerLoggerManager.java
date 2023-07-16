package snaker.snakerlib.internal;

import java.util.Map;

/**
 * Created by SnakerBone on 9/07/2023
 **/
public class SnakerLoggerManager
{
    private static boolean logStatus;
    private final Map<Class<?>, SnakerLogger> map = new AsynchronousHashMap<>();

    private SnakerLoggerManager()
    {
        
    }

    public static SnakerLogger getLogger(Class<?> forClass)
    {
        if (!Holder.INSTANCE.map.containsKey(forClass)) {
            Holder.INSTANCE.map.put(forClass, SnakerLogger.INSTANCE);
        }
        return Holder.INSTANCE.map.get(forClass);
    }

    public static boolean setLoggerStatus(boolean status)
    {
        return logStatus = status;
    }

    public static boolean getLogStatus()
    {
        return logStatus;
    }

    public static boolean invertLoggerStatus()
    {
        return logStatus ^= true;
    }

    private static class Holder
    {
        private static final SnakerLoggerManager INSTANCE = new SnakerLoggerManager();
    }
}
