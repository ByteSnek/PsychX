package xyz.snaker.tq.level.item;

import java.util.concurrent.atomic.AtomicReference;

import xyz.snaker.tq.level.entity.crystal.ComaCrystal;
import xyz.snaker.tq.level.entity.crystal.ComaCrystalLightningBolt;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.CRYING_OBSIDIAN;
import static net.minecraft.world.level.block.Blocks.OBSIDIAN;

/**
 * Created by SnakerBone on 8/12/2023
 **/
public class ComaCrystalItem extends EmptyItem
{
    private final AtomicReference<InteractionResult> resultReference = new AtomicReference<>(InteractionResult.PASS);
    private final AtomicReference<Level> levelReference = new AtomicReference<>();

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context)
    {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        levelReference.set(level);

        if (player != null) {
            ItemStack stack = player.getMainHandItem();

            if (context.getClickedFace() != Direction.UP || !stack.is(Items.COMA_CRYSTAL.get())) {
                return resultReference.get();
            }

            BlockPos.betweenClosed(pos.below(2).south().west(), pos.north().east()).forEach(this::setRandomBlock);
            BlockPos above = pos.above(2);

            double posX = above.getX() + 0.5;
            double posY = above.getY();
            double posZ = above.getZ() + 0.5;

            ComaCrystal crystal = new ComaCrystal(level, posX, posY, posZ);

            level.gameEvent(crystal, GameEvent.ENTITY_PLACE, above);

            if (level.addFreshEntity(crystal)) {
                for (int i = 0; i < 5; i++) {
                    if (!level.isClientSide) {
                        ComaCrystalLightningBolt bolt = new ComaCrystalLightningBolt(Entities.COMA_CRYSTAL_LIGHTNING_BOLT.get(), level);

                        bolt.setPos(posX, posY, posZ);

                        level.addFreshEntity(bolt);
                    }
                }

                stack.shrink(1);
                resultReference.set(InteractionResult.SUCCESS);
            }
        }

        return resultReference.get();
    }

    public void setRandomBlock(BlockPos pos)
    {
        Level level = levelReference.get();
        RandomSource random = level.random;
        Block block;

        if (level.isClientSide) {
            return;
        }

        if (random.nextFloat() < 0.25) {
            block = OBSIDIAN;
        } else if (random.nextFloat() < 0.25) {
            block = CRYING_OBSIDIAN;
        } else if (random.nextFloat() < 0.25) {
            block = Blocks.COMA_SOIL.get();
        } else {
            block = level.getBlockState(pos).getBlock();
        }

        if (level.setBlockAndUpdate(pos, block.defaultBlockState())) {
            resultReference.set(InteractionResult.SUCCESS);
        }
    }
}
