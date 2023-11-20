package bytesnek.tq.level.block.entity.shader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import bytesnek.tq.level.block.entity.ShaderBlockEntity;
import bytesnek.tq.rego.BlockEntities;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class FoggyBlockEntity extends ShaderBlockEntity<FoggyBlockEntity>
{
    public FoggyBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.FOGGY.get(), pos, state);
    }
}
