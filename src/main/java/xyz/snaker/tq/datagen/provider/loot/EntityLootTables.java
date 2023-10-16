package xyz.snaker.tq.datagen.provider.loot;

import java.util.stream.Stream;

import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Rego;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 16/10/2023
 **/
public class EntityLootTables extends EntityLootSubProvider
{
    public EntityLootTables()
    {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate()
    {
        add(Entities.COSMIC_CREEPERITE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .add(LootItem.lootTableItem(Items.ENDER_PEARL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                )
                .withPool(LootPool.lootPool().add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER,
                                EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS))
                        )
                )
        );

        add(Entities.FLARE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BLAZE_ROD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .add(LootItem.lootTableItem(Items.BLAZE_POWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                        )
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
        );
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes()
    {
        return UnsafeStuff.cast(Entities.REGISTER.getEntries().stream().map(RegistryObject::get).filter(Rego.entitiesWithLootTables()));
    }
}
