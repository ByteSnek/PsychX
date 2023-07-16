package snaker.snakerlib.data;

/**
 * Created by SnakerBone on 25/05/2023
 **/
public enum SnakerConstants
{
    MOVE_SPEED(0.25),
    FLY_SPEED(0.5),
    ATTACK_SPEED(1),
    FLY_FOLLOW_RANGE(64),
    FOLLOW_RANGE(16),
    SPEED_MODIFIER(1.35),
    CREATURE_XP_REWARD(8),
    MOB_XP_REWARD(16),
    BOSS_XP_REWARD(8000);

    final double value;

    SnakerConstants(double value)
    {
        this.value = value;
    }

    public int asInt()
    {
        return (int) value;
    }

    public float asFloat()
    {
        return (float) value;
    }

    public double asDouble()
    {
        return value;
    }
}
