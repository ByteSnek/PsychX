package bytesnek.tq.datagen.provider;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import bytesnek.hiss.sneaky.Sneaky;
import bytesnek.snakerlib.resources.ResourceLocations;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.block.ShaderBlock;
import bytesnek.tq.rego.Blocks;
import bytesnek.tq.utility.RegistryMapper;

import static net.minecraft.world.level.block.Blocks.*;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class BlockStates extends BlockStateProvider implements RegistryMapper
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
        map(Blocks.REGISTER, Block[]::new).forEach(block ->
        {
            if (block instanceof ShaderBlock<?> shaderBlock) {
                addShaderBlock(shaderBlock);
                return;
            }

            if (block instanceof BushBlock bushBlock) {
                addPlantBlock(bushBlock);
                return;
            }

            if (block instanceof LeavesBlock leavesBlock) {
                addLeavesBlock(leavesBlock);
                return;
            }

            if (block instanceof RotatedPillarBlock rotatedPillarBlock) {
                addLogBlock(rotatedPillarBlock);
                return;
            }

            addCubeBlock(block);
        });
    }

    private void addShaderBlock(Block block)
    {
        String blockName = ResourceLocations.getPath(block);
        ResourceLocation shaderModel = modLoc("shader");
        ResourceLocation particleTexture = blockTexture(BLOCK_2_PARTICLE.get(block));

        ModelFile modelFile = models()
                .withExistingParent(blockName, shaderModel)
                .texture("particle", particleTexture);

        simpleBlock(block, modelFile);
    }

    private void addPlantBlock(Block block)
    {
        String plantName = ResourceLocations.getPath(block);
        ResourceLocation blockTexture = blockTexture(block);

        ModelFile modelFile = models()
                .cross(plantName, blockTexture)
                .renderType("cutout");

        simpleBlock(block, modelFile);
    }

    private void addLeavesBlock(Block leaves)
    {
        String leavesName = ResourceLocations.getPath(leaves);
        ResourceLocation blockTexture = blockTexture(leaves);

        ModelFile modelFile = models()
                .cubeAll(leavesName, blockTexture)
                .renderType("cutout");

        simpleBlock(leaves, modelFile);
    }

    private void addLogBlock(Block block)
    {
        ResourceLocation blockTexture = blockTexture(block);

        axisBlock(Sneaky.cast(block), blockTexture, blockTexture);
    }

    private void addCubeBlock(Block block)
    {
        String blockName = ResourceLocations.getPath(block);
        ResourceLocation blockTexture = blockTexture(block);

        if (block instanceof LiquidBlock) {
            addCubeBlockDummy(block);
            return;
        }

        ModelFile modelFile = models()
                .cubeAll(blockName, blockTexture);

        simpleBlock(block, modelFile);
    }

    private void addCubeBlockDummy(Block block)
    {
        String blockName = ResourceLocations.getPath(block);
        ResourceLocation blockTexture = modLoc(ModelProvider.BLOCK_FOLDER + "/dummy");

        ModelFile modelFile = models()
                .cubeAll(blockName, blockTexture);

        simpleBlock(block, modelFile);
    }
}
