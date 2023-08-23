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

    default void shader(Block block)
    {
        String name = ResourceStuff.getPath(block);
        ModelFile file = getInstance().models().withExistingParent(name, getInstance().modLoc("shader"));
        getInstance().simpleBlock(block, file);
    }

    default void cube(Block block)
    {
        String name = ResourceStuff.getPath(block);
        ModelFile file = getInstance().models().cubeAll(name, getInstance().blockTexture(block)).renderType("cutout");
        getInstance().simpleBlock(block, file);
    }

    default void log(Block block)
    {
        getInstance().logBlock(UnsafeStuff.cast(block));
    }

    default void plant(Block plant)
    {
        ResourceLocation plantVariant = ResourceStuff.getResourceLocation(plant);
        String plantName = plantVariant.getPath();

        getInstance().simpleBlock(plant, getInstance().models()
                .cross(plantName, getInstance().blockTexture(plant))
                .renderType("cutout"));
    }
}
