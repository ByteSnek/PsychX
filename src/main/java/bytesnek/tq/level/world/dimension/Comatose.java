package bytesnek.tq.level.world.dimension;

import java.util.List;
import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.ITeleporter;

import bytesnek.tq.rego.Blocks;
import bytesnek.tq.rego.Levels;

import static net.minecraft.world.level.block.Blocks.AIR;
import static net.minecraft.world.level.material.Fluids.WATER;

/**
 * Created by SnakerBone on 29/07/2023
 **/
public class Comatose
{
    private static final List<Block> BLOCKS = List.of(Blocks.COMASTONE.get());

    public static Function<BlockPos, Teleporter> getTeleporter()
    {
        return Teleporter::new;
    }

    public static List<Block> getBlockList()
    {
        return BLOCKS;
    }

    public static class Teleporter implements ITeleporter
    {
        public BlockPos pos;

        private Teleporter(BlockPos pos)
        {
            this.pos = pos;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
        {
            entity = repositionEntity.apply(false);

            int y = 61;

            if (!isInsideComatose(currentWorld)) {
                y = pos.getY();
            }

            BlockPos destPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockPos suitablePos = findSuitablePos(destWorld, destPos);

            entity.setPos(suitablePos.getX(), suitablePos.getY(), suitablePos.getZ());

            if (entity instanceof ServerPlayer player) {
                if (player.level() instanceof ServerLevel level) {
                    if (level == destWorld) {
                        if (player.isUsingItem()) {
                            player.stopUsingItem();
                        }
                    }
                }
            }

            return entity;
        }

        @Override
        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld)
        {
            return false;
        }

        private BlockPos findSuitablePos(ServerLevel destWorld, BlockPos destPos)
        {
            int attemptCounter = 0;

            boolean notAir = destWorld.getBlockState(destPos).getBlock() != AIR;
            boolean aboveNotAir = destWorld.getBlockState(destPos.above()).getBlock() != AIR;
            boolean waterReplaceable = destWorld.getBlockState(destPos).canBeReplaced(WATER);
            boolean aboveWaterReplaceable = destWorld.getBlockState(destPos.above()).canBeReplaced(WATER);

            while (notAir && aboveNotAir && waterReplaceable && aboveWaterReplaceable && attemptCounter < 25) {
                destPos = destPos.above();
                attemptCounter++;
            }

            return new BlockPos(destPos.getX(), destPos.getY(), destPos.getZ());
        }

        private boolean isInsideComatose(ServerLevel currentWorld)
        {
            return currentWorld.dimension() == Levels.COMATOSE;
        }
    }
}
