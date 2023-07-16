package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 2/07/2023
 **/
public interface BitwiseOp
{
    BitwiseOp AND = (a, b) -> a & b;
    BitwiseOp OR = (a, b) -> a | b;
    BitwiseOp XOR = (a, b) -> a ^ b;
    BitwiseOp NOT_AND = (a, b) -> ~a & ~b;
    BitwiseOp NOT_OR = (a, b) -> ~a | ~b;
    BitwiseOp NOT_XOR = (a, b) -> ~a ^ ~b;
    BitwiseOp SIGNED_RIGHT = (a, b) -> a >> b;
    BitwiseOp UNSIGNED_RIGHT = (a, b) -> a >>> b;
    BitwiseOp SIGNED_LEFT = (a, b) -> a << b;

    int apply(int a, int b);
}
