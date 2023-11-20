package bytesnek.tq.utility;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 17/11/2023
 **/
public interface RegistryMapper
{
    Predicate<Block> NEGATE_LIQUID_BLOCK = block -> !(block instanceof LiquidBlock);

    default <T> Stream<T> map(DeferredRegister<T> register, IntFunction<T[]> function)
    {
        return Arrays.stream(register.getEntries()
                .stream()
                .map(RegistryObject::get)
                .toArray(function)
        );
    }

    default <T> Stream<T> map(DeferredRegister<T> register, Predicate<T> predicate, IntFunction<T[]> function)
    {
        return Arrays.stream(register.getEntries()
                .stream()
                .map(RegistryObject::get)
                .filter(predicate)
                .toArray(function)
        );
    }
}
