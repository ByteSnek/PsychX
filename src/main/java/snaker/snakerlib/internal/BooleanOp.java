package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 2/07/2023
 **/
public interface BooleanOp
{
    BooleanOp TRUE = (a, b) -> true;
    BooleanOp FALSE = (a, b) -> false;
    BooleanOp OR = (a, b) -> a || b;
    BooleanOp XOR = (a, b) -> a ^ b;
    BooleanOp NOT_OR = (a, b) -> !a && !b;
    BooleanOp EQUAL = (a, b) -> a == b;
    BooleanOp NOT_EQUAL = (a, b) -> a != b;
    BooleanOp AND = (a, b) -> a && b;
    BooleanOp NOT_AND = (a, b) -> !a || !b;

    boolean apply(Boolean a, Boolean b);

    default boolean apply(Boolean a)
    {
        return apply(a, a);
    }
}
