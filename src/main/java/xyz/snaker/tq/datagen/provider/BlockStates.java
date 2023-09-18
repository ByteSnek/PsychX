package xyz.snaker.tq.datagen.provider;

import java.util.HashMap;
import java.util.Map;

import xyz.snaker.snakerlib.utility.tools.CollectionStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.utility.tools.BlockStateProviderTools;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.minecraft.world.level.block.Blocks.*;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockStates extends BlockStateProvider implements BlockStateProviderTools<BlockStates>
{
    static final Map<Block, Block> BLOCK_2_PARTICLE = Util.make(new HashMap<>(), map ->
    {
        map.put(Blocks.SWIRL.get(), YELLOW_CONCRETE_POWDER);
        map.put(Blocks.SNOWFLAKE.get(), LIGHT_BLUE_CONCRETE_POWDER);
        map.put(Blocks.WATERCOLOUR.get(), PINK_CONCRETE_POWDER);
        map.put(Blocks.MULTICOLOUR.get(), LIME_CONCRETE_POWDER);
        map.put(Blocks.FLARE.get(), BROWN_CONCRETE_POWDER);
        map.put(Blocks.STARRY.get(), WHITE_CONCRETE_POWDER);
        map.put(Blocks.GEOMETRIC.get(), BLACK_CONCRETE_POWDER);
        map.put(Blocks.BURNING.get(), BLACK_CONCRETE_POWDER);
        map.put(Blocks.FOGGY.get(), MAGENTA_CONCRETE_POWDER);
        map.put(Blocks.STATIC.get(), GRAY_CONCRETE);
    });

    public BlockStates(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Tourniqueted.MODID, helper);
    }

    @Override
    public void registerStatesAndModels()
    {
        CollectionStuff.mapDeferredRegistries(Blocks.REGISTRAR, Block[]::new).forEach(block -> {
            if (block instanceof ShaderBlock<?> shaderBlock) {
                shader(shaderBlock, BLOCK_2_PARTICLE.get(shaderBlock));
            } else if (block instanceof BushBlock bushBlock) {
                plant(bushBlock);
            } else if (block instanceof RotatedPillarBlock rotatedPillarBlock) {
                log(rotatedPillarBlock);
            } else {
                cube(block);
            }
        });
    }

    @Override
    public BlockStates getInstance()
    {
        return this;
    }
}
