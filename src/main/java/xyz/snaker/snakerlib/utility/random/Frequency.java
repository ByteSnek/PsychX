package xyz.snaker.snakerlib.utility.random;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Frequency
{
    public static final Frequency ALWAYS = new Frequency(0);
    public static final Frequency NEARLY_ALWAYS = new Frequency(1);
    public static final Frequency ALMOST_ALWAYS = new Frequency(2);
    public static final Frequency SLIGHTLY_ALWAYS = new Frequency(4);
    public static final Frequency KIND_OF_ALWAYS = new Frequency(8);
    public static final Frequency BARELY_ALWAYS = new Frequency(16);
    public static final Frequency OFTEN = new Frequency(32);
    public static final Frequency NEARLY_OFTEN = new Frequency(64);
    public static final Frequency KIND_OF_OFTEN = new Frequency(128);
    public static final Frequency BARELY_OFTEN = new Frequency(256);
    public static final Frequency SOMETIMES = new Frequency(512);
    public static final Frequency NEARLY_SOMETIMES = new Frequency(1024);
    public static final Frequency KIND_OF_SOMETIMES = new Frequency(2048);
    public static final Frequency BARELY_SOMETIMES = new Frequency(4096);
    public static final Frequency RARELY = new Frequency(8192);
    public static final Frequency NEARLY_RARELY = new Frequency(16384);
    public static final Frequency KIND_OF_RARELY = new Frequency(32768);
    public static final Frequency BARELY_RARELY = new Frequency(65536);
    public static final Frequency HARDLY = new Frequency(131072);
    public static final Frequency NEARLY_HARDLY = new Frequency(262144);
    public static final Frequency KIND_OF_HARDLY = new Frequency(524288);
    public static final Frequency BARELY_HARDLY = new Frequency(1048576);
    public static final Frequency IMPOSSIBLE = new Frequency(2097152);
    public static final Frequency VERY_IMPOSSIBLE = new Frequency(4194304);
    public static final Frequency REALLY_IMPOSSIBLE = new Frequency(8388608);
    public static final Frequency EXTREMELY_IMPOSSIBLE = new Frequency(16777216);
    public static final Frequency NEVER = new Frequency(2147483647);
    public static final Frequency NEARLY_NEVER = new Frequency(1073741824);
    public static final Frequency KIND_OF_NEVER = new Frequency(536870912);
    public static final Frequency BARELY_NEVER = new Frequency(268435456);

    private int value;

    private Frequency(int value)
    {
        this.value = value;
    }

    public static Frequency of(int value)
    {
        return new Frequency(value);
    }

    public int getValue()
    {
        return value;
    }
}
