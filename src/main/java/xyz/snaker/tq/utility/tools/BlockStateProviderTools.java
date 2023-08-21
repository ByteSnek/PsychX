package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface BlockStateProviderTools<T extends BlockStateProvider>
{
    T getInstance();

    default void shader(RegistryObject<Block> block)
    {
        String name = block.getId().getPath();
        ModelFile file = getInstance().models().withExistingParent(name, getInstance().modLoc("shader"));
        getInstance().simpleBlock(block.get(), file);
    }

    default void shaderWithLayer(RegistryObject<Block> block, RegistryObject<Block> texture)
    {
        String name = block.getId().getPath();
        ModelFile file = getInstance().models()
                .withExistingParent(name, getInstance().modLoc("shader"))
                .texture("layer0", getInstance().blockTexture(texture.get()));
        getInstance().simpleBlock(block.get(), file);
    }

    default void nylium(RegistryObject<Block> block)
    {
        String name = block.getId().getPath();
        ModelFile file = getInstance().models()
                .withExistingParent(name, getInstance().mcLoc("cube_bottom_top"))
                .texture("side", "block/" + name + "_side")
                .texture("top", "block/" + name + "_top")
                .texture("bottom", "block/coma_stone");
        getInstance().simpleBlock(block.get(), file);
    }

    default void cube(RegistryObject<Block> block)
    {
        String name = block.getId().getPath();
        ModelFile file = getInstance().models().cubeAll(name, getInstance().blockTexture(block.get())).renderType("cutout");
        getInstance().simpleBlock(block.get(), file);
    }

    default void log(RegistryObject<Block> block)
    {
        getInstance().logBlock(UnsafeStuff.cast(block.get()));
    }

    default void plant(RegistryObject<Block> plant, RegistryObject<Block> potted)
    {
        ResourceLocation plantVariant = plant.getId();
        ResourceLocation pottedVariant = potted.getId();

        String plantName = plantVariant.getPath();
        String pottedName = pottedVariant.getPath();

        getInstance().simpleBlock(plant.get(), getInstance().models()
                .cross(plantName, getInstance().blockTexture(plant.get()))
                .renderType("cutout"));

        getInstance().simpleBlock(potted.get(), getInstance().models()
                .withExistingParent(pottedName, getInstance().mcLoc("flower_pot_cross"))
                .renderType("cutout")
                .texture("plant", "block/" + plantName));
    }

    default void plant(RegistryObject<Block> plant)
    {
        ResourceLocation plantVariant = plant.getId();
        String plantName = plantVariant.getPath();

        getInstance().simpleBlock(plant.get(), getInstance().models()
                .cross(plantName, getInstance().blockTexture(plant.get()))
                .renderType("cutout"));
    }
}
