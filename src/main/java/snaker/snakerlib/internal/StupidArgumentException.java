package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 1/07/2023
 **/
public class StupidArgumentException extends RuntimeException
{
    public StupidArgumentException()
    {
        super("The mod dev who caused this crash may or may not be mentally retarded. Please check the logs and go report this to them");
    }
}
