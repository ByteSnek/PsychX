package xyz.snaker.tq.datagen.provider;

import java.util.Locale;

import xyz.snaker.hiss.utility.Strings;
import xyz.snaker.hiss.utility.Translations;
import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.RegistryMapper;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Languages extends LanguageProvider implements RegistryMapper
{
    public Languages(PackOutput output)
    {
        super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
    }

    @Override
    public void addTranslations()
    {
        map(Blocks.REGISTER, Block[]::new).forEach(this::addBlockTranslation);
        map(Items.REGISTER, Item[]::new).forEach(this::addItemTranslation);
        map(Entities.REGISTER, EntityType<?>[]::new).forEach(this::addEntityTranslation);
        map(Sounds.REGISTER, SoundEvent[]::new).forEach(this::addSoundTranslation);
        map(Tabs.REGISTER, CreativeModeTab[]::new).forEach(this::addCreativeTabTranslation);
        map(Effects.REGISTER, MobEffect[]::new).forEach(this::addEffectTranslation);

        addDamageTypeTranslation(DamageTypes.COSMIC, "%1$s was killed by a cosmic ray");

        add("command.force_removal_success", "Successfully force removed you from the world");
        add("message.health_repair_success", "Successfully repaired your health");
        add("flat_world_preset.iron", "Iron");
        add("flat_world_preset.concrete", "White Concrete");
        add("flat_world_preset.obsidian", "Obsidian");
        add("flat_world_preset.crying_obsidian", "Crying Obsidian");
    }

    private void addDamageTypeTranslation(ResourceKey<DamageType> key, String value)
    {
        add("death.attack." + key.location().getPath(), value);
    }

    private <T extends Block> void addBlockTranslation(T block)
    {
        String name = ResourceLocations.BLOCK.getPath(block);

        add("block." + name, Strings.i18nt(name));
    }

    private <T extends Item> void addItemTranslation(T item)
    {
        String name = ResourceLocations.ITEM.getPath(item);

        if (name.equals("atlas")) {
            add("item." + name, Strings.i18nt(Tourniqueted.NAME.toLowerCase() + "_" + name));

            name = Tourniqueted.NAME.toLowerCase() + "_" + name;
        }

        add("item." + name, Strings.i18nt(name));
    }

    private <T extends EntityType<?>> void addEntityTranslation(T entity)
    {
        String name = ResourceLocations.ENTITY_TYPE.getPath(entity);

        if (name.equals("cosmo")) {
            add("entity." + name, Strings.i18nt(name));
            add("entity." + "alpha_" + name, Strings.i18nt("alpha_" + name));

            return;
        }

        add("entity." + name, Strings.i18nt(name));
    }

    private void addSoundTranslation(SoundEvent sound)
    {
        String name = ResourceLocations.SOUND_EVENT.getPath(sound);

        add("sounds." + name, Strings.i18nt(name));
    }

    private <I extends MobEffect> void addEffectTranslation(I effect)
    {
        String name = ResourceLocations.MOB_EFFECT.getPath(effect);

        add("effect." + name, Strings.i18nt(name));
    }

    private void addCreativeTabTranslation(CreativeModeTab tab)
    {
        String name = tab.getDisplayName().getString();

        add(name, Strings.i18nt(name.substring(name.indexOf('.') + 1)));
    }

    @Override
    public void add(@NotNull String key, @NotNull String name)
    {
        super.add(Translations.joinKey(key, Tourniqueted.MODID, "itemGroup"), name);
    }
}
