package xyz.snaker.tq.datagen.provider;

import java.util.Locale;

import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.snakerlib.utility.Streams;
import xyz.snaker.snakerlib.utility.Strings;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.*;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Languages extends LanguageProvider
{
    public Languages(PackOutput output)
    {
        super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
    }

    @Override
    public void addTranslations()
    {
        Streams.mapDeferredRegistries(Blocks.REGISTER, Block[]::new).forEach(this::addBlockTranslation);
        Streams.mapDeferredRegistries(Items.REGISTER, Item[]::new).forEach(this::addItemTranslation);
        Streams.mapDeferredRegistries(Entities.REGISTER, EntityType<?>[]::new).forEach(this::addEntityTranslation);
        Streams.mapDeferredRegistries(Sounds.REGISTER, SoundEvent[]::new).forEach(this::addSoundTranslation);
        Streams.mapDeferredRegistries(Tabs.REGISTER, CreativeModeTab[]::new).forEach(this::addCreativeTabTranslation);
        Streams.mapDeferredRegistries(Effects.REGISTER, MobEffect[]::new).forEach(this::addEffectTranslation);

        add("command.force_removal_success", "Successfully force removed you from the world");
        add("message.health_repair_success", "Successfully repaired your health");
        add("flat_world_preset.iron", "BJ Howes Metaland");
        add("flat_world_preset.concrete", "Northbound M1 Motorway");
    }

    private <T extends Block> void addBlockTranslation(T block)
    {
        String name = ResourceLocations.getPath(block);

        add("block." + name, Strings.i18nt(name));
    }

    private <T extends Item> void addItemTranslation(T item)
    {
        String name = ResourceLocations.getPath(item);

        if (name.equals("atlas")) {
            add("item." + name, Strings.i18nt(Tourniqueted.NAME.toLowerCase() + "_" + name));

            name = Tourniqueted.NAME.toLowerCase() + "_" + name;
        }

        add("item." + name, Strings.i18nt(name));
    }

    private <T extends EntityType<?>> void addEntityTranslation(T entity)
    {
        String name = ResourceLocations.getPath(entity);

        if (name.equals("cosmo")) {
            add("entity." + name, Strings.i18nt(name));
            add("entity." + "alpha_" + name, Strings.i18nt("alpha_" + name));

            return;
        }

        add("entity." + name, Strings.i18nt(name));
    }

    private void addSoundTranslation(SoundEvent sound)
    {
        String name = ResourceLocations.getPath(sound);

        add("sounds." + name, Strings.i18nt(name));
    }

    private void addCreativeTabTranslation(CreativeModeTab tab)
    {
        String name = tab.getDisplayName().getString();

        add(name, Strings.i18nt(name.substring(name.indexOf('.') + 1)));
    }

    private <I extends MobEffect> void addEffectTranslation(I effect)
    {
        String name = ResourceLocations.getPath(effect);

        add("effect." + name, Strings.i18nt(name));
    }

    @Override
    public void add(@NotNull String key, @NotNull String value)
    {
        super.add(Tourniqueted.MODID + "." + key, value);
    }
}
