package xyz.snaker.tq.level.block.entity.shader;

import xyz.snaker.tq.level.block.entity.ShaderBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import xyz.snaker.tq.rego.BlockEntities;

/**
 * Created by SnakerBone on 14/08/2023
 **/
public class GeometricBlockEntity extends ShaderBlockEntity<GeometricBlockEntity>
{
    public GeometricBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.GEOMETRIC.get(), pos, state);
    }
}
