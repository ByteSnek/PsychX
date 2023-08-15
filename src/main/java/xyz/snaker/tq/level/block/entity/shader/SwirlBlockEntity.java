package xyz.snaker.tq.level.block.entity.shader;

import xyz.snaker.tq.level.block.entity.ShaderBlockEntity;
import xyz.snaker.tq.rego.BlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by SnakerBone on 14/08/2023
 **/
public class SwirlBlockEntity extends ShaderBlockEntity<SwirlBlockEntity>
{
    public SwirlBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.SWIRL.get(), pos, state);
    }
}
