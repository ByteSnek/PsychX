package snaker.snakerlib.level.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import snaker.snakerlib.utility.SketchyStuff;

/**
 * Created by SnakerBone on 27/06/2023
 **/
public abstract class SnakerEntityBlock extends BaseEntityBlock
{
    public SnakerEntityBlock(Properties properties)
    {
        super(properties);
    }

    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> a, BlockEntityType<E> b, BlockEntityTicker<? super E> ticker)
    {
        return a == b ? SketchyStuff.cast(ticker) : null;
    }
}
