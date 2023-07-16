package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum MarkerType
{
    NONE(null),
    MESSAGE("[SnakerLib/MESSAGE]: "),
    INFO("[SnakerLib/INFO]: "),
    DEBUG("[SnakerLib/DEBUG]: "),
    CATCHING("[SnakerLib/CATCHING]: "),
    SYSTEM("[SnakerLib/SYSTEM]: "),
    THREAD("[SnakerLib/THREAD]: "),
    WORKER("[SnakerLib/WORKER]: "),
    TRACE("[SnakerLib/TRACE]: "),
    DEV("[SnakerLib/DEV]: "),
    WARN("[SnakerLib/WARN]: "),
    ERROR("[SnakerLib/ERROR]: "),
    FATAL("[SnakerLib/FATAL]: ");

    final String value;

    MarkerType(String value)
    {
        this.value = value;
    }

    public String get()
    {
        return value == null ? "" : value;
    }
}
