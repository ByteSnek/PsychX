package xyz.snaker.tq.level.block.entity.shader;

import xyz.snaker.tq.level.block.entity.ShaderBlockEntity;
import xyz.snaker.tq.rego.BlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class BurningBlockEntity extends ShaderBlockEntity<BurningBlockEntity>
{
    public BurningBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.BURNING.get(), pos, state);
    }
}
