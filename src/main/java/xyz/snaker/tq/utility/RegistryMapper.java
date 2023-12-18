package xyz.snaker.tq.utility;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import xyz.snaker.hiss.logger.Logger;
import xyz.snaker.hiss.logger.Loggers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Created by SnakerBone on 17/11/2023
 **/
public interface RegistryMapper
{
    Logger LOGGER = Loggers.getLogger();
    Predicate<Block> NEGATE_LIQUID_BLOCK = block -> !(block instanceof LiquidBlock);

    default <T> Stream<T> map(DeferredRegister<T> register, IntFunction<T[]> function)
    {
        return Arrays.stream(register.getEntries()
                .stream()
                .map(Supplier::get)
                .toArray(function)
        );
    }

    default <T> Stream<T> map(DeferredRegister<T> register, Predicate<T> predicate, IntFunction<T[]> function)
    {
        return Arrays.stream(register.getEntries()
                .stream()
                .map(Supplier::get)
                .filter(predicate)
                .toArray(function)
        );
    }
}
