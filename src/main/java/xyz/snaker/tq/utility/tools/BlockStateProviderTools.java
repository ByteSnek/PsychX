package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.ResourceStuff;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface BlockStateProviderTools<T extends BlockStateProvider>
{
    T getInstance();

    default void shader(Block block, Block particle)
    {
        String blockName = ResourceStuff.getPath(block);
        ResourceLocation shaderModel = getInstance().modLoc("shader");
        ResourceLocation particleTexture = getInstance().blockTexture(particle);

        ModelFile modelFile = getInstance().models()
                .withExistingParent(blockName, shaderModel)
                .texture("particle", particleTexture);

        getInstance().simpleBlock(block, modelFile);
    }

    default void cube(Block block)
    {
        String blockName = ResourceStuff.getPath(block);
        ResourceLocation blockTexture = getInstance().blockTexture(block);

        ModelFile modelFile = getInstance().models()
                .cubeAll(blockName, blockTexture)
                .renderType("cutout");

        getInstance().simpleBlock(block, modelFile);
    }

    default void log(Block block)
    {
        ResourceLocation blockTexture = getInstance().blockTexture(block);

        getInstance().axisBlock(UnsafeStuff.cast(block), blockTexture, blockTexture);
    }

    default void plant(Block plant)
    {
        String plantName = ResourceStuff.getPath(plant);
        ResourceLocation blockTexture = getInstance().blockTexture(plant);

        ModelFile modelFile = getInstance().models()
                .cross(plantName, blockTexture)
                .renderType("cutout");

        getInstance().simpleBlock(plant, modelFile);
    }

    default void leaves(Block leaves)
    {
        String leavesName = ResourceStuff.getPath(leaves);
        ResourceLocation blockTexture = getInstance().blockTexture(leaves);

        ModelFile modelFile = getInstance().models()
                .cubeAll(leavesName, blockTexture)
                .renderType("cutout");

        getInstance().simpleBlock(leaves, modelFile);
    }
}
