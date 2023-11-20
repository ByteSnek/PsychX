package bytesnek.tq.level.block.entity.shader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import bytesnek.tq.level.block.entity.ShaderBlockEntity;
import bytesnek.tq.rego.BlockEntities;

/**
 * Created by SnakerBone on 14/08/2023
 **/
public class SnowflakeBlockEntity extends ShaderBlockEntity<SnowflakeBlockEntity>
{
    public SnowflakeBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.SNOWFLAKE.get(), pos, state);
    }
}
