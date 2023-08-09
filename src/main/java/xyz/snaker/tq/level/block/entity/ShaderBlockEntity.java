package xyz.snaker.tq.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import xyz.snaker.snakerlib.level.block.entity.SnakerBlockEntity;
import xyz.snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockEntity<T extends SnakerBlockEntity> extends SnakerBlockEntity
{
    public final RegistryObject<BlockEntityType<T>> blockEntity;

    public ShaderBlockEntity(RegistryObject<BlockEntityType<T>> type, BlockPos pos, BlockState state)
    {
        super(type.get(), pos, state);
        blockEntity = type;
    }

    public static class Flare extends ShaderBlockEntity<Flare>
    {
        public Flare(BlockPos pos, BlockState state)
        {
            super(Rego.BE_FLARE, pos, state);
        }
    }

    public static class MultiColour extends ShaderBlockEntity<MultiColour>
    {
        public MultiColour(BlockPos pos, BlockState state)
        {
            super(Rego.BE_MULTICOLOUR, pos, state);
        }
    }

    public static class Snowflake extends ShaderBlockEntity<Snowflake>
    {
        public Snowflake(BlockPos pos, BlockState state)
        {
            super(Rego.BE_SNOWFLAKE, pos, state);
        }
    }

    public static class Starry extends ShaderBlockEntity<Starry>
    {
        public Starry(BlockPos pos, BlockState state)
        {
            super(Rego.BE_STARRY, pos, state);
        }
    }

    public static class Swirl extends ShaderBlockEntity<Swirl>
    {
        public Swirl(BlockPos pos, BlockState state)
        {
            super(Rego.BE_SWIRL, pos, state);
        }
    }

    public static class WaterColour extends ShaderBlockEntity<WaterColour>
    {
        public WaterColour(BlockPos pos, BlockState state)
        {
            super(Rego.BE_WATERCOLOUR, pos, state);
        }
    }
}
