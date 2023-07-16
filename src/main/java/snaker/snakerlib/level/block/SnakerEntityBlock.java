package snaker.snakerlib.level.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import snaker.snakerlib.utility.SnakerUtil;

import java.util.function.Function;

/**
 * Created by SnakerBone on 27/06/2023
 **/
public abstract class SnakerEntityBlock extends BaseEntityBlock
{
    public SnakerEntityBlock(MapColor colour, SoundType sound, float strength)
    {
        super(Properties.of().mapColor(colour).sound(sound).strength(strength));
    }

    public SnakerEntityBlock(MapColor colour, Function<Properties, Properties> properties)
    {
        super(properties.apply(Properties.of().mapColor(colour)));
    }

    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> typeA, BlockEntityType<E> typeB, BlockEntityTicker<? super E> ticker)
    {
        return typeA == typeB ? SnakerUtil.shutUp(ticker) : null;
    }
}
