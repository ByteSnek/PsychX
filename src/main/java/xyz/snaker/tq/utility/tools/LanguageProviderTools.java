package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.ResourceStuff;
import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface LanguageProviderTools<T extends LanguageProvider>
{
    String ATLAS_ELEMENT = "atlas.tq.element.";

    T getInstance();

    default void tab(CreativeModeTab tab)
    {
        String name = tab.getDisplayName().getString();
        addDirect(name, StringStuff.i18nt(name.substring(name.indexOf('.') + 1)));
    }

    default <I extends EntityType<?>> void entity(I entity)
    {
        String name = ResourceStuff.getPath(entity);
        if (name.equals("cosmo")) {
            addDirect("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
            addDirect("entity." + Tourniqueted.MODID + "." + "alpha_" + name, StringStuff.i18nt("alpha_" + name));
            return;
        }
        addDirect("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Item> void item(I item)
    {
        String name = ResourceStuff.getPath(item);
        if (name.equals("atlas")) {
            name = Tourniqueted.NAME.toLowerCase() + "_" + name;
        }
        addDirect("item." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Block> void block(I block)
    {
        String name = ResourceStuff.getPath(block);
        addDirect("block." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default void sound(SoundEvent sound)
    {
        String name = ResourceStuff.getPath(sound);
        addDirect("sounds." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends MobEffect> void effect(I effect)
    {
        String name = ResourceStuff.getPath(effect);
        addDirect("effect." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default void addAtlasTranslations()
    {
        addAtlasElement("biomes");
        addAtlasElement("items");
        addAtlasElement("mobs");
        addAtlasElement("conversions");
        addAtlasElement("title", "Tourniqueted Atlas");

        addDirect("item.tq.cosmo_spine", "Cosmo Spine");
        addDirect("level.tq.comatose", "Comatose");
        addDirect("level.tq.comatose_dimension", "Comatose Dimension");
        addDirect("level.tq.comatose_biomes", "Comatose Biomes");
    }

    default void addMiscTranslations()
    {
        addDirect("commands.tq.config_set_success", "Config Set");
        addDirect("commands.tq.force_removal_success", "Successfully force removed you from the world");
        addDirect("message.tq.health_repair_success", "Successfully repaired your health");
    }

    default void addAtlasElement(String key)
    {
        addAtlasElement(key, StringStuff.i18nt(key));
    }

    default void addAtlasElement(String key, String value)
    {
        addDirect(ATLAS_ELEMENT.concat(key), value);
    }

    default void addDirect(String key, String value)
    {
        getInstance().add(key, value);
    }
}
