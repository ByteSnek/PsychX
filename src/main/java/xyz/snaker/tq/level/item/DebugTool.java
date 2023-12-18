package xyz.snaker.tq.level.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import xyz.snaker.snakerlib.utility.RegistryStreams;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.entity.crystal.ComaCrystal;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.*;

/**
 * Created by SnakerBone on 27/11/2023
 **/
public class DebugTool extends EmptyItem implements DebugItem
{
    public static final List<BlockPos> POSITIONS = new ArrayList<>();

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
    {
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context)
    {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = CRYING_OBSIDIAN.defaultBlockState();

        if (level.isClientSide || player == null) {
            return InteractionResult.PASS;
        }

        if (player.isShiftKeyDown()) {
            ComaCrystal crystal = new ComaCrystal(Entities.COMA_CRYSTAL.get(), level);

            crystal.moveTo(pos.above(1).getCenter().subtract(0, -0.5, 0));
            level.addFreshEntity(crystal);

            return InteractionResult.SUCCESS;
        }

        return place(player, level, pos, state, 65);
    }

    public InteractionResult place(Player player, Level level, BlockPos pos, BlockState state, int radius)
    {
        InteractionResult result = InteractionResult.PASS;

        double playerX = player.getX();
        double playerZ = player.getZ();

        for (int i = 0; i < 8; i++) {
            double angle = (i * Math.PI / 4);

            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);

            int circleX = (int) (playerX + xOffset);
            int circleZ = (int) (playerZ + zOffset);

            result = placeBase(level, new BlockPos(circleX, pos.getY() + 28, circleZ), state, 8);
        }

        return result;
    }

    public InteractionResult placeBlock(Level level, BlockPos pos, BlockState state)
    {
        if (level.setBlock(pos, state, Block.UPDATE_ALL_IMMEDIATE)) {
            POSITIONS.add(pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public InteractionResult placeBase(Level level, BlockPos origin, BlockState state, int radius)
    {
        AtomicReference<InteractionResult> result = new AtomicReference<>(InteractionResult.PASS);
        RandomSource random = level.random;

        for (int i = 0; i < 20; i += 3) {
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            bolt.setPos(origin.getX() - 2 + random.nextInt(5), origin.getY() - random.nextInt(20), origin.getZ() - 2 + random.nextInt(5));
            level.addFreshEntity(bolt);
        }

        BlockPos.betweenClosedStream(origin.offset(-radius, -25, -radius), origin.offset(radius, 4, radius)).forEach(pos ->
        {
            if (pos.getY() < origin.getY()) {
                double percent = (double) (origin.getY() - pos.getY()) / 25.;

                if (getDistance(pos.getX(), pos.getZ(), origin.getX(), origin.getZ()) <= radius + 0.5) {
                    if (1. - percent > random.nextDouble()) {
                        float block = random.nextFloat();

                        if (block < 0.05) {
                            placeRandomShaderBlock(level, pos);
                        } else if (block < 0.1) {
                            placeBlock(level, pos, NETHER_BRICKS.defaultBlockState());
                        } else if (block < 0.4) {
                            placeBlock(level, pos, AIR.defaultBlockState());
                        } else if (block < 0.6) {
                            placeBlock(level, pos, Blocks.COMA_SOIL.get().defaultBlockState());
                        } else if (block < 0.8) {
                            result.set(placeBlock(level, pos, state));
                        }
                    }
                }
            }

            int relY = pos.getY() - origin.getY();
            int absRelX = Math.abs(pos.getX() - origin.getX());
            int absRelZ = Math.abs(pos.getZ() - origin.getZ());

            if ((absRelX == 2 || absRelZ == 2) && absRelX <= 2 && absRelZ <= 2 && relY < 4 && relY > -1) {
                placeBlock(level, pos, IRON_BARS.defaultBlockState());
            }

            if (relY == 4 && absRelX <= 2 && absRelZ <= 2) {
                placeBlock(level, pos, NETHER_BRICK_SLAB.defaultBlockState());
            }

            if (relY == 0 && absRelX == 0 && absRelZ == 0) {
                Vec3 position = new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                ComaCrystal crystal = new ComaCrystal(Entities.COMA_CRYSTAL.get(), level);
                crystal.setPos(position);
                level.addFreshEntity(crystal);
            }
        });

        return result.get();
    }

    public InteractionResult placeRandomShaderBlock(Level level, BlockPos pos)
    {
        List<Block> blocks = RegistryStreams.mapDeferredRegistries(Blocks.REGISTER, Block[]::new)
                .filter(block -> block instanceof ShaderBlock<?>)
                .toList();
        int index = level.random.nextInt(blocks.size());
        Block block = blocks.get(index);

        return placeBlock(level, pos, block.defaultBlockState());
    }

    public double getDistance(double startX, double startZ, double endX, double endZ)
    {
        double x = startX - endX;
        double z = startZ - endZ;

        return Math.sqrt(x * x + z * z);
    }
}
